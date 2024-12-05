package cn.messageplus.core.handler;

import cn.messageplus.core.StartCore;
import cn.messageplus.core.request.MessageRequest;
import cn.messageplus.core.request.PathRequest;
import cn.messageplus.core.utils.exterior.SpringUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;

/**
 * @author mo
 * @日期: 2024-12-05 22:51
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
        method.invoke(mapping);
    }
}
