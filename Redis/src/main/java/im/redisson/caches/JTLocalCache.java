package im.redisson.caches;

import im.com.common.JTSpringUtils;
import org.redisson.api.RedissonClient;

import java.util.concurrent.locks.ReadWriteLock;

public abstract class JTLocalCache implements JTILocalCache
{
	public String name = null;

	protected RedissonClient redissonClient = null;

	public JTLocalCache(String name) 
	{
		this.name = name;
		this.redissonClient = JTSpringUtils.getBean(RedissonClient.class);
	}

	public ReadWriteLock getReadWriteLock() 
	{
		return null;
	}

	public abstract void clear();
 
	@Override
	public abstract int getSize();
}
