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
		// 1. Load biến môi trường .env nếu có
		Dotenv dotenv = Dotenv.load();
		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

		// 2. QUAN TRỌNG: Tắt chế độ Headless để Swing có thể hiển thị giao diện
		System.setProperty("java.awt.headless", "false");

		// 3. Chỉ gọi SpringApplication.run() MỘT LẦN duy nhất và lưu lại Context
		ConfigurableApplicationContext context = SpringApplication.run(DesktopJavaSwingApplication.class, args);

		// 4. Mở cửa sổ Swing (FrmBooks) trên Event Dispatch Thread của Swing
		EventQueue.invokeLater(() -> {
			FrmBooks frmBooks = context.getBean(FrmBooks.class);
			frmBooks.setVisible(true);
		});
	}

}
