package cn.messageplus.core.message.request;

import cn.messageplus.core.annotation.MessagePlusRequest;
import cn.messageplus.core.message.Message;
import cn.messageplus.core.message.request.parent.MessageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @deprecated
 * @author mo
 * @日期: 2024-12-03 23:23
 **/
@Deprecated
@Data
@MessagePlusRequest
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest extends MessageRequest {
    public static final short type = 2;
    String username;
    String password;

    @Override
    public short getType() {
        return type;
    }
}
