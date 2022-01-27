package im.com.events;


import im.com.interfaces.JTIEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JTEventManager
{
    public static Map<Object, ArrayList<JTEvent>> _eventMap = new HashMap<Object, ArrayList<JTEvent>>();

    public static void addEventListener(String type, JTIEventListener listener, Boolean once)
    {
        ArrayList<JTEvent> list = _eventMap.get(type);
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
            _eventMap.put(type, list);
        }
        JTEvent evt = JTEvent.create(type, once, listener);
        list.add(evt);
    }

    public static void addEventListener(int type, JTIEventListener listener, Boolean once)
    {
        addEventListener(String.valueOf(type), listener, once);
    }

    public static void dispatchEvent(String type, Object args)
    {
        ArrayList<JTEvent> list = _eventMap.get(type);
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

    public static void dispatchEvent(int type, Object args)
    {
        dispatchEvent(String.valueOf(type), args);
    }


    public static void removeEventListener(String type, JTIEventListener listener)
    {
        ArrayList<JTEvent> list = _eventMap.get(type);
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

    public static void removeEventListener(int type, JTIEventListener listener)
    {
        removeEventListener(String.valueOf(type), listener);
    }

    public static void recycle()
    {
        for (Map.Entry<Object, ArrayList<JTEvent>> entry: _eventMap.entrySet())
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


    public static void removeEvents(String type)
    {
        ArrayList<JTEvent> list = _eventMap.get(type);
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

    public static void removeEvents(int type)
    {
        removeEvents(String.valueOf(type));
    }

}

