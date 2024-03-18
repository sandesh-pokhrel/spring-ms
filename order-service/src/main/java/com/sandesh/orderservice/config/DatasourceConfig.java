package com.sandesh.orderservice.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DatasourceConfig {
    @Bean
    @Primary
    public AbstractRoutingDataSource dataSource(DsConfigProperties dsConfigProperties) {
        return new TenantAwareDataSource(dsConfigProperties);
    }

    private DataSource createDatasource(DsConfigProperties.DsProperties props) {
        return DataSourceBuilder
                .create(ClassLoader.getSystemClassLoader())
                .driverClassName(props.getDriverClassName())
                .url(props.getUrl())
                .username(props.getUsername())
                .password(props.getPassword())
                .build();
    }

    class TenantAwareDataSource extends AbstractRoutingDataSource {

        public TenantAwareDataSource(DsConfigProperties dsConfigProperties) {
            Map<Object, Object> targetDataSources = new HashMap<>();
            dsConfigProperties.getProps().forEach((k, v) -> {
                DataSource ds = createDatasource(v);
                targetDataSources.put(k, ds);
            });
            super.setTargetDataSources(targetDataSources);
        }

        @Override
        protected Object determineCurrentLookupKey() {
            return TenantContext.getCurrentTenant();
        }
    }
}
