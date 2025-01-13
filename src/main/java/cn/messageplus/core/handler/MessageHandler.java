package cn.messageplus.core.handler;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import cn.messageplus.core.message.Message;
import cn.messageplus.core.message.response.LoginResponse;
import cn.messageplus.core.session.SessionManage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求处理器
 */
@Slf4j
@ChannelHandler.Sharable
public class MessageHandler extends SimpleChannelInboundHandler<Message> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        // 不为空说明已登录，放行
        if (SessionManage.getUid(channelHandlerContext.channel()) != null) {
            // 放行
            channelHandlerContext.fireChannelRead(message);
        }
        // 为空则可能未登录，或者登录后首次请求，未添加至会话管理
        else {
            // 获取UID
            Object id = StpUtil.getLoginIdByToken(message.getHeaders().get("token"));
            if (id != null) {
                // 转换UID
                String uid = (String) id;
                // 加入会话管理
                SessionManage.join(uid, channelHandlerContext.channel());
                // 放行
                channelHandlerContext.fireChannelRead(message);
            } else {
                // 为空，说明未登录
                // 响应未登录
                channelHandlerContext.writeAndFlush(new LoginResponse(false));
            }
//            try {
//                // 获取UID
//                String uid = StpUtil.getLoginIdAsString();
//                // 加入会话管理
//                SessionManage.join(uid, channelHandlerContext.channel());
//                // 放行
//                channelHandlerContext.fireChannelRead(message);
//            } catch (NotLoginException e) {
//                // 抛出异常，说明未登录
//                // 响应未登录
//                channelHandlerContext.writeAndFlush(new LoginResponse(false));
//            }
        }
    }

}
