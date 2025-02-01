package cn.messageplus.core.message.request.parent;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 切片请求
 **/
@Data
@NoArgsConstructor
public abstract class MessageSliceRequest extends MessageRequest {
    // 切片ID
    protected int sliceId;
    // 切片总数
    protected int sliceNum;
    // 当前切片索引
    protected int currentIndex;
    // 实际长度
    protected int length;
    protected byte[] bytes;

    public MessageSliceRequest(byte[] bytes) {
        this.bytes = bytes;
    }
}
