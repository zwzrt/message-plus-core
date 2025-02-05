package cn.messageplus.core.util;

import cn.messageplus.core.entity.ChatRoom;
import cn.messageplus.core.entity.Group;
import cn.messageplus.core.manage.ChatRoomManage;
import cn.messageplus.core.manage.GroupManage;
import cn.messageplus.core.manage.SessionManage;
import cn.messageplus.core.message.response.parent.MessageResponse;

import java.util.*;

/**
 * 供开发人员调用的工具类
 */
public class MpcUtil {
    protected static final ThreadLocal<String> userId = new ThreadLocal<>();


    public static void setUserId(String userId) {
        MpcUtil.userId.set(userId);
    }

    public static String getUserId() {
        return userId.get();
    }

    /**
     * 发送响应
     * @param response 响应
     */
    public static void send(MessageResponse response) {
        SessionManage.send(response);
    }

    /**
     * 发送响应
     * @param response 响应
     */
    public static void sendByUser(MessageResponse response) {
        SessionManage.sendByUser(response);
    }

    /**
     * 发送响应
     * @param toId 用户ID
     * @param response 响应
     */
    public static void sendByUser(String toId, MessageResponse response) {
        SessionManage.sendByUser(toId, response);
    }

    /**
     * 发送群组响应
     * @param response 响应
     */
    public static void sendByGroup(MessageResponse response) {
        SessionManage.sendByGroup(response);
    }

    /**
     *
     */
    public static int joinChatRoom(String chatRoomId) {
        return ChatRoomManage.join(chatRoomId, MpcUtil.getUserId());
    }

    /**
     * 获取群组list
     */
    public static List<Group> getGroupList() {
        return GroupManage.getGroupList();
    }

    /**
     * 获取聊天室list
     */
    public static List<ChatRoom> getChatRoomList() {
        return ChatRoomManage.getChatRoomList();
    }

}
