package com.finpro.grocery;

import com.finpro.grocery.config.RsaKeyConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.xendit.Xendit;


@EnableConfigurationProperties(RsaKeyConfigProperties.class)
@SpringBootApplication
public class GroceryApplication {

	public static void main(String[] args) {
		String apiKey = System.getProperty("xendit.secret_api_key");
		Xendit.Opt.setApiKey(apiKey);

		SpringApplication.run(GroceryApplication.class, args);
	}

}
