package im.servers.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages= {"com.im", "im.redisson"})
@SpringBootApplication
public class LoginServer
{
	public static void main(String[] args) {
		SpringApplication.run(LoginServer.class, args);
	}

}
