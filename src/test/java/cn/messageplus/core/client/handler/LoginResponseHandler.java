package cn.messageplus.core.client.handler;

import cn.messageplus.core.server.response.LoginResponse;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 登录请求处理器
 **/
@Slf4j
@ChannelHandler.Sharable
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginResponse loginResponse) throws Exception {
        if (loginResponse.isSuccess()) {
            System.out.println("登陆成功");
        } else {
            System.out.println("登陆失败");
        }
    }
}
