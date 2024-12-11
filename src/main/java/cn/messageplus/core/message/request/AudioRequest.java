package cn.messageplus.core.message.request;

import cn.messageplus.core.annotation.MessagePlusRequest;
import cn.messageplus.core.message.Message;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 音频请求
 **/
@Data
@NoArgsConstructor
@MessagePlusRequest
public class AudioRequest extends Message {
    public static final short type = 4;
    private byte[] bytes;

    public AudioRequest(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public short getType() {
        return type;
    }
}
