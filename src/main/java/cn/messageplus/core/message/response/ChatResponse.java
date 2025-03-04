package cn.messageplus.core.message.response;

import cn.messageplus.core.annotation.MessagePlusRequest;
import cn.messageplus.core.message.response.parent.MessageResponse;
import lombok.Data;

/**
 * 聊天响应
 */
@Data
@MessagePlusRequest
public class ChatResponse extends MessageResponse {
    public static final short type = 51;
    String content;

    public ChatResponse() {}
    public ChatResponse(String content) {
        this.content = content;
    }

    @Override
    public short getType() {
        return type;
    }
}
