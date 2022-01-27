package im.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class JTMessageSender
{
    @Autowired
    private KafkaTemplate kafkaTemplate;

    public static JTMessageSender instance;

    @PostConstruct
    public void init()
    {
        JTMessageSender.instance = this;
    }

    public static void send(String topic, String msg)
    {
        JTMessageSender.instance.kafkaTemplate.send(topic, msg);
        logger.info("send message :" + ++times);
    }

    private static final Logger logger = LoggerFactory.getLogger(JTMessageSender.class);

    private static int times = 0;

//    @Scheduled(fixedDelay = 1000)
//    public void login() throws IOException
//    {
//        String value = "send Login Message to MQ Message Queue!";
//        times ++;
//        if (times >= 10)  return;
//        for (int i = 0; i<=100000000; i++)
//        {
////            kafkaTemplate.send("login", value);
//
//            kafkaTemplate.send("login", value);
//            logger.info(value);
//        }
//    }

//    @Scheduled(fixedDelay = 4000)
//    public void login() throws IOException
//    {
//        String value = "send Chat Message to MQ Message Queue!";
//        for (int i = 0; i<=10; i++)
//        {
//            kafkaTemplate.send("chat", value);
//            logger.info(value);
//        }
//    }


}
