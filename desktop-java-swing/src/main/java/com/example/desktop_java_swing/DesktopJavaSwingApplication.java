package com.example.desktop_java_swing;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.desktop_java_swing.repository")
//@EnableRedisRepositories(basePackages = "com.example.desktop_java_swing.repository.cached")
@EntityScan(basePackages = "com.example.desktop_java_swing.model")
public class DesktopJavaSwingApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
		SpringApplication.run(DesktopJavaSwingApplication.class, args);
	}

}
