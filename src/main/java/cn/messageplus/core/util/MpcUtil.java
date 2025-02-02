package cn.messageplus.core.util;

import cn.messageplus.core.entity.Group;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MpcUtil {
    public static final Map<String, Group> groupMap = new ConcurrentHashMap<>();
}
