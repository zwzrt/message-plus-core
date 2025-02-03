package cn.messageplus.core.util;

import cn.messageplus.core.entity.ChatRoom;
import cn.messageplus.core.entity.Group;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 供开发人员调用的工具类
 */
public class MpcUtil {
    protected static Map<String, Group> groupMap = new ConcurrentHashMap<>();
    protected static Map<String, ChatRoom> chatRoomMap = new ConcurrentHashMap<>();

    /**
     * 设置群组信息
     * @param groupList 群组列表
     */
    public static void settingGroups(List<Group> groupList) {
        Map<String, Group> collect = groupList.stream().collect(Collectors.toMap(Group::getId, e->e, (v1,v2)->v1));
        groupMap = new ConcurrentHashMap<>(collect);
    }

    /**
     * 设置聊天室信息
     * @param chatRoomList 聊天室列表
     */
    public static void settingChatRoom(List<ChatRoom> chatRoomList) {
        Map<String, ChatRoom> collect = chatRoomList.stream().collect(Collectors.toMap(ChatRoom::getId, e->e, (v1,v2)->v1));
        chatRoomMap = new ConcurrentHashMap<>(collect);
    }

    /**
     * 获取群组map
     */
    public static Map<String, Group> getGroupMap() {
        return groupMap;
    }
    /**
     * 获取群组list
     */
    public static List<Group> getGroupList() {
        return new ArrayList<>(groupMap.values());
    }
    /**
     * 获取聊天室map
     */
    public static Map<String, ChatRoom> getChatRoomMap() {
        return chatRoomMap;
    }
    /**
     * 获取聊天室list
     */
    public static List<ChatRoom> getChatRoomList() {
        return new ArrayList<>(chatRoomMap.values());
    }

}
