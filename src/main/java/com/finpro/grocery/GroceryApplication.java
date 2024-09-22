package com.finpro.grocery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.xendit.Xendit;

@SpringBootApplication
public class GroceryApplication {

	public static void main(String[] args) {
		String apiKey = System.getProperty("xendit.secret_api_key");
		Xendit.Opt.setApiKey(apiKey);

		SpringApplication.run(GroceryApplication.class, args);
	}

}
