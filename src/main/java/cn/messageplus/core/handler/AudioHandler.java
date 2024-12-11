package cn.messageplus.core.handler;

import cn.messageplus.core.message.request.AudioRequest;
import cn.messageplus.core.message.response.AudioResponse;
import cn.messageplus.core.session.SessionManage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;

/**
 * 音频请求处理器
 **/
@Slf4j
@ChannelHandler.Sharable
public class AudioHandler extends SimpleChannelInboundHandler<AudioRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AudioRequest msg) throws Exception {
        AudioResponse audioResponse = new AudioResponse(msg.getBytes());
        String s = new String(msg.getBytes());
        System.out.println(msg.getBytes().length);
        System.out.println(s.length());
        BeanUtils.copyProperties(msg, audioResponse);
        SessionManage.send(msg.getToId(), audioResponse);
    }
}
