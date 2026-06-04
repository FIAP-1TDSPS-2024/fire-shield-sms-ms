package br.com.catech.fire_shield_sms_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class FireShieldSmsMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FireShieldSmsMsApplication.class, args);
	}

}
