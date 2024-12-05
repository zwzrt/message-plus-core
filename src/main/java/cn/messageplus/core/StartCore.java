package cn.messageplus.core;

import cn.messageplus.core.annotation.MessagePlusMapping;
import cn.messageplus.core.annotation.MessagePlusRequest;
import cn.messageplus.core.handler.MessageHandler;
import cn.messageplus.core.handler.PathRequestHandler;
import cn.messageplus.core.request.MessageRequest;
import cn.messageplus.core.request.PathRequest;
import cn.messageplus.core.request.RequestFactory;
import cn.messageplus.core.utils.exterior.SpringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
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

    public static final Map<String, Method> mapByPathAndMethod = new ConcurrentHashMap<>();
    public static final Map<String, Class<?>> mapByPathAndClass = new ConcurrentHashMap<>();

    static {
        new Thread(()->{
            // 1.获取添加启动消息增强器核心注解的类，读取选择的协议类型
            List<Object> beansWithAnnotation = SpringUtils.getBeansWithAnnotation(EnableMessagePlusCore.class);
            if (beansWithAnnotation.isEmpty()) {
                throw new RuntimeException("无法获取到添加@EnableMessagePlusCore注解的类");
            }
            Object application = beansWithAnnotation.get(0);
            // 获取协议类型
            MessagePlusAgreement value = application.getClass().getAnnotation(EnableMessagePlusCore.class).value();

            // 2.初始化请求类型
            List<MessageRequest> requestList = SpringUtils.getBeansWithAnnotation(MessagePlusRequest.class).stream().map((o) -> (MessageRequest) o).collect(Collectors.toList());
            RequestFactory.addRequestType(requestList);

            // 3.初始化业务层(Controller)
            List<Object> beansWithMessagePlusMapping = SpringUtils.getBeansWithAnnotation(MessagePlusMapping.class);
            for (Object bean : beansWithMessagePlusMapping) {
                String path1 = bean.getClass().getAnnotation(MessagePlusMapping.class).value();
                Method[] methods = bean.getClass().getMethods();
                for (Method method : methods) {
                    MessagePlusMapping annotation = method.getAnnotation(MessagePlusMapping.class);
                    if (annotation != null) {
                        String path2 = annotation.value();
                        mapByPathAndClass.put(path1 + path2, bean.getClass());
                        mapByPathAndMethod.put(path1 + path2, method);
                    }
                }
            }

            // 4.启动网络服务
            try {
                serverBootstrap.channel(NioServerSocketChannel.class);
                serverBootstrap.group(boss, worker);
                serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        switch (value) {
                            case MPCA:
                                configureByMPCA(ch);
                                break;
                            case WEB_SOCKET:
                                configureByWEBSOCKET(ch);
                                break;
                        }
                        ch.pipeline().addLast(new PathRequestHandler());
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

    private static void configureByMPCA(SocketChannel ch) {
        MessageCodec MESSAGE_CODEC = new MessageCodec();
        MessageHandler MESSAGE_HANDLER = new MessageHandler();

        ch.pipeline().addLast(MESSAGE_CODEC);
        ch.pipeline().addLast(MESSAGE_HANDLER);
        ch.pipeline().addLast(new PathRequestHandler());
    }
    private static void configureByWEBSOCKET(SocketChannel ch) {
        ch.pipeline().addLast(new HttpServerCodec());
        ch.pipeline().addLast(new HttpObjectAggregator(65536));
        ch.pipeline().addLast(new WebSocketServerProtocolHandler("/"));
        ch.pipeline().addLast(new MessageToMessageCodec<BinaryWebSocketFrame, MessageRequest>() {
            @Override
            protected void encode(ChannelHandlerContext ctx, MessageRequest msg, List<Object> out) throws Exception {
            }

            @Override
            protected void decode(ChannelHandlerContext ctx, BinaryWebSocketFrame frame, List<Object> out) throws Exception {
                ByteBuf content = frame.content();
                byte type = content.readByte();
                byte[] bytes = new byte[content.readableBytes() - 1];
                content.readBytes(bytes);

                Class<? extends MessageRequest> requestType = RequestFactory.getRequestType(type);
                MessageRequest messageRequest = JSON.parseObject(new String(bytes), RequestFactory.getRequestType(type));
                out.add(messageRequest);
            }
        });
        ch.pipeline().addLast(new MessageToMessageCodec<TextWebSocketFrame, MessageRequest>() {
            @Override
            protected void encode(ChannelHandlerContext ctx, MessageRequest msg, List<Object> out) throws Exception {
            }

            @Override
            protected void decode(ChannelHandlerContext ctx, TextWebSocketFrame frame, List<Object> out) throws Exception {
                JSONObject jsonObject = JSON.parseObject(frame.text());
                MessageRequest messageRequest = jsonObject.toJavaObject(RequestFactory.getRequestType(Short.parseShort(jsonObject.getString("type"))));

                out.add(messageRequest);
            }
        });
    }
}
