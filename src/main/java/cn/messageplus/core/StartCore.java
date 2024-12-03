package cn.messageplus.core;

import cn.messageplus.core.annotation.MessagePlusRequest;
import cn.messageplus.core.handler.MessageHandler;
import cn.messageplus.core.request.MessageRequest;
import cn.messageplus.core.request.RequestFactory;
import cn.messageplus.core.utils.exterior.SpringUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 消息增强器核心启动程序
 */
@Slf4j
@Configuration
public class StartCore {
    static {
        new Thread(()->{
            // 1.初始化请求类型
            List<MessageRequest> requestList = SpringUtils.getBeansWithAnnotation(MessagePlusRequest.class).stream().map((o) -> (MessageRequest) o).collect(Collectors.toList());
            RequestFactory.addRequestType(requestList);
            // 2.启动网络服务
            NioEventLoopGroup boss = new NioEventLoopGroup();
            NioEventLoopGroup worker = new NioEventLoopGroup();
            MessageCodec MESSAGE_CODEC = new MessageCodec();
            MessageHandler MESSAGE_HANDLER = new MessageHandler();
            try {
                ServerBootstrap serverBootstrap = new ServerBootstrap();
                serverBootstrap.channel(NioServerSocketChannel.class);
                serverBootstrap.group(boss, worker);
                serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(MESSAGE_CODEC);
                        ch.pipeline().addLast(MESSAGE_HANDLER);
                    }
                });
                ChannelFuture channelFuture = serverBootstrap.bind(8080).sync();
                log.info("启动完成");
                channelFuture.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                log.error("server error", e);
            } finally {
                boss.shutdownGracefully();
                worker.shutdownGracefully();
            }
        }).start();
    }
}
