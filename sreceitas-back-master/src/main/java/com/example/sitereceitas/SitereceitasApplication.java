package com.example.sitereceitas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SitereceitasApplication {

	public static void main(String[] args) {
		SpringApplication.run(SitereceitasApplication.class, args);
	}

}
