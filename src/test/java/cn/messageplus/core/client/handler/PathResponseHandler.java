package cn.messageplus.core.client.handler;

import cn.messageplus.core.annotation.MessagePlusHandler;
import cn.messageplus.core.message.response.PathResponse;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author mo
 * @日期: 2024-12-07 18:05
 **/
@MessagePlusHandler
@ChannelHandler.Sharable
public class PathResponseHandler extends SimpleChannelInboundHandler<PathResponse> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, PathResponse pathResponse) throws Exception {
        System.out.println(pathResponse);
    }
}
