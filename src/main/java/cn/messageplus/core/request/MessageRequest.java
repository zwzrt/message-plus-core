package cn.messageplus.core.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 请求类型抽象类
 **/
public abstract class MessageRequest implements Serializable {
    private final byte version = 1;

    public byte getVersion() {
        return version;
    }

    /**
     * 自定义请求类型字节码需要大于等于100
     */
    public abstract byte getType();
}
