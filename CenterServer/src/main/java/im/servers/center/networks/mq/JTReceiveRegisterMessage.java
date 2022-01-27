package im.servers.center.networks.mq;

import im.com.events.JTFunctionManager;
import im.mq.JTMessageListener;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.stereotype.Service;


@Service
@EnableKafka
public class JTReceiveRegisterMessage extends JTMessageListener
{
    @Override
    protected void readMessage(String topic, String msg)
    {
        JTFunctionManager.execute(topic, msg);
    }

//    /**
//     * kafka监听
//     * @return
//     */
//    @Bean
//    public JTReceiveRegisterMessage listener()
//    {
//        return new JTReceiveRegisterMessage();
//    }
}
