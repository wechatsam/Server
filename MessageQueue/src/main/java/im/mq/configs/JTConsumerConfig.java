package im.mq.configs;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JTConsumerConfig
{
    @Value("${kafka.consumer.servers}")
    public String servers;
    @Value("${kafka.consumer.enable.auto.commit}")
    public boolean enableAutoCommit;
    @Value("${kafka.consumer.session.timeout}")
    public String sessionTimeout;
    @Value("${kafka.consumer.auto.commit.interval}")
    public String autoCommitInterval;
    @Value("${kafka.consumer.group.id}")
    public String groupId;
    @Value("${kafka.consumer.auto.offset.reset}")
    public String autoOffsetReset;
    @Value("${kafka.consumer.concurrency}")
    public int concurrency;

    @Value("#{'${consumer.topics:}'.empty ? null : '${consumer.topics:}'.split(',')}")
    public String[] topics;

    @Bean
    public JTConsumerConfig consumerConfig()
    {
        return new JTConsumerConfig();
    }
}
