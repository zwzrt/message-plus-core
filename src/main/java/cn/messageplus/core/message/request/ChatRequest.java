package cn.messageplus.core.message.request;

import cn.messageplus.core.annotation.MessagePlusRequest;
import cn.messageplus.core.message.request.parent.MessageRequest;
import lombok.Data;

/**
 * 聊天请求
 */
@Data
@MessagePlusRequest
public class ChatRequest extends MessageRequest {
    public static final short type = 1;
    String content;

    public ChatRequest() {}
    public ChatRequest(String content) {
        this.content = content;
    }

    @Override
    public short getType() {
        return type;
    }
}
