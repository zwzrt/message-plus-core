package cn.messageplus.core.server.response;

import cn.messageplus.core.annotation.MessagePlusResponse;
import cn.messageplus.core.message.Message;
import lombok.Data;

/**
 * @author mo
 * @日期: 2024-12-07 14:50
 **/
@Data
@MessagePlusResponse
public class LoginResponse extends Message {
    private boolean success;

    public LoginResponse() {}
    public LoginResponse(boolean success) {
        this.success = success;
    }

    @Override
    public short getType() {
        return 200;
    }
}
