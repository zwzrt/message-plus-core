package cn.messageplus.core.entity;

import cn.messageplus.core.utils.SnowflakeIDUtil;
import lombok.Data;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 群组
 **/
@Data
public class Group {
    /**
     * 群组ID
     */
    private String id;
    /**
     * 创建者ID
     */
    private String createUserId;
    /**
     * 群组名称
     */
    private String name;
    /**
     * 用户ID列表
     */
    private CopyOnWriteArrayList<String> userIdList;
    /**
     * 是否开启禁言
     */
    private Boolean isForbiddenSpeak = false;

    /**
     * 创建群组
     * @param createUserId 创建者ID
     * @param groupName 群组名称
     */
    public Group(String createUserId, String groupName) {
        this.id = SnowflakeIDUtil.getGroupID();
        this.createUserId = createUserId;
        this.name = groupName;
        this.userIdList = new CopyOnWriteArrayList<>();
    }

    /**
     * 创建群组
     * @param createUserId 创建者ID
     * @param groupName 群组名称
     * @param userIdList 成员ID（无需携带创建者ID）
     */
    public Group(String createUserId, String groupName, CopyOnWriteArrayList<String> userIdList) {
        this(SnowflakeIDUtil.getGroupID(), createUserId, groupName, userIdList);
    }

    /**
     * 创建群组
     * @param id 群组ID
     * @param createUserId 创建者ID
     * @param groupName 群组名称
     * @param userIdList 成员ID（无需携带创建者ID）
     */
    public Group(String id, String createUserId, String groupName, CopyOnWriteArrayList<String> userIdList) {
        this(createUserId, groupName);
        this.id = id;
        this.userIdList = userIdList;
    }

    /**
     * 加入群组
     * @param userId 用户ID
     */
    public void join(String userId) {
        this.userIdList.add(userId);
    }

    /**
     * 获取群组的简化字符串表示
     * 此方法用于生成一个包含创建者ID和群组名称的字符串，用于快速比较两个群组是否相同
     * @return 群组的简化字符串表示
     */
    private String getEasyStr() {
        return this.getCreateUserId()+":"+this.getName();
    }
    
    /**
     * 获取群组的简化字符串表示
     * 这是一个静态方法，用于外部调用生成群组的简化字符串表示
     * @param createUserId 群组的创建者ID
     * @param name 群组的名称
     * @return 群组的简化字符串表示
     */
    private static String getEasyStr(String createUserId, String name) {
        return createUserId+":"+name;
    }

    /**
     * 判断两个群组是否相同
     * 此方法使用群组的简化字符串表示来比较两个群组是否相同
     * @param group1 第一个群组对象
     * @param group2 第二个群组对象
     * @return 如果两个群组相同返回true，否则返回false
     */
    public static boolean isSame(Group group1, Group group2) {
        return group1.getEasyStr().equals(group2.getEasyStr());
    }
    
    /**
     * 判断给定的群组信息与一个群组对象是否相同
     * 此方法用于在已知群组创建者ID和群组名称的情况下，判断该群组与另一个群组对象是否相同
     * @param createUserId 群组的创建者ID
     * @param name 群组的名称
     * @param group2 待比较的群组对象
     * @return 如果两个群组相同返回true，否则返回false
     */
    public static boolean isSame(String createUserId, String name, Group group2) {
        return getEasyStr(createUserId, name).equals(group2.getEasyStr());
    }

}
