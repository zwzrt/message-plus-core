package cn.messageplus.core.request;

import cn.messageplus.core.annotation.MessagePlusRequest;
import lombok.Data;

/**
 * 聊天请求
 */
@Data
@MessagePlusRequest
public class ChatRequest extends MessageRequest {
    String content;

    public ChatRequest() {}
    public ChatRequest(String content) {
        this.content = content;
    }

    @Override
    public short getType() {
        return 2;
    }
}
