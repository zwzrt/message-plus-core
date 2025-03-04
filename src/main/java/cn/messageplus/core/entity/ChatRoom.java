package cn.messageplus.core.entity;

import cn.messageplus.core.utils.SnowflakeIDUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 聊天室
 **/
@Data
@NoArgsConstructor
public class ChatRoom implements Serializable {
    /**
     * 聊天室ID
     */
    private String id;
    /**
     * 创建者ID
     */
    private String createUserId;
    /**
     * 聊天室名称
     */
    private String name;
    /**
     * 最大人数
     */
    private int maxUserNum = 0;
    /**
     * 总人数
     */
    private int allUserNum = 0;
    /**
     * 当前人数
     */
    private int userNum = 0;
    /**
     * 用户ID列表
     */
    private Map<String, Integer> clientIdMap = new ConcurrentHashMap<>();
    /**
     * 点赞数量
     */
    private long thumbsUpNum = 0;
    /**
     * 开播时间
     */
    private Timestamp openingTime = new Timestamp(System.currentTimeMillis());
    /**
     * 停播时间
     */
    private Timestamp offTime;
    /**
     * 是否开启禁言
     */
    private Boolean isForbiddenSpeak = false;

    /**
     * 创建聊天室
     * @param createUserId 创建者ID
     * @param chatRoomName 聊天室名称
     */
    public ChatRoom(String createUserId, String chatRoomName) {
        this.id = SnowflakeIDUtil.getID();
        this.createUserId = createUserId;
        this.name = chatRoomName;
    }

    /**
     * 创建聊天室
     * @param id 聊天室ID
     * @param createUserId 创建者ID
     * @param chatRoomName 聊天室名称
     */
    public ChatRoom(String id, String createUserId, String chatRoomName) {
        this.id = id;
        this.createUserId = createUserId;
        this.name = chatRoomName;
    }

    /**
     * 加入聊天室
     * @param userId 用户ID
     * @return 总人数
     */
    public int joinChatRoom(String userId) {
        if (userId==null||userId.isEmpty()) return -1;

        // 是否存在
        Integer i = clientIdMap.get(userId);

        // 未加入聊天室
        if (i==null) {
            // 加入聊天室
            clientIdMap.put(userId, 1);
            // 设置最大人数
            this.maxUserNum = clientIdMap.size()>this.maxUserNum?clientIdMap.size():this.maxUserNum;
        }

        return clientIdMap.size();
    }

    /**
     * 退出聊天室
     * @param userId 用户ID
     * @return 总人数
     */
    public int quitChatRoom(String userId) {
        if (userId==null||userId.isEmpty()) return -1;
        // 退出聊天室
        clientIdMap.remove(userId);

        return clientIdMap.size();
    }


    public List<String> getClientIdList() {
        return new ArrayList<>(clientIdMap.keySet());
    }

}
