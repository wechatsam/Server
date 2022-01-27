package im.com.networks;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "netty")
public class JTServerConfig
{
    private String address;

    private int port;
}
