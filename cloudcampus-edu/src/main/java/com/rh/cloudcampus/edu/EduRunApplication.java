package com.rh.cloudcampus.edu;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"service"})
public class EduRunApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduRunApplication.class, args);
    }

}
