package cn.messageplus.core.message.request;

import cn.messageplus.core.annotation.MessagePlusRequest;
import cn.messageplus.core.message.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 路径请求
 **/
@Data
@MessagePlusRequest
@AllArgsConstructor
@NoArgsConstructor
public class PathRequest extends Message {
    public static final short type = 3;
    // 请求路径
    String path;
    // 请求参数
    Object[] args;

    public PathRequest(String path) {
        this.path = path;
    }

    @Override
    public short getType() {
        return type;
    }
}
