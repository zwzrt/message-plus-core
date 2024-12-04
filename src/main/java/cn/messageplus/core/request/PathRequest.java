package cn.messageplus.core.request;

import cn.messageplus.core.annotation.MessagePlusRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 路径请求
 **/
@Data
@MessagePlusRequest
public class PathRequest extends MessageRequest {
    // 请求路径
    String path;

    @Override
    public byte getType() {
        return 1;
    }
}
