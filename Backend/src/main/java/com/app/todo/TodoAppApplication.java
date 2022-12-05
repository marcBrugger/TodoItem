package com.app.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodoAppApplication {
	//First steps:
		// 1. open docker (desktop) and start the container "backend"
			// docker run --name todoDB -e MYSQL_ROOT_PASSWORD=t******* -d -e MYSQL_DATABASE=todo -p 3307:3306 mysql:8.0.31
		// 2. open a terminal
		// 3. enter -> docker compose up
		// 4. in the Backend folder start the app via -> mvn spring-boot:run
		// You can now use the SWAGGER UI to CRUD Todo Items into the DB
		
	// http://localhost:8080/swagger-ui/index.html
	// http://localhost:8080/v3/api-docs/

	// https://spring.io/guides/gs/accessing-data-mysql/

	public static void main(String[] args) {
		SpringApplication.run(TodoAppApplication.class, args);
	}
}
