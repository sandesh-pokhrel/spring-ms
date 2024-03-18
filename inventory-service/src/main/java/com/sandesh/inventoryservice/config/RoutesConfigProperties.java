package com.sandesh.inventoryservice.config;

import com.sandesh.inventoryservice.model.Route;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "hint")
public class RoutesConfigProperties {

    private Map<String, Route> routes;
}
