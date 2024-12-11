package cn.messageplus.core.codec;

import cn.messageplus.core.message.Message;
import cn.messageplus.core.message.MessageFactory;
import cn.messageplus.core.message.request.AudioRequest;
import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * WebSocket字节流编码器
 **/
@Slf4j
public class BinaryWebSocketCodec extends MessageToMessageCodec<BinaryWebSocketFrame, Message> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, BinaryWebSocketFrame frame, List<Object> out) throws Exception {
        ByteBuf content = frame.content();
        byte version = content.readByte();
        short type = content.readShort();
        int length = content.readInt();
        byte[] bytes = new byte[length];
        content.readBytes(bytes);

        Class<? extends Message> requestType = MessageFactory.getMessageType(type);

        Message messageRequest = JSON.parseObject(new String(bytes), MessageFactory.getMessageType(type));

        if (requestType == AudioRequest.class) {
            int audioLength = content.readInt();
            byte[] audioBytes = new byte[audioLength];
            content.readBytes(audioBytes);
            AudioRequest request = (AudioRequest) messageRequest;
            request.setBytes(audioBytes);
        }

        out.add(messageRequest);
    }
}
