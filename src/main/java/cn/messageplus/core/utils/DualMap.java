package cn.messageplus.core.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 双向Map
 **/
public class DualMap<K, V> {
    private final Map<K, V> positiveMap;
    private final Map<V, K> negativeMap;

    public DualMap() {
        positiveMap = new HashMap<>();
        negativeMap = new HashMap<>();
    }

    public void put(K key, V value) {
        positiveMap.put(key, value);
        negativeMap.put(value, key);
    }


    /**
     * 获取值
     * @param key 键
     * @return 值
     */
    public V getV(K key) {
        return positiveMap.get(key);
    }

    /**
     * 获取键
     * @param value 值
     * @return 键
     */
    public K getK(V value) {
        return negativeMap.get(value);
    }
}
