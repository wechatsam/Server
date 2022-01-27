package im.com.events;



import im.com.interfaces.JTIEventListener;
import im.com.interfaces.JTIEventSignaler;
import im.com.interfaces.JTIPoolObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


public class JTEventDispatcher implements JTIPoolObject, JTIEventSignaler
{
	public Map<Object, ArrayList<JTEvent>> _eventMap = new HashMap<Object, ArrayList<JTEvent>>();
	public JTEventDispatcher() 
	{
		super();
	}

	public void addEventListener(Object type, JTIEventListener listener, Boolean once)
	{
		ArrayList<JTEvent> list = this._eventMap.get(type);
		if (list != null)
		{
			int size = list.size();
			for (int i = 0; i < size; i++)
			{
				JTEvent e = list.get(i);
				if (e.compare(listener)) return;
			}
		}
		else
		{
			list = new ArrayList<JTEvent>();
			this._eventMap.put(type, list);
		}
		JTEvent evt = JTEvent.create(type, once, listener);
		list.add(evt);
	}

	public void dispatch(Object type, Object args)
	{
		ArrayList<JTEvent> list = this._eventMap.get(type);
		if (list != null)
		{
			int size = list.size();
			for (int i = 0; i < size; i++)
			{
				JTEvent e = list.get(i);
				if (e != null)  e.getListener().execute(args);
			}
		}
	}

	public void removeListener(Object type, JTIEventListener listener)
	{
		ArrayList<JTEvent> list = this._eventMap.get(type);
		if (list != null)
		{
			int size = list.size();
			for (int i = 0; i < size; i++)
			{
				JTEvent e  = list.get(i);
				if (e.compare(listener))
				{
					list.remove(i);
					JTEvent.put(e);
					break;
				}
			}
		}
	}

	@Override
	public void recycle() 
	{
		for (Entry<Object, ArrayList<JTEvent>> entry: this._eventMap.entrySet()) 
		{
			ArrayList<JTEvent> list = entry.getValue();
			int size = list.size();
			for (int i = 0; i < size; i++)
			{
				JTEvent e  = list.remove(0);
				JTEvent.put(e);
			}
		}
	}

	@Override
	public void removeListener(Object type) 
	{
		ArrayList<JTEvent> list = this._eventMap.get(type);
		if (list != null)
		{
			int size = list.size();
			for (int i = 0; i < size; i++)
			{
				JTEvent e  = list.remove(0);
				JTEvent.put(e);
			}
		}
	}

}
