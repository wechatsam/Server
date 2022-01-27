package im.mq.base;

import java.util.ArrayList;
import java.util.List;

public class JTTopicProtocol
{
    private List<Integer> protocols = null;

    private String _topic = null;

    public JTTopicProtocol(String topic)
    {
        this.protocols = new ArrayList<Integer>();
        this.bind(topic);
    }

    public JTTopicProtocol()
    {
        this.protocols = new ArrayList<Integer>();
    }

    public void bind(String topic)
    {
        this._topic = topic;
    }

    public boolean add(int protocol)
    {
        int index = this.protocols.indexOf(protocol);
        if (index > -1) return false;
        this.protocols.add(protocol);
        return true;
    }

    public boolean check(int protocol)
    {
        return this.protocols.contains(protocol);
    }

    public String topic()
    {
        return this._topic;
    }
}
