package cn.messageplus.core.session;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 会话管理
 **/
public class SessionManage {
    private static final Map<String, Channel> uidChannelMap = new ConcurrentHashMap<>();
    private static final Map<Channel, String> channelUidMap = new ConcurrentHashMap<>();


    public static void join(String uid, Channel channel) {
        uidChannelMap.put(uid, channel);
        channelUidMap.put(channel, uid);
    }


    public static String getUid(Channel channel) {
        return channelUidMap.get(channel);
    }
}
