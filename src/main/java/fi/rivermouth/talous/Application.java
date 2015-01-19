package fi.rivermouth.talous;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import fi.rivermouth.talous.profiles.DevProfile;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application  {
	
	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
	
}
