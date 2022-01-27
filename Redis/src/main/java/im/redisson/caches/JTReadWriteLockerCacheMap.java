package im.redisson.caches;

import com.alibaba.fastjson.JSON;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RReadWriteLock;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class JTReadWriteLockerCacheMap extends JTLocalCache implements JTICacheMap
{
    private RMap<Object, Object> cacheMap = null;

    public JTReadWriteLockerCacheMap(String name)
    {
        super(name);
        this.cacheMap = this.redissonClient.getMap(name);
    }

    public int getSize()
    {
        return this.cacheMap.size();
    }

    public void set(String key, Object value)
    {
        RReadWriteLock locker = this.cacheMap.getReadWriteLock(key);
        RLock writeLocker = locker.writeLock();
        writeLocker.lock();
        try
        {
            if (value instanceof String) this.cacheMap.put(key, value);
            else
            {
                this.cacheMap.put(key, JSON.toJSONString(value));
            }
        }
        finally
        {
            writeLocker.unlock();
        }
    }

    @Override
    public Object getObject(String key)
    {
        RReadWriteLock locker = this.cacheMap.getReadWriteLock(key);
        RLock readLocker = locker.writeLock();
        readLocker.lock();
        try
        {
            String content = get(key);
            Object value = content != null ? JSON.parse(content) : null;
            return value;
        }
        finally
        {
            readLocker.unlock();
        }
    }

    @Override
    public RMap getMap()
    {
        return this.cacheMap;
    }

    @Override
    public String get(String key)
    {
        RReadWriteLock locker = this.cacheMap.getReadWriteLock(key);
        RLock readLocker = locker.writeLock();
        readLocker.lock();
        try
        {
            return (String) this.cacheMap.get(key);
        }
        finally
        {
            readLocker.unlock();
        }
    }

    public Object remove(Object key)
    {
        RReadWriteLock locker = this.cacheMap.getReadWriteLock(key);
        RLock writeLocker = locker.writeLock();
        writeLocker.lock();
        try
        {
            String value = (String) this.cacheMap.get(key);
            return value != null ? JSON.parse(value) : null;
        }
        finally
        {
            writeLocker.unlock();
        }
    }

    public LinkedHashMap<Object, Object> toLinkMap()
    {
        Set<Object> keys = this.cacheMap.keySet();
        Map<Object, Object> map = this.cacheMap.getAll(keys);
        return (LinkedHashMap<Object, Object>) map;
    }

    public void clear()
    {
        this.cacheMap.clear();
    }

    public static JTReadWriteLockerCacheMap instance(String name)
    {
        JTReadWriteLockerCacheMap cache = (JTReadWriteLockerCacheMap) JTCacheManager.getValue(name);
        if (cache == null)
        {
            cache = new JTReadWriteLockerCacheMap(name);
            JTCacheManager.put(name, cache);
        }
        return cache;
    }
}
