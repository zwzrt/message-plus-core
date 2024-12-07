package cn.messageplus.core.client;

import cn.messageplus.core.MessageCodec;
import cn.messageplus.core.client.handler.PathResponseHandler;
import cn.messageplus.core.message.MessageFactory;
import cn.messageplus.core.message.request.LoginRequest;
import cn.messageplus.core.message.request.PathRequest;
import cn.messageplus.core.client.handler.LoginResponseHandler;
import cn.messageplus.core.message.response.LoginResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

/**
 * 客户端
 **/
@Slf4j
public class ClientTest {
    private static void login(ChannelHandlerContext ctx) {
        System.out.println("用户1：admin");
        System.out.println("用户2：张三");
        Scanner scanner = new Scanner(System.in);
        System.out.print("选择账户：");
        String s = scanner.nextLine();
        String username;
        switch (s) {
            case "1":
                username = "admin";
                break;
            case "2":
                username = "张三";
                break;
            default:
                username = s;
        }
        System.out.print("输入密码：");
        String password = scanner.nextLine();
        LoginRequest loginRequest = new LoginRequest(username, password);
        ctx.writeAndFlush(loginRequest);
    }

    public static void main(String[] args) {
        // 1.初始化
        MessageFactory.clientStaticInit();
        // 2.启动
        NioEventLoopGroup group = new NioEventLoopGroup();
        MessageCodec MESSAGE_CODEC = new MessageCodec();
        LoginResponseHandler LOGIN_RESPONSE_HANDLER = new LoginResponseHandler();
        PathResponseHandler PATH_RESPONSE_HANDLER = new PathResponseHandler();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(group);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(MESSAGE_CODEC);
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
//                            ChatRequest chatRequest = new ChatRequest("hello");
//                            ctx.writeAndFlush(chatRequest);

                            PathRequest request1 = new PathRequest("/test/1");
                            ctx.writeAndFlush(request1);


                            login(ctx);

                            PathRequest request2 = new PathRequest("/test/2", new Object[]{"哈哈哈"});
                            ctx.writeAndFlush(request2);
                        }
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            ctx.fireChannelRead(msg);
                        }
                    });
                    ch.pipeline().addLast(LOGIN_RESPONSE_HANDLER);
                    ch.pipeline().addLast(PATH_RESPONSE_HANDLER);
                }
            });
            Channel channel = bootstrap.connect("localhost", 8080).sync().channel();
            channel.closeFuture().sync();
        } catch (Exception e) {
            log.error("client error", e);
        } finally {
            group.shutdownGracefully();
        }
    }
}
