package cn.messageplus.core.message.response;

import cn.messageplus.core.annotation.MessagePlusRequest;
import cn.messageplus.core.annotation.MessagePlusResponse;
import cn.messageplus.core.message.Message;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 音频请求
 **/
@Data
@NoArgsConstructor
@MessagePlusResponse
public class AudioResponse extends Message {
    public static final short type = 54;
    private List<Byte> bytes;

    public AudioResponse(byte[] bytes) {
        this.bytes = new ArrayList<>();
        for (byte b : bytes) {
            this.bytes.add(b);
        }
    }

    @Override
    public short getType() {
        return type;
    }
}
