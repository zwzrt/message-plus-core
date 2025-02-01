package cn.messageplus.core.message.request;

import cn.messageplus.core.annotation.MessagePlusRequest;
import cn.messageplus.core.message.request.parent.MessageSliceRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 音频请求
 **/
@Data
@NoArgsConstructor
@MessagePlusRequest
public class AudioRequest extends MessageSliceRequest {
    public static final short type = 4;

    @Override
    public short getType() {
        return type;
    }
}
