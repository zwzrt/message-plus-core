package cn.messageplus.core;

import cn.messageplus.core.message.Message;
import cn.messageplus.core.message.MessageFactory;
import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

@ChannelHandler.Sharable
public class MessageCodec extends MessageToMessageCodec<ByteBuf, Message> {
    /**
     * 编码
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
        ByteBuf buffer = ctx.alloc().buffer();
        // 版本
        buffer.writeByte(msg.getVersion());
        // 类型
        buffer.writeShort(msg.getType());
        byte[] jsonBytes = JSON.toJSONBytes(msg);
        // 长度
        buffer.writeInt(jsonBytes.length);
        // 内容
        buffer.writeBytes(jsonBytes);

        out.add(buffer);
    }
    /**
     * 解码
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        while (msg.readableBytes() > 0) {
            // 版本
            byte version = msg.readByte();
            // 类型
            short type = msg.readShort();
            // 长度
            int length = msg.readInt();
            // 内容
            byte[] content = new byte[length];
            msg.readBytes(content, 0, length);

            Object message = JSON.parseObject(content, MessageFactory.getMessageType(type));

            out.add(message);
        }
    }
}
