package im.redisson.caches;

import org.redisson.api.RList;

public class JTLocalCacheList<T extends Object> extends JTLocalCache
{
	private RList<T> list = null;

	public JTLocalCacheList(String name) 
	{
		super(name);
		this.list = this.redissonClient.getList(name);
	}

	@Override
	public void clear() 
	{
		this.list.clear();
	}

	public T get(int index)
	{
		return this.list.get(index);
	}

	public boolean add(T e)
	{
		return this.list.add(e);
	}

	@Override
	public int getSize() 
	{
		return 0;
	}

	public T remove(int index) 
	{
		return this.list.remove(index);
	}
	
	@SuppressWarnings("rawtypes")
	public static JTLocalCacheList instance(String name)
	{
		JTCacheManager cacheManger = JTCacheManager.instance;
		JTLocalCacheList cache = (JTLocalCacheList) cacheManger.getValue(name);
		if (cache == null)
		{
			cache = new JTLocalCacheList(name);
			cacheManger.put(name, cache);
		}
		return cache;
	}
}
