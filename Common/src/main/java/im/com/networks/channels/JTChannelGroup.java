package im.com.networks.channels;


import im.com.gamedatas.JTDataInfo;
import im.com.networks.JTSendPackage;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class JTChannelGroup extends JTDataInfo
{
	private DefaultChannelGroup _channels = null;
	private Map<String, JTChannel> _channelMap = null;
//	private Logger logger = Logger.getLogger(JTChannelGroup.class);
	private Lock locker = null;
	public JTChannelGroup()
	{
		locker = new ReentrantLock();
		this._channelMap = new ConcurrentHashMap<String, JTChannel>();
		this._channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	}

	public void addChannel(JTChannel c)
	{
		locker.lock();
		try
		{
			Boolean flag = _channelMap.containsKey(c.uid);
			if (flag)
			{
//				logger.error("the channel realdy add to the channelgroup!");
			}
			_channelMap.put(c.uid, c);
			_channels.add(c.channel);
		}
		finally  
		{
			locker.unlock();
		}
	}

	public JTChannel getChannelInfo(String uid)
	{
		locker.lock();
		try
		{
			Boolean flag = _channelMap.containsKey(uid);
			if (flag)
			{
//				logger.error("the channel realdy add to the channelgroup!");
			}
			return _channelMap.get(uid);
		}
		finally  
		{
			locker.unlock();
		}
	}

	public void remove(JTChannel c)
	{
		locker.lock();
		try
		{
			Boolean flag = _channelMap.containsKey(c.uid);
			if (!flag)
			{
//				logger.error("cant find the uid:" + c.uid + "from the map");
			}
			_channelMap.remove(c.uid);
			_channels.remove(c.channel);
		}
		finally  
		{
			locker.unlock();
		}
	}

	public void sendNormalPackage(int protocol, Map<String, Object> dataMap)
	{
		JTSendPackage sendPackage = JTSendPackage.begin(_channels);
		sendPackage.writeProtocol(protocol);
		sendPackage.writeStatus(JTSendPackage.STATUS_NORMAL);
		sendPackage.writeContent(dataMap);
		sendPackage.send();
	}

	public void sendNormalPackage(int protocol)
	{
		JTSendPackage sendPackage = JTSendPackage.begin(_channels);
		sendPackage.writeProtocol(protocol);
		sendPackage.writeStatus(JTSendPackage.STATUS_NORMAL);
		sendPackage.send();
	}

	public void sendErrorPackage(int protocol, int errorCode)
	{
		JTSendPackage sendPackage = JTSendPackage.begin(_channels);
		sendPackage.writeProtocol(protocol);;
		sendPackage.writeStatus(JTSendPackage.STATUS_ERROR);
		sendPackage.writeCode(errorCode);
		sendPackage.send();
	}

	@Override
	public void recycle() 
	{

	}
}
