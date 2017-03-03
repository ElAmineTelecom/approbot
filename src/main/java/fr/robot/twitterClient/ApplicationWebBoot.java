package fr.robot.twitterClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.context.request.RequestContextListener;

@EnableScheduling
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class ApplicationWebBoot {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationWebBoot.class, args);
	}

	/**
	 * To support the scoping of beans at the request, session, and global
	 * session levels (web-scoped beans), some minor initial configuration is
	 * required before you define your beans. (This initial setup is not
	 * required for the standard scopes, singleton and prototype.)
	 * 
	 */

	// twitter4j.oauth.consumer-key=mCjbPtHpgkZyqcLPRZyiNF7fk
	// twitter4j.oauth.consumer-secret=vpf28b5OUfIhjaeJL5acYTCbjDe5c191DdeGf2XyFN7okYk8lH
	// twitter4j.oauth.access-token=834532806785110017-64M3qKaKG4vkd0sFui5qoZ2azQ5qzVL
	// twitter4j.oauth.access-token-secret=t3DINQhbBa7aEamWSqyWneqyVenTs1xUrPFO3rPP4NPES

	@Bean
	public RequestContextListener requestContextListener() {
		return new RequestContextListener();
	}

}
