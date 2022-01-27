package im.com.events;


import im.com.interfaces.JTIEvent;
import im.com.interfaces.JTIEventListener;
import im.com.interfaces.JTIPool;
import im.com.interfaces.JTIPoolObject;
import im.com.pools.JTPool;

public class JTEvent implements JTIPoolObject, JTIEvent
{
	private Object type = null;
	private Boolean once = null;
	private JTIEventListener listener = null;

	public JTEvent()
	{

	}

	public void reset(Object type, Boolean once, JTIEventListener listener)
	{
		this.type = type;
		this.listener = listener;
	}

	@Override
	public void recycle() 
	{
		this.type = null;
		this.listener = null;
		this.once = false;
	}
	
	public Boolean compare(JTIEventListener listener)
	{
			return this.listener == listener ? true : false;
	}

	public Object getType() 
	{
		return type;
	}

	public Object getArgs() 
	{
		return listener;
	}

	public Boolean getOnce() 
	{
		return once;
	}
	
	private static JTIPool<JTIPoolObject> pool = JTPool.instance(JTEvent.class);
	public static JTEvent create(Object type, Boolean once, JTIEventListener listener)
	{
		JTEvent evt = (JTEvent) JTEvent.pool.get();
		evt.reset(type, once, listener);
		return evt;
	}

	public static void put(JTEvent evt)
	{
		JTEvent.put(evt);
	}

	public JTIEventListener getListener() 
	{
		return listener;
	}

	@Override
	public void addEventListener(String type, Boolean once, JTIEventListener listener) 
	{
		
	}
}
