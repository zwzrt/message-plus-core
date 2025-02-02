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
    protected String fromId;
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
