plugins {
	java
	id("org.springframework.boot") version "3.2.0" // Lưu ý: Spring Boot 4 chưa tồn tại (hiện tại mới tới 3.x), nếu bạn dùng bản 3.x hãy chọn version tương ứng
	id("io.spring.dependency-management") version "1.1.7"
	id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

// --- Cấu hình JavaFX plugin ---
javafx {
	version = "21.0.2"
	modules = listOf("javafx.controls", "javafx.fxml")
}

dependencies {
	// --- Database & Persistence ---
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	runtimeOnly("org.mariadb.jdbc:mariadb-java-client")

	// --- Utilities ---
	implementation("io.github.cdimascio:java-dotenv:5.2.2")
	implementation("org.hibernate.orm:hibernate-jcache")

	// --- Lombok ---
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	// --- Development Tools ---
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	developmentOnly("org.springframework.boot:spring-boot-docker-compose")

	// --- Testing ---
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testCompileOnly("org.projectlombok:lombok")
	testAnnotationProcessor("org.projectlombok:lombok")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}