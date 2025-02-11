package cn.messageplus.core;

import cn.messageplus.core.annotation.MessagePlusHandler;
import cn.messageplus.core.annotation.MessagePlusMapping;
import cn.messageplus.core.annotation.MessagePlusRequest;
import cn.messageplus.core.annotation.MessagePlusResponse;
import cn.messageplus.core.codec.BinaryWebSocketCodec;
import cn.messageplus.core.codec.MessageCodec;
import cn.messageplus.core.codec.TextWebSocketCodec;
import cn.messageplus.core.entity.ChatRoom;
import cn.messageplus.core.entity.Group;
import cn.messageplus.core.handler.*;
import cn.messageplus.core.implement.SelectChatRoomInterface;
import cn.messageplus.core.implement.SelectGroupInterface;
import cn.messageplus.core.manage.ChatRoomManage;
import cn.messageplus.core.manage.GroupManage;
import cn.messageplus.core.manage.SessionManage;
import cn.messageplus.core.message.Message;
import cn.messageplus.core.message.MessageFactory;
import cn.messageplus.core.utils.exterior.SpringUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 消息增强器核心启动程序
 */
@Slf4j
@Configuration
public class StartCore {
    private static final ServerBootstrap serverBootstrap = new ServerBootstrap();
    private static final NioEventLoopGroup boss = new NioEventLoopGroup();
    private static final NioEventLoopGroup worker = new NioEventLoopGroup();

    /**
     * Codec
     */
    private static final MessageCodec MESSAGE_CODEC = new MessageCodec();

    private static final BinaryWebSocketCodec BINARY_WEB_SOCKET_CODEC = new BinaryWebSocketCodec();
    private static final TextWebSocketCodec TEXT_WEB_SOCKET_CODEC = new TextWebSocketCodec();


    /**
     * 协议类型
     */
    private static MessagePlusAgreement protocolType = MessagePlusAgreement.MPCA;
    /**
     * 自定义请求处理器列表
     */
    private static List<SimpleChannelInboundHandler<?>> customRequestHandlerList;


    // 路径请求处理器的类
    public static final Map<String, Class<?>> mapByPathAndClass = new ConcurrentHashMap<>();
    // 路径请求处理器的方法
    public static final Map<String, Method> mapByPathAndMethod = new ConcurrentHashMap<>();


