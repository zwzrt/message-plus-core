package cn.messageplus.core.message.request;

import cn.messageplus.core.annotation.MessagePlusRequest;
import cn.messageplus.core.message.Message;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 图片请求
 **/
@Data
@NoArgsConstructor
@MessagePlusRequest
public class PictureRequest extends Message {
    public static final short type = 5;

    // 切片ID
    private int sliceId;
    // 切片总数
    private int sliceNum;
    // 当前切片索引
    private int currentIndex;
    // 实际长度
    private int length;
    private byte[] bytes;

    @Override
    public short getType() {
        return type;
    }
}
