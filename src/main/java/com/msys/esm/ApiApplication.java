package com.msys.esm;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class ApiApplication {
    public static void main(String[] args) {
        System.out.println();
        SpringApplication.run(ApiApplication.class, args);
    }

}
