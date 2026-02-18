package com.tfg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.tfg")
@EnableJpaRepositories(basePackages = "com.tfg.repository")
@EnableTransactionManagement
public class ServiciosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiciosApplication.class, args);
		System.out.println("\n========================================");
		System.out.println("🚀 Aplicación iniciada correctamente");
		System.out.println("📍 API disponible en: http://localhost:8080");
		System.out.println("📊 Base de datos: db_servicios");
		System.out.println("========================================\n");
	}

	/**
	 * Bean para generar contraseñas hasheadas Útil para crear nuevos usuarios con
	 * contraseñas seguras
	 */
	public static void generatePassword(String rawPassword) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println("Contraseña original: " + rawPassword);
		System.out.println("Contraseña hasheada: " + encoder.encode(rawPassword));
	}
}