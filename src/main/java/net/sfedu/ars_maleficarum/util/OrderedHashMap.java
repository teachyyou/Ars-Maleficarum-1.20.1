package net.sfedu.ars_maleficarum.util;

import java.util.*;

public class OrderedHashMap<K, V> extends LinkedHashMap<K, V> {
    protected List<K> elements = new ArrayList<>();

    public OrderedHashMap() {
        super();
    }

    public K getKey(int i) {
        return elements.get(i);
    }

    public V getElement(int i) {
        return get(elements.get(i));
    }

    @Override
    public V put(K key, V value) {
        if (!containsKey(key)) {
            elements.add(key);
        }
        return super.put(key, value);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public V remove(Object key) {
        elements.remove(key);
        return super.remove(key);
    }

    @Override
    public void clear() {
        elements.clear();
        super.clear();
    }
}
