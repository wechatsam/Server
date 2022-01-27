package im.com.common;

import java.util.Date;

public class JTLogger
{
    private static final String LOG_INFO= "[INFO] :";
    @SuppressWarnings("unused")
	private static final String LOG_ASSET= "[ASSET] :";
    @SuppressWarnings("unused")
	private static final String LOG_DEBUG= "[DEBUG] :";
    
    public static void info(Object msg)
    {
    	JTLogger.print(JTLogger.LOG_INFO, msg);
    }

    public static void idebug(Object data)
    {
//    	JTLogger.print(JTLogger.LOG_DEBUG, JSON.stringify(data));
    }

    public static void asserts(Boolean flag, Object msg)
    {
        if (!flag) throw new Error(msg.toString());
    }
 
    @SuppressWarnings("deprecation")
	private static void print(String type, Object msg)
    {
        Date date = new Date();
        int hour = date.getHours();
        int minutes = date.getMinutes();
        int seconds= date.getSeconds();
        String time = hour + ":" + minutes + ":" + seconds + " >>>> ";
        System.out.print(type + time + msg + "\n");
    }
}
