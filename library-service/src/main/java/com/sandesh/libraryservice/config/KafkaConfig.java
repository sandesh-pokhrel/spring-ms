package com.sandesh.libraryservice.config;

import com.sandesh.libraryservice.model.Lecturer;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    public static final String LECTURER_TOPIC = "lecturer";
    public static final String BOOTSTRAP_SERVER_ADDRESS = "localhost:9092";

    @Bean
    public NewTopic lecturerTopic() {
        return TopicBuilder.name(LECTURER_TOPIC).build();
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER_ADDRESS);
        return new KafkaAdmin(configs);
    }

    @Bean
    public ProducerFactory<Long, Lecturer> producerFactory() {
        Map<String, Object> configMap = Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER_ADDRESS,
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class
        );
        return new DefaultKafkaProducerFactory<>(configMap);
    }

    @Bean
    public KafkaTemplate<Long, Lecturer> lecturerKafkaTemplate(ProducerFactory<Long, Lecturer> lecturerProducerFactory) {
        return new KafkaTemplate<>(lecturerProducerFactory);
    }
}
