package im.mq;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.Optional;

public abstract class JTMessageListener
{
    protected Logger logger= LoggerFactory.getLogger(JTMessageListener.class);

    private int times = 0;
    @KafkaListener(topics = {"${kafka.consumer.topics}"}, groupId = "redisson")
    protected void messageListener(ConsumerRecord<?, ?> record)
    {
            Optional message = Optional.ofNullable(record.value());
            if (message.isPresent())
            {
                String value = (String) message.get();
                logger.info("receive" + ++this.times + "    Message from ：   " + record.topic() + "____partition       :" + record.partition() + "    message:  "+ message);
                this.readMessage(record.topic(), value);
            }
    }

    protected abstract void readMessage(String topic, String msg);

//    /**
//     * 实时获取kafka数据(生产一条，监听生产topic自动消费一条)
//     * @param record
//     * @throws IOException
//     * @return
//     */
//    @KafkaListener(topics = {"login"}, groupId = "redisson")
////    @KafkaListener(groupId = "redisson", topicPartitions = {
////            @TopicPartition(topic = "login", partitions = {"0"}),
////            @TopicPartition(topic = "chat", partitions = {"0", "1"})
////    })
////    @KafkaListener(groupId = "redisson", topicPartitions = { @TopicPartition(topic = "login", partitions = {"0"})})
//    @SendTo("chat")
//    public String receiveLoginMessage(ConsumerRecord<?, ?> record) throws IOException
//    {
//        Optional message = Optional.ofNullable(record.value());
//        String value = null;
//        if (message.isPresent())
//        {
//            value = (String) message.get();
//            logger.info("message:  receive Message from      ：" + record.topic() + "___________partition       :" + record.partition());
//        }
//
//        return value;
//    }
//
//    @KafkaListener(topics = "chat",groupId = "redisson")
//    public void receiveChatMessage(ConsumerRecord<?, ?> record) throws IOException
//    {
//        Optional message = Optional.ofNullable(record.value());
//        if (message.isPresent())
//        {
//            String value = (String) message.get();
//            logger.info("message:  receive Message from      ：" + record.topic() + "___________partition       :" + record.partition());
//        }
//    }


}
