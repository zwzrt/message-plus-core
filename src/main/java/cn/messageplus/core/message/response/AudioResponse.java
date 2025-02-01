package cn.messageplus.core.message.response;

import cn.messageplus.core.annotation.MessagePlusResponse;
import cn.messageplus.core.message.response.parent.MessageSliceResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 音频响应
 **/
@Data
@NoArgsConstructor
@MessagePlusResponse
public class AudioResponse extends MessageSliceResponse {
    public static final short type = 54;

    public AudioResponse(byte[] bytes) {
        super(bytes);
    }

    @Override
    public short getType() {
        return type;
    }
}
