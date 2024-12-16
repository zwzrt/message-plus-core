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

    public V getV(K key) {
        return positiveMap.get(key);
    }

    public K getK(V value) {
        return negativeMap.get(value);
    }
}
