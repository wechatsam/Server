package im.redisson.caches;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class JTCacheManager
{
    @Autowired
    public RedissonClient redissonClient;

    public static final String ACCOUNT_CACHES = "account_caches";

    public Map<String, JTLocalCache> cacheMap = new ConcurrentHashMap<String, JTLocalCache>();

    public static JTCacheManager instance;

    @PostConstruct
    public void initialize()
    {
        JTCacheManager.instance = this;
    }

    public static JTLocalCache getValue(String key)
    {
        return instance.cacheMap.get(key);
    }

    public static JTLocalCache put(String key, JTLocalCache value)
    {
        return instance.cacheMap.put(key, value);
    }

}
