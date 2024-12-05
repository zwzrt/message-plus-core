package cn.messageplus.core.handler;

import cn.messageplus.core.annotation.MessagePlusMapping;
import cn.messageplus.core.request.MessageRequest;
import cn.messageplus.core.request.PathRequest;
import cn.messageplus.core.request.RequestFactory;
import cn.messageplus.core.utils.exterior.SpringUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

/**
 * 请求处理器
 */
@ChannelHandler.Sharable
public class MessageHandler extends SimpleChannelInboundHandler<MessageRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageRequest message) throws Exception {
        channelHandlerContext.fireChannelRead(message);
    }
}
