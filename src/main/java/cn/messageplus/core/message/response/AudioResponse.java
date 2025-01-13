package cn.messageplus.core.message.response;

import cn.messageplus.core.annotation.MessagePlusResponse;
import cn.messageplus.core.message.MessageResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 音频响应
 **/
@Data
@NoArgsConstructor
@MessagePlusResponse
public class AudioResponse extends MessageResponse {
    public static final short type = 54;

    // 切片ID
    private int sliceId;
    // 切片总数
    private int sliceNum;
    // 当前切片索引
    private int currentIndex;
    // 实际长度
    private int length;
    private List<Byte> bytes;

    public AudioResponse(byte[] bytes) {
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
