package cn.messageplus.core.message.request;

import cn.messageplus.core.annotation.MessagePlusRequest;
import cn.messageplus.core.message.request.parent.MessageSliceRequest;
import lombok.Data;

/**
 * 图片请求
 **/
@Data
@MessagePlusRequest
public class PictureRequest extends MessageSliceRequest {
    public static final short type = 5;

    @Override
    public short getType() {
        return type;
    }
}
