package cn.messageplus.core.handler;

import cn.messageplus.core.message.request.LoginRequest;
import cn.messageplus.core.message.response.LoginResponse;
import cn.messageplus.core.session.SessionManage;
import cn.messageplus.core.utils.exterior.SpringUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 登录请求处理器
 **/
@Slf4j
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequest loginRequest) throws Exception {
        // 获得登录处理器
        LoginHandler loginHandler = SpringUtils.getBean(LoginHandler.class);
        // 若为空，则开发者未实现登录处理器
        if (loginHandler != null) {
            // 尝试登录，若失败则返回空，否则返回用户身份标识
            String uid = loginHandler.login(loginRequest.getUsername(), loginRequest.getPassword());
            if (uid != null) {
                // 加入会话管理
                SessionManage.join(uid, channelHandlerContext.channel());
                channelHandlerContext.writeAndFlush(new LoginResponse(true));
                return;
            }
        }
        channelHandlerContext.writeAndFlush(new LoginResponse(false));
    }
}
