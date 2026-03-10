package com.tfg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger logger = LoggerFactory.getLogger(ServiciosApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ServiciosApplication.class, args);
		logger.info("\n========================================");
		logger.info("🚀 Aplicación iniciada correctamente");
		logger.info("📍 API disponible en: http://localhost:8080");
		logger.info("📊 Base de datos: db_servicios");
		logger.info("========================================\n");
	}

	/**
	 * Bean para generar contraseñas hasheadas Útil para crear nuevos usuarios con
	 * contraseñas seguras
	 */
	public static void generatePassword(String rawPassword) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		logger.info("Contraseña original: " + rawPassword);
		logger.info("Contraseña hasheada: " + encoder.encode(rawPassword));
	}
}