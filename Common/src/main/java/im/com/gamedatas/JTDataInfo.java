package im.com.gamedatas;


import im.com.interfaces.JTIPoolObject;

import java.lang.reflect.Field;
import java.util.Map;


public abstract class JTDataInfo extends Object implements JTIPoolObject
{

	public JTDataInfo() 
	{
		super();
	}

	public void update(Object data) 
	{
		if(data instanceof JTDataInfo)
		{
			updateObject(data);
		}
		else
		{
//			Map<String, Object> dataMap = (Map<String, Object>) data;
//		    Set<String> keys = dataMap.keySet();
//			Class<? extends Object> cls = this.getClass();
//			Field[] list = cls.getDeclaredFields();
		}
	}
	
	private void updateObject(Object data)
	{
		Class<? extends Object> dataCls = data.getClass();
		Field[] list = dataCls.getDeclaredFields();
		try 
		{
			for (int i = 0; i < list.length; i++)
			{
				Field property = list[i];
				Field value =  dataCls.getField(property.getName());
				property.set(this, value.get(data));
			}
		} 
		catch (IllegalArgumentException | IllegalAccessException e) 
		{
			e.printStackTrace();
		} 
		catch (NoSuchFieldException e) 
		{
			e.printStackTrace();
		} 
		catch (SecurityException e) 
		{
			e.printStackTrace();
		}
	}

	public JTDataInfo clone()
	{
		return null;
	}

	public Map<String, Object> toMap()
	{
		return null;
	}
}
