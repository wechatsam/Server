package im.servers.register.networks.mq;

import im.mq.JTMessageListener;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.stereotype.Service;

@Service
@EnableKafka
public class JTReceiveMessageListener extends JTMessageListener
{
    @Override
    protected void readMessage(String topic, String msg) {

    }

}
