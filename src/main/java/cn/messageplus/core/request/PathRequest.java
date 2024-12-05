package cn.messageplus.core.request;

import cn.messageplus.core.annotation.MessagePlusRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 路径请求
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@MessagePlusRequest
public class PathRequest extends MessageRequest {
    // 请求路径
    String path;

    @Override
    public short getType() {
        return 1;
    }
}
