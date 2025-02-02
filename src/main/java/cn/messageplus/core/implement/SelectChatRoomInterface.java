package cn.messageplus.core.implement;

import cn.messageplus.core.entity.ChatRoom;

import java.util.List;

/**
 * 查询聊天室接口
 */
public interface SelectChatRoomInterface {
    public List<ChatRoom> select();
}
