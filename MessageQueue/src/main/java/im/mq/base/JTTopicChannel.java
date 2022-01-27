package im.mq.base;

import java.util.HashMap;
import java.util.Map;

public class JTTopicChannel
{
    protected Map<String, JTTopicProtocol> _topicMap = null;
    public JTTopicChannelManager owner = null;

    public JTTopicChannel()
    {
        super();
        this._topicMap = new HashMap<>();
    }

    public void bind(JTTopicChannelManager owner)
    {
        this.owner = owner;
    }

    public JTTopicProtocol add(String topic)
    {
        JTTopicProtocol channel = this._topicMap.get(topic);
        if (channel == null)
        {
            channel = new JTTopicProtocol();
            this._topicMap.put(topic, channel);
        }
        return channel;
    }

    public void registerTopicProtocol(String topic, int protocol)
    {
        JTTopicProtocol protocols = this.add(topic);
        protocols.add(protocol);
    }

    public JTTopicChannelManager getOwner()
    {
        return owner;
    }


}
