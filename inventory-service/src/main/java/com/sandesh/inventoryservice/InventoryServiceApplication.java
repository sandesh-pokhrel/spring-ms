package com.sandesh.inventoryservice;

import com.sandesh.inventoryservice.config.RoutesConfigProperties;
import com.sandesh.inventoryservice.model.Route;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.Map;

@EnableDiscoveryClient
@SpringBootApplication
@EnableConfigurationProperties
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(RoutesConfigProperties props) {
        return (args) -> {
            props.getRoutes().forEach((k, v) -> {
                System.out.println("For: " + k);
                System.out.println("Url: " + v.getUrl() + " ---- " + "Name: " + v.getName());
                System.out.println(" ----------- ");
            });
        };
    }

}
