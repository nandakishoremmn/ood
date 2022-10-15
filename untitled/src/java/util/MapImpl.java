package java.util;

import javafx.util.Pair;

public class MapImpl<K, V> implements Map<K, V> {
    List<List<Pair<K, V>>> data = new ArrayList<>();

    private int bucketSize = 10;
    private int dataSize = 0;

    public MapImpl() {
        data = new ArrayList<>(10);
    }

    @Override
    public int size() {
        return dataSize;
    }

    @Override
    public boolean isEmpty() {
        return dataSize == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        int hash = key.hashCode() % bucketSize;
        if(data.get(hash) == null) {
            return false;
        } else {
            return data.get(hash).stream()
                    .map(pair -> pair.getKey())
                    .anyMatch(key1 -> key1.equals(key));
        }
    }

    @Override
    public boolean containsValue(Object value) {
        data.stream().filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .map(pair -> pair.getValue())
                .anyMatch(value1 -> value1.equals(value));
    }

    @Override
    public V get(Object key) {
        return null;
    }

    @Override
    public V put(K key, V value) {
        int hash = key.hashCode() % bucketSize;
        if(data.get(hash) == null) {
            data.set(hash, new ArrayList<>());
        }
        boolean found = false;
        for (int i = 0 ; i < size(); i++) {
            if(data.get(hash).get(i).getKey().equals(key)) {
                data.get(hash).set(i, new Pair<>(key, value));
                found = true;
                break;
            }
        }
        if(!found) {
            data.get(hash).add(new Pair<>(key, value));
        }
        return value;
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }
}
