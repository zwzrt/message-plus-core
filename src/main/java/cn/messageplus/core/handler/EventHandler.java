package cn.messageplus.core.handler;

import cn.messageplus.core.manage.SessionManage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketException;

/**
 * 事件处理器
 **/
@Slf4j
@ChannelHandler.Sharable
public class EventHandler extends ChannelInboundHandlerAdapter {
    /**
     * 断开连接
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.debug("{} disconnected", SessionManage.getUid(ctx.channel()));
    }
    /**
     * 出现异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof SocketException) {
            log.warn("Client abnormal disconnection", cause);
        } else {
            log.warn(cause.getMessage());
        }
    }
}
