package com.apicollabdev.odk.collabdev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan("com.apicollabdev.odk.collabdev")
public class CollabdevApplication {

    public static void main(String[] args) {
        SpringApplication.run(CollabdevApplication.class, args);

    }
}
