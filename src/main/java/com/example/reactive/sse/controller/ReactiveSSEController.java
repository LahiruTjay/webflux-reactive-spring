package com.example.reactive.sse.controller;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class ReactiveSSEController {

    @GetMapping(path = "/stream-flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamFlux() {
        return Flux.interval(Duration.ofSeconds(1))
            .map(sequence -> "Flux - " + LocalTime.now().toString());
    }

    @GetMapping(value = "/notifyonEvent", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getData() {
        Random r = new Random();
        int low = 0;
        int high = 50;
        return Flux.fromStream(Stream.generate(() -> r.nextInt(high - low) + low)
            .map(s -> String.valueOf(s))
            .peek((msg) -> {
                System.out.println(msg);
            }))
            .map(s -> s)
            .delayElements(Duration.ofSeconds(1));
    }

    @GetMapping("/stream-sse")
    public Flux<ServerSentEvent<String>> streamEvents() {
        return Flux.interval(Duration.ofSeconds(1))
            .map(sequence -> ServerSentEvent.<String> builder()
                .id(String.valueOf(sequence))
                .event("periodic-event")
                .data("SSE - " + LocalTime.now().toString())
                .build());
    }

}
