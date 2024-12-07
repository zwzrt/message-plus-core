package cn.messageplus.core.server.handler;

import cn.messageplus.core.annotation.MessagePlusHandler;
import cn.messageplus.core.server.request.LoginRequest;
import cn.messageplus.core.server.response.LoginResponse;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 登录请求处理器
 **/
@Slf4j
@MessagePlusHandler
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequest> {
    private static final Map<String, String> accountMap = new ConcurrentHashMap<>();

    static {
        accountMap.put("admin", "admin");
        accountMap.put("张三", "123456");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequest loginRequest) throws Exception {
        String pass = accountMap.get(loginRequest.getUsername());
        if (pass==null) {
            log.info("登陆失败：{}", loginRequest.getUsername());
            channelHandlerContext.writeAndFlush(new LoginResponse(false));
        } else if (pass.equals(loginRequest.getPassword())) {
            log.info("登录成功：{}", loginRequest.getUsername());
            channelHandlerContext.writeAndFlush(new LoginResponse(true));
        } else {
            log.info("???");
        }
    }
}
