package im.com.networks;


import com.alibaba.fastjson.JSON;
import im.com.interfaces.JTIPool;
import im.com.interfaces.JTIPoolObject;
import im.com.pools.JTPool;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class JTSendPackage implements JTIPoolObject
{
	private Object content = new HashMap<String, Object>();
	private Map<String, Object> data = new HashMap<String, Object>();
	private Object protocol;
	private Comparable<?> channel = null;
	private int errorCode = 0;
	private int status = 0;
	
	public static int STATUS_NORMAL = 1;
	public static int STATUS_ERROR = 0;
	
	public static Logger logger = LoggerFactory.getLogger(JTSendPackage.class);
//	

	public void writeProtocol(int protocol)
	{
		this.protocol = protocol;
	}

	public void writeProtocol(String protocol)
	{
		this.protocol = protocol;
	}

	public void writeStatus(int status)
	{
		this.status = status;
	}
	
	public void writeContent(Map<String, Object> dataMap)
	{
		this.content = dataMap;
	}
	
	public void writeContent(Object data)
	{
		this.content = data;
	}
	
	public void writeCode(int errorCode)
	{
		this.errorCode = errorCode;
	}
	
	@SuppressWarnings("unchecked")
	public void write(String key, Object value)
	{
		 ((Map<String, Object>) content).put(key, value);
	}   

	public String send()
	{
		this.data.put("protocol", this.protocol);
		this.data.put("status", this.status);
		if (this.status == JTSendPackage.STATUS_NORMAL)
		{
			this.data.put("content", this.content);
		}
		else
		{
			this.data.put("errorCode", this.errorCode);
		}
		String c = JSON.toJSONString(this.data);
		if (channel instanceof Channel)
		{
			((Channel) channel).writeAndFlush(new TextWebSocketFrame(c));
		}
		else if (channel instanceof ChannelGroup)
		{
			((ChannelGroup) channel).writeAndFlush(new TextWebSocketFrame(c));
		}
		JTSendPackage.logger.info("[sendPackage.send] : protocol:  " + this.protocol +   "   content: " + c);
		return c;
	}

	public void setup(Comparable<?> channel)
	{
		this.channel = channel;
	}

	public void recycle()
	{
		this.content = new HashMap<String, Object>();
		this.channel = null;
		this.data = new HashMap<String, Object>();
		this.protocol = 0;
		this.status = this.errorCode = 0;
	}

	private static JTIPool<JTIPoolObject> pool = JTPool.instance(JTSendPackage.class);
	public static JTSendPackage begin(Comparable<?> channel)
	{
		JTSendPackage sendPackage = (JTSendPackage) JTSendPackage.pool.get();
		sendPackage.setup(channel);
		return sendPackage;
	}

	public static void put(JTSendPackage sendPackage)
	{
		JTSendPackage.pool.put(sendPackage);
	}
	
	public static void sendNormalPackage(int protocol, Comparable<?> channel)
	{
		JTSendPackage sendPackage = JTSendPackage.begin(channel);
		sendPackage.writeProtocol(protocol);;
		sendPackage.writeStatus(JTSendPackage.STATUS_NORMAL);
		sendPackage.send();
	}
	
	public static void sendNormalPackage(int protocol, Object data, Comparable<?> channel)
	{
		JTSendPackage sendPackage = JTSendPackage.begin(channel);
		sendPackage.writeProtocol(protocol);;
		sendPackage.writeStatus(JTSendPackage.STATUS_NORMAL);
		sendPackage.writeContent(data);
		sendPackage.send();
	}
	
	public static String sendErrorPackage(int protocol, int errorCode, Comparable<?> channel)
	{
		JTSendPackage sendPackage = JTSendPackage.begin(channel);
		sendPackage.writeProtocol(protocol);
		sendPackage.writeStatus(JTSendPackage.STATUS_ERROR);
		sendPackage.writeCode(errorCode);
		return sendPackage.send();
	}

	public static String sendErrorPackage(String protocol, int errorCode, Comparable<?> channel)
	{
		JTSendPackage sendPackage = JTSendPackage.begin(channel);
		sendPackage.writeProtocol(protocol);
		sendPackage.writeStatus(JTSendPackage.STATUS_ERROR);
		sendPackage.writeCode(errorCode);
		return sendPackage.send();
	}
}
