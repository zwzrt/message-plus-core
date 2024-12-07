package cn.messageplus.core.handler;

import cn.messageplus.core.message.Message;
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
        channelHandlerContext.fireChannelRead(message);
    }
}
