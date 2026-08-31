package com.example.desktop_java_swing;


import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.*;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = "com.example.desktop_java_swing.repository")
//@EnableRedisRepositories(basePackages = "com.example.desktop_java_swing.repository.cached")
//@EntityScan(basePackages = "com.example.desktop_java_swing.model")
public class DesktopJavaSwingApplication {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.load();
		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

		System.setProperty("java.awt.headless", "false");
		ConfigurableApplicationContext context = SpringApplication.run(DesktopJavaSwingApplication.class, args);

		EventQueue.invokeLater(() -> {
			FrmBooks frmBooks = context.getBean(FrmBooks.class);
			frmBooks.setVisible(true);
		});
	}

}
