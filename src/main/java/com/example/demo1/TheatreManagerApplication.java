package com.example.demo1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TheatreManagerApplication extends SpringBootServletInitializer{
    public static void main(String[] args) {
        SpringApplication.run(TheatreManagerApplication.class, args);
    }
}
