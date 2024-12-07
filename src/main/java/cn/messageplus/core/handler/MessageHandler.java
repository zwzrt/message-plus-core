package cn.messageplus.core.handler;

import cn.messageplus.core.message.Message;
import cn.messageplus.core.message.response.LoginResponse;
import cn.messageplus.core.session.SessionManage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 请求处理器
 */
@ChannelHandler.Sharable
public class MessageHandler extends SimpleChannelInboundHandler<Message> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        // 不为空说明已登录，放行
        if (SessionManage.getUid(channelHandlerContext.channel()) != null) {
            channelHandlerContext.writeAndFlush(new LoginResponse(true));
            channelHandlerContext.fireChannelRead(message);
        } else {
            channelHandlerContext.writeAndFlush(new LoginResponse(false));
        }
    }
}
