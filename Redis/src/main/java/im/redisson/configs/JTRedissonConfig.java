package im.redisson.configs;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
public class JTRedissonConfig
{
    @Value("${spring.redis.redisson.file}")
    public String url;

    @Bean
    public RedissonClient redissonClient() throws IOException {
        Config config = Config.fromYAML(new ClassPathResource(url).getInputStream());
        return Redisson.create(config);
    };
}
