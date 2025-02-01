package cn.messageplus.core.message.response;

import cn.messageplus.core.annotation.MessagePlusResponse;
import cn.messageplus.core.message.response.parent.MessageResponse;
import lombok.Data;

/**
 * @deprecated 
 * @author mo
 * @日期: 2024-12-07 14:50
 **/
@Deprecated
@Data
@MessagePlusResponse
public class LoginResponse extends MessageResponse {
    public static final short type = 52;
    private boolean success;

    public LoginResponse() {}
    public LoginResponse(boolean success) {
        this.success = success;
    }

    @Override
    public short getType() {
        return type;
    }
}
