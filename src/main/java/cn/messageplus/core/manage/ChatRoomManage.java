package cn.messageplus.core.manage;

import cn.messageplus.core.entity.ChatRoom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 聊天室管理
 */
public class ChatRoomManage {
    protected static Map<String, ChatRoom> chatRoomMap = new ConcurrentHashMap<>();


    /**
     * 设置聊天室信息
     * @param chatRoomList 聊天室列表
     */
    public static void settingChatRoom(List<ChatRoom> chatRoomList) {
        Map<String, ChatRoom> collect = chatRoomList.stream().collect(Collectors.toMap(ChatRoom::getId, e->e, (v1, v2)->v1));
        chatRoomMap = new ConcurrentHashMap<>(collect);
    }

    /**
     * 加入聊天室
     * @param chatRoomId 聊天室ID
     * @param userId 用户ID
     * @return 该聊天室当前人数
     */
    public static int join(String chatRoomId, String userId) {
        ChatRoom chatRoom = chatRoomMap.get(chatRoomId);
        if (chatRoom!=null) {
            return chatRoom.joinChatRoom(userId);
        } else {
            return -1;
        }
    }

    /**
     * 获取聊天室
     * @param chatRoomId 聊天室ID
     */
    public static ChatRoom getChatRoomById(String chatRoomId) {
        return chatRoomMap.get(chatRoomId);
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
