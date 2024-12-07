package cn.messageplus.core.message.response;

import cn.messageplus.core.annotation.MessagePlusRequest;
import cn.messageplus.core.annotation.MessagePlusResponse;
import cn.messageplus.core.message.Message;
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
public class PathResponse extends Message {
    public static final short type = 20;
    private Object data;

    @Override
    public short getType() {
        return type;
    }
}
