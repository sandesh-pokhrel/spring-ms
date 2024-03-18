package com.sandesh.inventoryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.experimental.boot.server.exec.CommonsExecWebServerFactoryBean;
import org.springframework.experimental.boot.test.context.DynamicProperty;
import org.springframework.experimental.boot.test.context.EnableDynamicProperty;

@TestConfiguration
@EnableDynamicProperty
public class TestInventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication
                .from(InventoryServiceApplication::main)
                .with(TestInventoryServiceApplication.class)
                .run(args);
    }

    @Bean
    @DynamicProperty(name = "eureka.client.service-url.defaultZone", value = "'http://localhost:'+port+'/eureka/'")
    static CommonsExecWebServerFactoryBean messagesApiServer() {
        return CommonsExecWebServerFactoryBean.builder()
                .mainClass("org.springframework.boot.loader.launch.JarLauncher")
                .classpath((cp) -> cp
                        .files("../discovery-service/target/discovery-service-0.0.1-SNAPSHOT.jar")
                );
    }
}
