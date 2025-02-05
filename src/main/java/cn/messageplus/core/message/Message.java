package cn.messageplus.core.message;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求类型抽象类
 **/
@Data
public abstract class Message implements Serializable {
    protected final byte version = 1;
    // 请求/响应头
    protected Map<String, String> headers = new HashMap<>();
    // 请求ID
    protected String id;
    // 发送方ID
    protected String fromId;
    // 接收方类型
    // 个人 - 1
    // 群聊 - 3
    // 聊天室 - 5
    protected short toType;
    // 接收方ID
    protected String toId;

    public byte getVersion() {
        return version;
    }

    /**
     * 自定义请求类型字节码需要大于等于100
     * @return 类型编码
     */
    public abstract short getType();
}
