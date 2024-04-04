package com.sandesh.springreactive.controller;

import com.github.javafaker.Faker;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

@RestController
public class ReactiveController {

    public record ReactiveResponse(String instrument, String name) {}

    @GetMapping("/hello/{name}")
    public Mono<String> sayHello(@PathVariable String name) {
        return Mono.just(STR."Namaste \{name}");
    }

    @GetMapping(value = "/multiple/{name}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ReactiveResponse> greetMultiple(@PathVariable String name) {
        Faker faker = new Faker();
        Stream<String> stringStream = Stream.generate(() -> STR."\{faker.music().instrument()} \{name} !");
        return Flux.fromStream(stringStream)
                .map(val -> {
                    String[] splits = val.split(" ");
                    return new ReactiveResponse(splits[0], splits[1]);
                })
                .filter(rr -> !rr.instrument().equals("Violin"))
                .log()
                .delayElements(Duration.of(1, ChronoUnit.SECONDS));
    }
}
