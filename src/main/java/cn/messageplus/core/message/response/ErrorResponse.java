package cn.messageplus.core.message.response;

import cn.messageplus.core.annotation.MessagePlusRequest;
import cn.messageplus.core.message.response.parent.MessageResponse;
import lombok.Data;

/**
 * 错误响应
 */
@Data
@MessagePlusRequest
public class ErrorResponse extends MessageResponse {
    public static final short type = 99;
    String content;

    public ErrorResponse() {}
    public ErrorResponse(String content) {
        this.content = content;
    }

    @Override
    public short getType() {
        return type;
    }
}
