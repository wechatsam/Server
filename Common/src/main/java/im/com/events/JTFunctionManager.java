package im.com.events;


import im.com.interfaces.JTIEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JTFunctionManager
{
    public static Map<Object, ArrayList<JTEvent>> _eventMap = new HashMap<Object, ArrayList<JTEvent>>();

    public static void registerFunction(String type, JTIEventListener listener, Boolean once)
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

    public static void registerFunction(int type, JTIEventListener listener, Boolean once)
    {
         registerFunction(String.valueOf(type), listener, once);
    }

    public static void execute(String type, Object args)
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

    public static void execute(int type, Object args)
    {
        execute(String.valueOf(type), args);
    }


    public static void removeFunction(String type, JTIEventListener listener)
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

    public static void removeFunction(int type, JTIEventListener listener)
    {
        removeFunction(String.valueOf(type), listener);
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


    public static void removeFunctions(String type)
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

    public static void removeFunctions(int type)
    {
        removeFunctions(String.valueOf(type));
    }

}

