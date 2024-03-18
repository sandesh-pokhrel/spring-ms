package com.sandesh.orderservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "custom-datasource")
public class DsConfigProperties {

    private Map<String, DsProperties> props;


    @Getter
    @Setter
    static class DsProperties {
        private String url;
        private String username;
        private String password;
        private String driverClassName;
    }
}
