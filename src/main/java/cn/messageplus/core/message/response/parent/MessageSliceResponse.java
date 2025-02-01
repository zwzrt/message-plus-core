package cn.messageplus.core.message.response.parent;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 切片响应
 **/
@Data
@NoArgsConstructor
public abstract class MessageSliceResponse extends MessageResponse {
    // 切片ID
    protected int sliceId;
    // 切片总数
    protected int sliceNum;
    // 当前切片索引
    protected int currentIndex;
    // 实际长度
    protected int length;
    protected List<Byte> bytes;

    public MessageSliceResponse(byte[] bytes) {
        this.bytes = new ArrayList<>();
        for (byte b : bytes) {
            this.bytes.add(b);
        }
    }
}
