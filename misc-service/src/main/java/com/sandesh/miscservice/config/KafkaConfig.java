package com.sandesh.miscservice.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@Slf4j
@Configuration
@EnableKafkaStreams
public class KafkaConfig {

    @Autowired
    public void process(StreamsBuilder streamsBuilder) {
        streamsBuilder.stream("lecturer", Consumed.with(Serdes.Long(), Serdes.String()))
                .foreach((k, v) -> log.info("Key: {} .. Value: {}", k, v));
    }
}
