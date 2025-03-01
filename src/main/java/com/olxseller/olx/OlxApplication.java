package com.olxseller.olx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = "com.olxseller.olx")
// @EnableJpaAuditing
public class OlxApplication{

	
	   public static void main(String[] args) {
	      SpringApplication.run(OlxApplication.class, args);
	   }

}
