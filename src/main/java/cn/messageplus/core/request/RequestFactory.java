package cn.messageplus.core.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请求工厂
 **/
public class RequestFactory {
    // 存储请求类型及请求类型的代码
    public static Map<Short, Class<? extends MessageRequest>> messageClasses = new HashMap<>();

    /**
     * 添加请求类型
     */
    public static void addRequestType(List<MessageRequest> requestTypes) {
        for (MessageRequest requestType : requestTypes) {
            messageClasses.put(requestType.getType(), requestType.getClass());
        }
    }

    public static Class<? extends MessageRequest> getRequestType(short type) {
        return messageClasses.get(type);
    }

}
