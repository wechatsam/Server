package im.com.pools;



import im.com.interfaces.JTIPool;
import im.com.interfaces.JTIPoolObject;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class JTPool<T extends JTIPoolObject> implements JTIPool<T>
{
	protected Class<T> _cls = null;
	protected Vector<T> _list = null;
	public JTPool(Class<T> cls)
	{
		this._cls = cls;
		this._list = new Vector<T>();
	}

	@SuppressWarnings("deprecation")
	public T get()
	{
		if (this._list.size() > 0)
		{
			return this._list.remove(0);
		}
		try 
		{
			return this._cls.newInstance();
		} 
		catch (InstantiationException e) 
		{
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) 
		{
			e.printStackTrace();
		}
		return null;
	}

	public void put(T item)
	{
		if (item != null && this._list.indexOf(item) == -1)   
		{
			item.recycle();
			this._list.add(item);
		}
	}

	
	private static ConcurrentMap<Object, JTIPool<JTIPoolObject>> _poolMap = new ConcurrentHashMap<>();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static JTIPool<JTIPoolObject> instance(Class<?> cls) 
	{
		JTIPool<JTIPoolObject> pool = JTPool._poolMap.get(cls);
		if (pool == null)
		{
			pool = new JTPool(cls);
			JTPool._poolMap.put(cls, pool);
		}
		return pool;
	}
 
}
