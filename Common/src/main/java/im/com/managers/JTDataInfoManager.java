package im.com.managers;



import im.com.events.JTEventDispatcher;
import im.com.gamedatas.JTDataInfo;

import java.util.HashMap;
import java.util.Map;


public abstract class JTDataInfoManager<T extends JTDataInfo> extends JTEventDispatcher
{
	protected Map<String, T> chatMap = new HashMap<String, T>();
	public JTDataInfoManager() 
	{
		super();
		
		
	}
}
