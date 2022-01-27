package im.mq.base;

import java.util.ArrayList;
import java.util.List;

public class JTProtocol
{
    public int protocol = 0;

    public List<String> topics = null;

    public JTProtocol()
    {
        this.topics = new ArrayList<String>();
    }

    public void setup(int protocol, String topic)
    {
        int index = this.topics.indexOf(topic);
        if (index == -1)
        {
                    this.topics.add(topic);
        }
        this.protocol = protocol;
    }
}
