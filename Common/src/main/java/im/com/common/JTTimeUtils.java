package im.com.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JTTimeUtils 
{
		private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		public static String getLocalTime()
		{
			 return format.format(new Date());
		}
}
