package cn.messageplus.core.message.request;

import cn.messageplus.core.annotation.MessagePlusRequest;
import cn.messageplus.core.message.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mo
 * @日期: 2024-12-03 23:23
 **/
@Data
@MessagePlusRequest
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest extends Message {
    public static final short type = 11;
    String username;
    String password;

    @Override
    public short getType() {
        return type;
    }
}
