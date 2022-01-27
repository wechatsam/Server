package im.mq.configs;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JTProducerConfig
{
    @Value("${kafka.producer.servers}")
    public String servers;

    @Value("${kafka.producer.retries}")
    public int retries;

    @Value("${kafka.producer.batch.size}")
    public int batchSize;

    @Value("${kafka.producer.linger}")
    public int linger;

    @Value("${kafka.producer.buffer.memory}")
    public int bufferMemory;

    @Value("#{'${producer.topics:}'.empty ? null : '${producer.topics:}'.split(',')}")
    private String[] topics;


    @Bean
    public JTProducerConfig producerConfig()
    {
        return new JTProducerConfig();
    }
}
