package im.mq.base;

import java.util.HashMap;
import java.util.Map;

public class JTTopicChannelManager
{
    protected Map<Integer, JTProtocol> _protocolMap = null;
    protected JTTopicChannel _pushChannel = null;
    protected JTTopicChannel _pullChannel = null;

    public JTTopicChannelManager()
    {
        this._protocolMap = new HashMap<Integer, JTProtocol>();
        this._pullChannel = new JTTopicChannel();
        this._pushChannel = new JTTopicChannel();
    }

    public JTTopicProtocol registerPushProtocols(String topic, int[] protocols)
    {
        JTTopicProtocol topicProtocol = this._pushChannel.add(topic);
        for (int i = 0; i < protocols.length; i++)
        {
            int protocol = protocols[i];
            topicProtocol.add(protocol);
        }
        return topicProtocol;
    }

    public void registerProtocols(String topic, int[] protocols)
    {
            this.registerPullProtocols(topic, protocols);
            this.registerPushProtocols(topic, protocols);
    }

    public JTTopicProtocol registerPullProtocols(String topic, int[] protocols)
    {
        JTTopicProtocol topicProtocol = this._pullChannel.add(topic);
        for (int i = 0; i < protocols.length; i++)
        {
            int protocol = protocols[i];
            topicProtocol.add(protocol);
        }
        return topicProtocol;
    }

    public JTTopicProtocol registerPushProtocol(String topic, int protocol)
    {
        JTTopicProtocol protocols = this._pushChannel.add(topic);
        protocols.add(protocol);
        return protocols;
    }

    public JTTopicProtocol registerPullProtocol(String topic, int protocol)
    {
        JTTopicProtocol protocols = this._pullChannel.add(topic);
        protocols.add(protocol);
        return protocols;
    }

    public void registerProtocol(String topic, int protocol)
    {
        this.registerPullProtocol(topic, protocol);
        this.registerPushProtocol(topic, protocol);
    }
}
