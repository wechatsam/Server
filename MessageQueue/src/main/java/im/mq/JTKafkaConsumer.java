package im.mq;

import im.mq.configs.JTConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class JTKafkaConsumer
{
    @Autowired
    private JTConsumerConfig consumerConfig;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Bean
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(consumerConfig.concurrency);
        factory.getContainerProperties().setPollTimeout(3000);
        // ---设置ReplyTemplate参数，将kafkaTemplate对象加入
        factory.setReplyTemplate(kafkaTemplate);
        return factory;
    }

    @Bean
    public ConsumerFactory<Integer, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    public Map<String, Object> consumerConfigs()
    {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumerConfig.servers);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, consumerConfig.enableAutoCommit);
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, consumerConfig.autoCommitInterval);
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, consumerConfig.sessionTimeout);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, consumerConfig.groupId);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, consumerConfig.autoOffsetReset);
        return properties;
    }



}