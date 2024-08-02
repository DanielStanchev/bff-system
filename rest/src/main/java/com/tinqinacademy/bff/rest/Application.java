package com.tinqinacademy.bff.rest;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;



@Slf4j
@ComponentScan(basePackages = "com.tinqinacademy.bff")
@EntityScan(basePackages = "com.tinqinacademy.bff.persistence.entity")

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}
