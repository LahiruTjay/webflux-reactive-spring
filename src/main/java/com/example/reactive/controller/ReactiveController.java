package com.example.reactive.controller;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Date;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.reactive.model.WebFluxEvent;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@RestController
public class ReactiveController {

    @RequestMapping(name = "/test", method = RequestMethod.GET)
    public void sentEventStream() {
        WebClient.create("http://localhost:8080")
            .get()
            .uri("/events")
            .accept(MediaType.TEXT_EVENT_STREAM)
            .retrieve()
            .bodyToFlux(WebFluxEvent.class)
            .subscribe(event -> System.out.println(event.toString()));
    }

    @GetMapping(name = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<WebFluxEvent> getEventStream() {
        Flux<WebFluxEvent> eventFlux = Flux.fromStream(Stream.generate(() -> new WebFluxEvent(new Random().nextLong(), new Date())));
        Flux<Long> durationFlux = Flux.interval(Duration.ofSeconds(1));
        return Flux.zip(eventFlux, durationFlux).map(Tuple2::getT1);
    }

    @GetMapping(path = "/stream-flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamFlux() {
        return Flux.interval(Duration.ofSeconds(1)).map(sequence -> "Flux - " + LocalTime.now().toString());
    }

}
