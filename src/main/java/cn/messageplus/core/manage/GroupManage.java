package cn.messageplus.core.manage;

import cn.messageplus.core.entity.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 群组管理
 */
public class GroupManage {
    protected static Map<String, Group> groupMap = new ConcurrentHashMap<>();

    /**
     * 设置群组信息
     * @param groupList 群组列表
     */
    public static void settingGroups(List<Group> groupList) {
        Map<String, Group> collect = groupList.stream().collect(Collectors.toMap(Group::getId, e->e, (v1, v2)->v1));
        groupMap = new ConcurrentHashMap<>(collect);
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
}
