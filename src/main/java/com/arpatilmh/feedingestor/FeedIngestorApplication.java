package com.arpatilmh.feedingestor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FeedIngestorApplication {
    public static void main(String[] args) {
        SpringApplication.run(FeedIngestorApplication.class, args);
        System.out.println("Hello, I am sports feed ingestor application");
    }
}