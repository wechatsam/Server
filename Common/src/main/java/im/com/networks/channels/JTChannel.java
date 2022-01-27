package im.com.networks.channels;



import im.com.events.JTEventDispatcher;
import im.com.networks.JTSendPackage;
import io.netty.channel.Channel;

import java.util.Map;

public abstract class JTChannel extends JTEventDispatcher
{
	public Channel channel = null;
	public String uid = null;
	
	public void setup(Channel channel)
	{
		this.channel = channel;
	}
	
	public void sendNormalPackage(int protocol, Map<String, Object> dataMap)
	{
		JTSendPackage sendPackage = JTSendPackage.begin(channel);
		sendPackage.writeProtocol(protocol);;
		sendPackage.writeStatus(JTSendPackage.STATUS_NORMAL);
		sendPackage.writeContent(dataMap);
		sendPackage.send();
	}
	
	public void sendNormalPackage(int protocol, Object data)
	{
		JTSendPackage sendPackage = JTSendPackage.begin(channel);
		sendPackage.writeProtocol(protocol);;
		sendPackage.writeStatus(JTSendPackage.STATUS_NORMAL);
		sendPackage.writeContent(data);
		sendPackage.send();
	}
	
	
	public void sendNormalPackage(int protocol)
	{
		JTSendPackage sendPackage = JTSendPackage.begin(channel);
		sendPackage.writeProtocol(protocol);
		sendPackage.writeStatus(JTSendPackage.STATUS_NORMAL);
		sendPackage.send();
	}
	
	public void sendErrorPackage(int protocol, int errorCode)
	{
		JTSendPackage sendPackage = JTSendPackage.begin(channel);
		sendPackage.writeProtocol(protocol);;
		sendPackage.writeStatus(JTSendPackage.STATUS_ERROR);
		sendPackage.writeCode(errorCode);
		sendPackage.send();
	}
}
