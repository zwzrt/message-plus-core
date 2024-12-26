package cn.messageplus.core.handler;

import cn.messageplus.core.message.request.AudioRequest;
import cn.messageplus.core.message.response.AudioResponse;
import cn.messageplus.core.session.SessionManage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

/**
 * 音频请求处理器
 **/
@Slf4j
@ChannelHandler.Sharable
public class AudioHandler extends SimpleChannelInboundHandler<AudioRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AudioRequest msg) throws Exception {
        // 1.创建响应对象
        AudioResponse audioResponse = new AudioResponse(msg.getBytes());
        // 2.拷贝
        BeanUtils.copyProperties(msg, audioResponse);
        // 3.响应请求
        SessionManage.send(msg.getToId(), audioResponse);
    }
}
