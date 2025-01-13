package cn.messageplus.core.session;

import cn.messageplus.core.message.Message;
import cn.messageplus.core.message.MessageResponse;
import cn.messageplus.core.utils.BidHashMap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 会话管理
 **/
public class SessionManage {
//    private static final DualMap<String, Channel> uidChannelMap = new DualMap<>();
//    private static final DualMap<String, ByteBuf> uidBufferMap = new DualMap<>();
    private static final BidHashMap<String, Channel> uidChannelMap = new BidHashMap<>();
    public static final BidHashMap<String, ByteBuf> uidBufferMap = new BidHashMap<>();
    private static final Map<String, Integer> uidHalfPackageMap = new ConcurrentHashMap<>();



    public static void join(String uid, Channel channel) {
        uidChannelMap.put(uid, channel);
        uidBufferMap.put(uid, channel.alloc().buffer());
    }

    public static void send(String toId, MessageResponse response) {
        Channel channel = uidChannelMap.getV(toId);
        if (channel != null) {
            channel.writeAndFlush(response);
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
     * 设置该通道出现半包，还差多少长度
     * @param channel 出现半包的通道
     * @param length 剩余长度
     */
    public static void setHalfPackage(Channel channel, int length) {
        String uid = getUid(channel);
        if (uid == null) return;
        if (length < 0) length = 0;
        uidHalfPackageMap.put(uid, length);
    }

    /**
     * 该通道是否存在半包
     * @param channel 通道
     * @return 还差多少
     */
    public static Integer hasHalfPackage(Channel channel) {
        String uid = getUid(channel);
        if (uid == null) return 0;
        return uidHalfPackageMap.get(uid);
    }
}