    static {
        new Thread(()->{
            // 1.获取添加启动消息增强器核心注解的类，读取选择的协议类型
            configureProtocolType();

            // 2.配置注解相关类及方法
            configureAnnotationRelated();

            // 3.初始化群组及聊天室数据
            SelectGroupInterface sgi = SpringUtils.getBean(SelectGroupInterface.class);
            if (sgi != null) {
                List<Group> select = sgi.select();
                GroupManage.settingGroups(select);
            }
            SelectChatRoomInterface sci = SpringUtils.getBean(SelectChatRoomInterface.class);
            if (sci != null) {
                List<ChatRoom> select = sci.select();
                ChatRoomManage.settingChatRoom(select);
            }

            // 4.启动网络服务
            EventHandler EVENT_HANDLER = new  EventHandler();
            MessageHandler MESSAGE_HANDLER = new MessageHandler();
            HeartbeatRequestHandler HEARTBEAT_REQUEST_HANDLER = new HeartbeatRequestHandler();
            PathRequestHandler PATH_REQUEST_HANDLER = new PathRequestHandler();
            try {
                serverBootstrap.channel(NioServerSocketChannel.class);
                serverBootstrap.group(boss, worker);
                serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        // 用来判断是不是 读空闲时间过长，或 写空闲时间过长
                        // 5s 内如果没有收到 channel 的数据，会触发一个事件
                        ch.pipeline().addLast(new IdleStateHandler(30, 0, 0));
                        // ChannelDuplexHandler 可以同时作为入栈和出栈处理器
                        ch.pipeline().addLast(new ChannelDuplexHandler() {
                            // 用来触发特殊事件
                            @Override
                            public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                IdleStateEvent event = (IdleStateEvent) evt;
                                // 触发了读空闲事件
                                if (event.state() == IdleState.READER_IDLE) {
                                    log.debug("{} does not send a heartbeat request within 30s, forced disconnection", SessionManage.getUid(ctx.channel()));
                                    ctx.channel().close();
                                }
                            }
                        });
                        switch (protocolType) {
                            case MPCA:
                                configureByMPCA(ch);
                                break;
                            case WEB_SOCKET:
                                configureByWEBSOCKET(ch);
                                break;
                        }
                        ch.pipeline().addLast(MESSAGE_HANDLER);
                        ch.pipeline().addLast(HEARTBEAT_REQUEST_HANDLER);
                        ch.pipeline().addLast(PATH_REQUEST_HANDLER);
                        // 添加自定义处理器
                        for (SimpleChannelInboundHandler<?> handler : customRequestHandlerList) {
                            ch.pipeline().addLast(handler);
                        }
                        ch.pipeline().addLast(EVENT_HANDLER);
                    }
                    @Override
                    protected Object clone() throws CloneNotSupportedException {
                        log.warn("关闭链接");
                        return super.clone();
                    }
                });
                ChannelFuture channelFuture = serverBootstrap.bind(8080).sync();
                log.info("启动完成");
                channelFuture.channel().closeFuture().sync();
                System.out.println("关闭");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                boss.shutdownGracefully();
                worker.shutdownGracefully();
            }
        }).start();
    }

    /**
     * <h2>1.</h2>
     * 获取添加启动消息增强器核心注解的类，读取选择的协议类型
     */
    private static void configureProtocolType() {
        // 获取添加启动消息增强器核心注解的类，读取选择的协议类型
        List<Object> beansWithAnnotation = SpringUtils.getBeansWithAnnotation(EnableMessagePlusCore.class);
        if (beansWithAnnotation.isEmpty()) {
            throw new RuntimeException("无法获取到添加@EnableMessagePlusCore注解的类");
        }
        Object application = beansWithAnnotation.get(0);
        // 获取协议类型
        protocolType = application.getClass().getAnnotation(EnableMessagePlusCore.class).value();
    }

    /**
     * <h2>2.</h2>
     * 配置注解相关类及方法
     */
    private static void configureAnnotationRelated() {
        // 1.初始化请求类型
        List<Message> requestList = SpringUtils.getBeansWithAnnotation(MessagePlusRequest.class).stream().map((o) -> (Message) o).collect(Collectors.toList());
        MessageFactory.addMessageTypes(requestList);
        List<Message> responseList = SpringUtils.getBeansWithAnnotation(MessagePlusResponse.class).stream().map((o) -> (Message) o).collect(Collectors.toList());
        MessageFactory.addMessageTypes(responseList);

        // 2.初始化请求处理器
        customRequestHandlerList = SpringUtils.getBeansWithAnnotation(MessagePlusHandler.class).stream().map((o) -> (SimpleChannelInboundHandler<?>) o).collect(Collectors.toList());

        // 3.初始化业务层(Controller)
        List<Object> beansWithMessagePlusMapping = SpringUtils.getBeansWithAnnotation(MessagePlusMapping.class);
        for (Object bean : beansWithMessagePlusMapping) {
            String basePath = bean.getClass().getAnnotation(MessagePlusMapping.class).value();
            Method[] methods = bean.getClass().getMethods();
            for (Method method : methods) {
                MessagePlusMapping annotation = method.getAnnotation(MessagePlusMapping.class);
                if (annotation != null) {
                    String methodPath = annotation.value();
                    String fullPath = basePath + methodPath;
                    mapByPathAndClass.put(fullPath, bean.getClass());
                    mapByPathAndMethod.put(fullPath, method);
                }
            }
        }
    }


    /**
     * 配置 {@code MPCA} 协议相关配置及解码器
     */
    private static void configureByMPCA(SocketChannel ch) {
        ch.pipeline().addLast(MESSAGE_CODEC);
    }

    /**
     * 配置 {@code WebSocket} 协议相关配置及解码器
     */
    private static void configureByWEBSOCKET(SocketChannel ch) {
        ch.pipeline().addLast(new HttpServerCodec());
        ch.pipeline().addLast(new HttpObjectAggregator(65536));
        ch.pipeline().addLast(new WebSocketServerProtocolHandler("/"));
        ch.pipeline().addLast(BINARY_WEB_SOCKET_CODEC);
        ch.pipeline().addLast(TEXT_WEB_SOCKET_CODEC);
    }
}
