/**
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package im.redisson.caches;

import com.alibaba.fastjson.JSON;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class JTLockerCacheMap extends JTLocalCache implements JTICacheMap
{
	private RMap<Object, Object> cacheMap = null;

	public JTLockerCacheMap(String name)
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
		RLock locker = this.cacheMap.getLock(key);
		locker.lock();
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
			locker.unlock();
		}
	}


	@Override
	public Object getObject(String key)
	{
		RLock locker = this.cacheMap.getLock(key);
		locker.lock();
		try
		{
			String content = get(key);
			Object value = content != null ? JSON.parse(content) : null;
			return value;
		}
		finally
		{
			locker.unlock();
		}
	}

	@Override
	public String get(String key)
	{
		RLock locker = this.cacheMap.getLock(key);
		locker.lock();
		try
		{
			return (String) this.cacheMap.get(key) ;
		}
		finally
		{
			locker.unlock();
		}
	}

	public Object remove(Object key) 
	{
		RLock locker = this.cacheMap.getLock(key);
		locker.lock();
		try
		{
			String value = (String) this.cacheMap.get(key);
			return value != null ? JSON.parse(value) : null;
		}
		finally
		{
			locker.unlock();
		}
	}
	
	public LinkedHashMap<Object, Object> toLinkMap()
	{
		Set<Object> keys = this.cacheMap.keySet();
		Map<Object, Object> map = this.cacheMap.getAll(keys);
		return (LinkedHashMap<Object, Object>) map;
	}

	@Override
	public RMap getMap()
	{
		return this.cacheMap;
	}

	public void clear() 
	{
		this.cacheMap.clear();
	}
	
	public static JTLockerCacheMap instance(String name)
	{
		JTLockerCacheMap cache = (JTLockerCacheMap) JTCacheManager.getValue(name);
		if (cache == null)
		{
			cache = new JTLockerCacheMap(name);
			JTCacheManager.put(name, cache);
		}
		return cache;
	}
}