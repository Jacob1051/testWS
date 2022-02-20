package com.server.signalisation;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.server.signalisation.services.FileStorageInterface;

@SpringBootApplication
public class SignalisationApplication{

	@Resource
	  FileStorageInterface storageService;
	
	public static void main(String[] args) {
		SpringApplication.run(SignalisationApplication.class, args);
	}
	
	@Bean
	public WebMvcConfigurer corsConfiguration() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedHeaders("*")
				.allowedMethods("*")
				.allowCredentials(false);
			}
		};
	}
}
