package im.redisson.caches;

import org.redisson.api.RMap;

import java.util.LinkedHashMap;

public interface JTICacheMap
{
    int getSize();

    void set(String key, Object value);

    String get(String key);

    Object getObject(String key);

    Object remove(Object key);

    LinkedHashMap<Object, Object> toLinkMap();

    RMap getMap();

    void clear();
}
