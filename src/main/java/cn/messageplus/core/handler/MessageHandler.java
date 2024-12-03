package cn.messageplus.core.handler;

import cn.messageplus.core.request.MessageRequest;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class MessageHandler extends SimpleChannelInboundHandler<MessageRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageRequest message) throws Exception {
        System.out.println(message);
    }
}
