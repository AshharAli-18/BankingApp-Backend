package com.Training.BankingApp;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankingAppApplication {

	public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();

        System.setProperty("DB_PROD_USERNAME", dotenv.get("DB_PROD_USERNAME"));
        System.setProperty("DB_PROD_PASSWORD", dotenv.get("DB_PROD_PASSWORD"));

        System.setProperty("DB_TEST_USERNAME", dotenv.get("DB_TEST_USERNAME"));
        System.setProperty("DB_TEST_PASSWORD", dotenv.get("DB_TEST_PASSWORD"));

        SpringApplication.run(BankingAppApplication.class, args);

	}

}
