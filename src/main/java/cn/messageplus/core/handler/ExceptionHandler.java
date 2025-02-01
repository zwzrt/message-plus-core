package cn.messageplus.core.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketException;

/**
 * 异常处理器
 **/
@Slf4j
@ChannelHandler.Sharable
public class ExceptionHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof SocketException) {
            log.warn("有客户端异常断开", cause);
        } else {
            log.warn(cause.getMessage());
        }
    }
}
