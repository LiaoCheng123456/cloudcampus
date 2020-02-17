package com.rh.cloudcampus;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author liaocheng
 * @date 2020-1-15 17:20
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableFeignClients(basePackages = {"service"})
public class TakeOutRunApplication {

    public static void main(String[] args) {
        SpringApplication.run(TakeOutRunApplication.class, args);
    }

}
