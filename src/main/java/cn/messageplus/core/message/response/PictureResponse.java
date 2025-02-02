package cn.messageplus.core.message.response;

import cn.messageplus.core.annotation.MessagePlusResponse;
import cn.messageplus.core.message.response.parent.MessageResponse;
import cn.messageplus.core.message.response.parent.MessageSliceResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片响应
 **/
@Data
@NoArgsConstructor
@MessagePlusResponse
public class PictureResponse extends MessageSliceResponse {
    public static final short type = 55;

    public PictureResponse(byte[] bytes) {
        this.bytes = new ArrayList<>();
        for (byte b : bytes) {
            this.bytes.add(b);
        }
    }

    @Override
    public short getType() {
        return type;
    }
}
