package com.hy.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class LoveApplication {


    public static void main(String[] args) {
        SpringApplication.run(LoveApplication.class, args);
    }


}
