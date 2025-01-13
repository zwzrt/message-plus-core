package cn.messageplus.core.utils;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 * 双向Map
 **/
public class BidHashMap<K, V> implements Serializable {


    /**
     * 节点
     * @param <K> 键类型
     * @param <V> 值类型
     */
    static private class Node<K, V> implements Serializable {
        K key;
        V value;
        Node<K, V> last;
        Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "{" +
                    "key=" + key + "," +
                    "value=" + value +
                    "}";
        }
    }



    /**
     * 容器
     */
    // 以键的哈希值为索引
    private Node<K, V>[] keyNodes;
    // 最后一个节点
    private Node<K, V>[] keyEndNodes;
    // 以值的哈希值为索引
    private Node<K, V>[] valueNodes;
    // 最后一个节点
    private Node<K, V>[] valueEndNodes;
    /**
     * 当前容器最大容量
     */
    private int maxSize = 32;
    /**
     * 当前容器大小
     */
    private int size = 0;
    /**
     * 扩容次数
     */
    private short expansionNum = 0;



    public BidHashMap() {
       this.keyNodes = new Node[this.maxSize];
       this.keyEndNodes = new Node[this.maxSize];
       this.valueNodes = new Node[this.maxSize];
       this.valueEndNodes = new Node[this.maxSize];
    }



    /**
     * 添加元素
     * @param key 键
     * @param value 值
     * @return 值
     */
    public V put(K key, V value) {
        // 键值对已经存在，无法添加
        if (this.getV(key) != null) {
            throw new RuntimeException("键值对已经存在: "+this.getV(key));
        }

        V v = this.getV(key);

        Node<K, V> n1 = new Node<>(key, value);
        Node<K, V> n2 = new Node<>(key, value);
        int kIndex = this.getIndex(key);
        int vIndex = this.getIndex(value);
        if (this.keyNodes[kIndex] == null) {
            this.keyNodes[kIndex] = n1;
            this.keyEndNodes[kIndex] = n1;
        } else {
            this.keyEndNodes[kIndex].next = n1;
            this.keyEndNodes[kIndex] = n1;
        }

        if (this.valueNodes[vIndex] == null) {
            this.valueNodes[vIndex] = n2;
            this.valueEndNodes[vIndex] = n2;
        } else {
            this.valueEndNodes[vIndex].next = n2;
            this.valueEndNodes[vIndex] = n2;
        }

        this.size++;

        // 扩容
        if(this.size >= this.maxSize) {
            this.expansion();
        }

        return value;
    }
    protected V put(Node<K, V> node) {
        return this.put(node.key, node.value);
    }

    /**
     * 删除元素
     * @param key 键
     * @return 返回值
     */
    public V remove(K key) {
        // 1.查询值
        V value = this.getV(key);
        if (value == null) return null;
        // 2.获取索引
        int kIndex = this.getIndex(key);
        int vIndex = this.getIndex(value);
        // 3.删除键节点
        Node<K, V> keyNode = this.keyNodes[kIndex];
        while (keyNode != null) {
            if (keyNode.key.equals(key)) {
                if (keyNode.last == null) {
                    // 为根节点，直接修改根节点
                    this.keyNodes[kIndex] = keyNode.next;
                } else {
                    // 为子节点，将上一个节点指向下一个节点
                    keyNode.last.next = keyNode.next;
                }
            }
            keyNode = keyNode.next;
        }
        // 4.删除值节点
        Node<K, V> valueNode = this.valueNodes[vIndex];
        while (valueNode != null) {
            if (valueNode.value.equals(value)) {
                if (valueNode.last == null) {
                    // 为根节点，直接修改根节点
                    this.valueNodes[vIndex] = valueNode.next;
                } else {
                    // 为子节点，将上一个节点指向下一个节点
                    valueNode.last.next = valueNode.next;
                }
            }
            valueNode = valueNode.next;
        }
        this.size--;
        return value;
    }

    /**
     * 删除元素
     * @param value 值
     * @return 返回键
     */
    public K removeByValue(V value) {
        K key = this.getK(value);
        if (key == null) return null;
        int kIndex = this.getIndex(key);
        int vIndex = this.getIndex(value);
        Node<K, V> keyNode = this.keyNodes[kIndex];
        while (keyNode != null) {
            if (keyNode.key.equals(key)) {
                if (keyNode.last == null) {
                    this.keyNodes[kIndex] = keyNode.next;
                } else {
                    keyNode.last.next = keyNode.next;
                }
            }
            keyNode = keyNode.next;
        }
        Node<K, V> valueNode = this.valueNodes[vIndex];
        while (valueNode != null) {
            if (valueNode.value.equals(value)) {
                if (valueNode.last == null) {
                    this.valueNodes[vIndex] = valueNode.next;
                } else {
                    valueNode.last.next = valueNode.next;
                }
            }
            valueNode = valueNode.next;
        }
        this.size--;
        return key;
    }

    /**
     * 容器大小
     */
    public int size() {
        return this.size;
    }
    /**
     * 当前容器最大容量
     */
    public int maxSize() {
        return this.maxSize;
    }
    public short getExpansionNum() {
        return this.expansionNum;
    }

    /**
     * 判空
     */
    public boolean isEmpty() {
        return this.keyNodes.length == 0;
    }

    /**
     * 清空
     */
    public void clear() {
        this.maxSize = 32;
        this.size = 0;
        this.expansionNum = 0;
        this.keyNodes = new Node[this.maxSize];
        this.keyEndNodes = new Node[this.maxSize];
        this.valueNodes = new Node[this.maxSize];
        this.valueEndNodes = new Node[this.maxSize];
    }

    /**
     * 获取值
     * @param key 键
     * @return 值
     */
    public V getV(K key) {
        Node<K, V> node = this.keyNodes[this.getIndex(key)];
        // 为空
        if (node == null) return null;
        // 条件key和实际key不同
        if (node.key.equals(key)) return node.value;
        // 遍历链表
        while (node.next != null) {
            if (node.next.key.equals(key)) {
                return node.value;
            } else {
                node = node.next;
            }
        }
        return null;
    }

    /**
     * 获取键
     * @param value 值
     * @return 键
     */
    public K getK(V value) {
        Node<K, V> node = this.valueNodes[this.getIndex(value)];
        // 为空
        if (node == null) return null;
        // 条件value和实际value不同
        if (node.value.equals(value)) return node.key;
        // 遍历链表
        while (node.next != null) {
            if (node.next.value.equals(value)) {
                return node.key;
            } else {
                node = node.next;
            }
        }
        return null;
    }

    public Node<K, V>[] getKeyNodes() {
        return this.keyNodes;
    }

    /**
     * 该键值对是否存在
     * @param key 键
     * @param value 值
     * @return 是否存在
     * @deprecated 不准确
     */
    @Deprecated
    public boolean existence(K key, V value) {
        V v = getV(key);
        if (v == null || v.equals(value)) {
            return false;
        }
        K k = getK(v);
        if (k == null || k.equals(key)) {
            return false;
        }
        return true;
    }

    /**
     * 该键是否存在
     * @param key 键
     * @return 是否存在
     * @deprecated 不准确
     */
    @Deprecated
    public boolean existenceByKey(K key) {
        V v = getV(key);
        if (v == null) {
            return false;
        }
        K k = getK(v);
        if (k == null) {
            return false;
        }
        return true;
    }

    /**
     * 该值是否存在
     * @param value 值
     * @return 是否存在
     * @deprecated 不准确
     */
    @Deprecated
    public boolean existenceByValue(V value) {
        K k = getK(value);
        if (k == null) {
            return false;
        }
        V v = getV(k);
        if (v == null) {
            return false;
        }
        return true;
    }

    /**
     * 通过哈希值获取对应索引
     * @param hashCode 哈希值
     * @return 索引
     */
    protected int getIndex(int hashCode) {
        return Math.abs(hashCode % maxSize);
    }
    protected int getIndex(Object o) {
        return Math.abs(o.hashCode() % maxSize);
    }

    /**
     * 扩容
     */
    protected void expansion() {
        // 已经到达最大容量，无法扩容
        if (this.maxSize == Integer.MAX_VALUE) {
            // 抛出异常
            throw new RuntimeException("数据超出容器最大容量");
        }
        // 修改容量
        this.maxSize = this.maxSize * 2;
        this.size = 0;
        // 复制旧容器
        Node<K, V>[] oldKeyNodes = this.keyNodes;
//        Node<K, V>[] keyEndNodes = this.keyEndNodes;
//        Node<K, V>[] valueNodes = this.valueNodes;
//        Node<K, V>[] valueEndNodes = this.valueEndNodes;
        // 创建新容器
        this.keyNodes = new Node[this.maxSize];
        this.keyEndNodes = new Node[this.maxSize];
        this.valueNodes = new Node[this.maxSize];
        this.valueEndNodes = new Node[this.maxSize];
        // 转移数据到新容器
        this.foreach(oldKeyNodes, (node) -> {
            this.put(node);
        });
        // 增加扩容次数
        expansionNum++;
    }

    protected void foreach(Node<K, V>[] nodes, Consumer<Node<K, V>> consumer) {
        for (Node<K, V> node : nodes) {
            while (node!=null) {
                consumer.accept(node);
                node = node.next;
            }
        }
    }

}
