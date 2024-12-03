package cn.messageplus.core.server.request;

import cn.messageplus.core.annotation.MessagePlusRequest;
import cn.messageplus.core.request.MessageRequest;
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
public class LoginRequest extends MessageRequest {
    String username;
    String password;

    @Override
    public byte getType() {
        return 100;
    }
}
