package cn.messageplus.core.manage;

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


    public static void join(String uid, Channel channel) {
        uidChannelMap.removeByKey(uid);
        uidChannelMap.put(uid, channel);
        uidBufferMap.removeByKey(uid);
        uidBufferMap.put(uid, channel.alloc().buffer());
    }

    /**
     * 发送响应
     * @param response 响应
     */
    public static void send(MessageResponse response) {
        switch (response.getToType()) {
            case 1: {
                sendByUser(response);
                break;
            } case 3: {
                sendByGroup(response);
                break;
            } case 5: {
                sendByChatRoom(response);
                break;
            } default: {

            }
        }
    }

    /**
     * 发送响应
     * @param response 响应
     */
    public static void sendByUser(MessageResponse response) {
        sendByUser(response.getToId(), response);
    }

    /**
     * 发送响应
     * @param response 响应
     */
    public static void sendByUser(String toId, MessageResponse response) {
        Channel channel = uidChannelMap.getV(toId);
        if (channel != null) {
            channel.writeAndFlush(response);
        }
    }

    /**
     * 发送群组响应
     * @param response 响应
     */
    public static void sendByGroup(MessageResponse response) {
        Group group = GroupManage.getGroupById(response.getToId());
        if (group==null) return;
        sendByUser(group.getCreateUserId(), response);
        for (String userId : group.getUserIdList()) {
            sendByUser(userId, response);
        }
    }

    /**
     * 发送聊天室响应
     * @param response 响应
     */
    public static void sendByChatRoom(MessageResponse response) {
        ChatRoom chatRoom = ChatRoomManage.getChatRoomById(response.getToId());
        if (chatRoom==null) return;
        sendByUser(chatRoom.getCreateUserId(), response);
        for (String userId : chatRoom.getClientIdList()) {
            sendByUser(userId, response);
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

}
