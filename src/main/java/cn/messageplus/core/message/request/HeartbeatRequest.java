package cn.messageplus.core.message.request;

import cn.messageplus.core.annotation.MessagePlusRequest;
import cn.messageplus.core.message.request.parent.MessageRequest;
import lombok.Data;

/**
 * 心跳请求
 */
@Data
@MessagePlusRequest
public class HeartbeatRequest extends MessageRequest {
    public static final short type = 0;
    @Override
    public short getType() {
        return type;
    }
}
