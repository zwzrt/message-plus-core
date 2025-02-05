package cn.messageplus.core.handler;

import cn.dev33.satoken.stp.StpUtil;
import cn.messageplus.core.message.Message;
import cn.messageplus.core.manage.SessionManage;
import cn.messageplus.core.message.response.ErrorResponse;
import cn.messageplus.core.util.MpcUtil;
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
        String suid = SessionManage.getUid(channelHandlerContext.channel());
        if (suid != null) {
            MpcUtil.setUserId(suid);
            message.setFromId(suid);
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
                MpcUtil.setUserId(suid);
                message.setFromId(uid);
                // 放行
                channelHandlerContext.fireChannelRead(message);
            } else {
                // 为空，说明未登录
                // 响应未登录
                channelHandlerContext.writeAndFlush(new ErrorResponse("Not logged in!"));
            }
        }
    }

}
