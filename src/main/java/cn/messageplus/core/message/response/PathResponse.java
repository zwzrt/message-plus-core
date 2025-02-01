package cn.messageplus.core.message.response;

import cn.messageplus.core.annotation.MessagePlusRequest;
import cn.messageplus.core.message.response.parent.MessageResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 路径响应
 **/
@Data
@MessagePlusRequest
@AllArgsConstructor
@NoArgsConstructor
public class PathResponse extends MessageResponse {
    public static final short type = 53;
    private Object data;

    @Override
    public short getType() {
        return type;
    }
}
