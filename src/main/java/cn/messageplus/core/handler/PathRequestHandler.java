package cn.messageplus.core.handler;

import cn.messageplus.core.StartCore;
import cn.messageplus.core.message.request.PathRequest;
import cn.messageplus.core.message.response.PathResponse;
import cn.messageplus.core.utils.exterior.SpringUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;

/**
 * 路径请求处理器
 **/
@ChannelHandler.Sharable
public class PathRequestHandler extends SimpleChannelInboundHandler<PathRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, PathRequest request) throws Exception {
        // 1.获取请求路径
        String path = request.getPath();
        // 2.获取处理该路径请求的类类型及其方法
        Class<?> clazz = StartCore.mapByPathAndClass.get(path);
        Method method = StartCore.mapByPathAndMethod.get(path);
        if (clazz == null && method == null) {
            return;
        }
        // 3.获取到实体类
        Object mapping = SpringUtils.getBean(clazz);
        // 4.执行对应方法
        Object invoke = method.invoke(mapping, request.getArgs());
        if (invoke != null) {
            channelHandlerContext.writeAndFlush(new PathResponse(invoke));
        }
    }
}
