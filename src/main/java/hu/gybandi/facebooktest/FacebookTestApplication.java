package hu.gybandi.facebooktest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.social.config.annotation.EnableSocial;

@EnableSocial
@SpringBootApplication
public class FacebookTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(FacebookTestApplication.class, args);
	}
}