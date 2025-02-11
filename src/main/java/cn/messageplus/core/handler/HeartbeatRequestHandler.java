package cn.messageplus.core.handler;

import cn.messageplus.core.manage.SessionManage;
import cn.messageplus.core.message.request.HeartbeatRequest;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 心跳请求处理器
 */
@Slf4j
@ChannelHandler.Sharable
public class HeartbeatRequestHandler extends SimpleChannelInboundHandler<HeartbeatRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartbeatRequest msg) throws Exception {
        log.debug("{} sent a heartbeat request", SessionManage.getUid(ctx.channel()));
    }
}
