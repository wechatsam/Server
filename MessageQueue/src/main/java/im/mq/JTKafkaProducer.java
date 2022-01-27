package im.mq;

import im.mq.configs.JTProducerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class JTKafkaProducer
{
    @Autowired
    private JTProducerConfig producerConfig;

    public Map<String, Object> producerConfigs()
    {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, producerConfig.servers);
        properties.put(ProducerConfig.RETRIES_CONFIG, producerConfig.retries);
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, producerConfig.batchSize);
        properties.put(ProducerConfig.LINGER_MS_CONFIG, producerConfig.linger);
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, producerConfig.bufferMemory);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return properties;
    }

    public ProducerFactory<String, String> producerFactory()
    {
        DefaultKafkaProducerFactory factory = new DefaultKafkaProducerFactory<>(producerConfigs());
        return factory;
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate()
    {
        return new KafkaTemplate<String, String>(producerFactory());
    }
}