package cn.messageplus.core.message;

import cn.messageplus.core.message.request.PathRequest;
import cn.messageplus.core.message.response.PathResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请求工厂
 **/
public class MessageFactory {
    // 存储请求类型及请求类型的代码
    public static Map<Short, Class<? extends Message>> messageClasses = new HashMap<>();

    static {
        MessageFactory.addMessageType(PathRequest.type, PathRequest.class);

        MessageFactory.addMessageType(PathResponse.type, PathResponse.class);
    }


    /**
     * 添加请求类型
     */
    public static void addMessageType(short type, Class<? extends Message> messageClass) {
        messageClasses.put(type, messageClass);
    }
    /**
     * 添加请求类型
     */
    public static void addMessageType(Message messageType) {
        messageClasses.put(messageType.getType(), messageType.getClass());
    }
    /**
     * 添加请求类型
     */
    public static void addMessageTypes(List<Message> messageType) {
        for (Message requestType : messageType) {
            messageClasses.put(requestType.getType(), requestType.getClass());
        }
    }

    public static Class<? extends Message> getMessageType(short type) {
        return messageClasses.get(type);
    }

}
