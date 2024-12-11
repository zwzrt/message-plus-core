package cn.messageplus.core.codec;

import cn.messageplus.core.message.Message;
import cn.messageplus.core.message.MessageFactory;
import cn.messageplus.core.message.request.AudioRequest;
import cn.messageplus.core.message.response.AudioResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * WebSocket字符串编码器
 **/
@Slf4j
public class TextWebSocketCodec extends MessageToMessageCodec<TextWebSocketFrame, Message> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
        TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(JSON.toJSONString(msg));

        out.add(textWebSocketFrame);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, TextWebSocketFrame frame, List<Object> out) throws Exception {
        JSONObject jsonObject = JSON.parseObject(frame.text());
        // 获取消息类型
        Class<? extends Message> typeClazz = MessageFactory.getMessageType(Short.parseShort(jsonObject.getString("type")));
        if (typeClazz == null) return;

        Message messageRequest = jsonObject.toJavaObject(typeClazz);

        out.add(messageRequest);
    }
}
