package com.example.app;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        // Load environment variables before Spring starts
        // Dotenv dotenv = Dotenv.configure()
        //         .filename(".env")
        //         .load();

        // Dotenv dotenv = Dotenv.configure()
        //         .directory("/home/ubuntu/JavaAPP")
        //         .filename(".env")
        //         .load();
        
        //Dotenv dotenv = Dotenv.configure()
        //        .directory("/home/ubuntu/JavaAPP")
        //        .filename(".env")
        //        .load();
        Dotenv dotenv = Dotenv.configure().load();
        System.setProperty("spring.datasource.url", dotenv.get("DB_URL"));
        System.setProperty("spring.datasource.username", dotenv.get("DB_USERNAME"));
        System.setProperty("spring.datasource.password", dotenv.get("DB_PASSWORD"));

        System.setProperty("aws.s3.bucket-name", dotenv.get("AWS_S3_BUCKET_NAME"));
        System.setProperty("aws.access-key", dotenv.get("AWS_ACCESS_KEY_ID"));
        System.setProperty("aws.secret-key", dotenv.get("AWS_SECRET_ACCESS_KEY"));
        System.setProperty("aws.region", dotenv.get("AWS_REGION"));

        SpringApplication.run(App.class, args);
    }
}
