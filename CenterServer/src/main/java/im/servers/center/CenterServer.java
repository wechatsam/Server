package im.servers.center;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude= DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"im.redisson", "im.com", "im.mq", "im.servers.center"})
@MapperScan({"im.servers.center.dao.mappers"})
public class CenterServer 
{
	public static void main(String[] args) {
		SpringApplication.run(CenterServer.class, args);
	}
}
