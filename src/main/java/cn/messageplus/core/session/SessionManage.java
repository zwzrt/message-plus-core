package cn.messageplus.core.session;

import cn.messageplus.core.entity.ChatRoom;
import cn.messageplus.core.entity.Group;
import cn.messageplus.core.message.response.parent.MessageResponse;
import cn.messageplus.core.utils.BidHashMap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 会话管理
 **/
public class SessionManage {
    protected static final BidHashMap<String, Channel> uidChannelMap = new BidHashMap<>();
    protected static final BidHashMap<String, ByteBuf> uidBufferMap = new BidHashMap<>();
    protected static Map<String, Group> groupMap = new ConcurrentHashMap<>();
    protected static Map<String, ChatRoom> chatRoomMap = new ConcurrentHashMap<>();



    public static void join(String uid, Channel channel) {
        uidChannelMap.removeByKey(uid);
        uidChannelMap.put(uid, channel);
        uidBufferMap.removeByKey(uid);
        uidBufferMap.put(uid, channel.alloc().buffer());
    }

    /**
     * 发送响应
     * @param toId 用户ID
     * @param response 响应
     */
    public static void send(String toId, MessageResponse response) {
        Channel channel = uidChannelMap.getV(toId);
        if (channel != null) {
            channel.writeAndFlush(response);
        }
    }

    /**
     * 发送群组响应
     * @param toGroupId 群组ID
     * @param response 响应
     */
    public static void sendByGroup(String toGroupId, MessageResponse response) {
        Group group = getGroupById(toGroupId);
        if (group==null) return;
        send(group.getCreateUserId(), response);
        for (String userId : group.getUserIdList()) {
            send(userId, response);
        }
    }

    public static String getUid(Channel channel) {
        return uidChannelMap.getK(channel);
    }



    public static ByteBuf getBuf(String uid) {
        return uidBufferMap.getV(uid);
    }

    public static ByteBuf getBuf(Channel channel) {
        String uid = getUid(channel);
        if (uid == null) return null;
        return uidBufferMap.getV(uid);
    }


    /**
     * 设置群组信息
     * @param groupList 群组列表
     */
    public static void settingGroups(List<Group> groupList) {
        Map<String, Group> collect = groupList.stream().collect(Collectors.toMap(Group::getId, e->e, (v1, v2)->v1));
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
     * 获取群组
     * @param groupId 群组ID
     */
    public static Group getGroupById(String groupId) {
        return groupMap.get(groupId);
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
