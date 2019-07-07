package com.example.reactive.controller;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Date;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
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

    /*@RequestMapping(name = "/test", method = RequestMethod.GET)
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
        return Flux.zip(durationFlux, eventFlux)
            .map(Tuple2::getT2);
    }*/

    @GetMapping(path = "/stream-flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamFlux() {
        return Flux.interval(Duration.ofSeconds(1))
            .map(sequence -> "Flux - " + LocalTime.now()
                .toString());
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
                .data("SSE - " + LocalTime.now()
                    .toString())
                .build());
    }

    @GetMapping("/stream")
    public void consumeServerSentEvent() {
        
        WebClient client = WebClient.create("http://localhost:8080");
        
        ParameterizedTypeReference<ServerSentEvent<String>> type = new ParameterizedTypeReference<ServerSentEvent<String>>() {};

        Flux<ServerSentEvent<String>> eventStream = client.get()
            .uri("/stream-sse")
            .retrieve()
            .bodyToFlux(type);

        eventStream.subscribe(content -> System.out.println("Time: {} - event: name[{}], id [{}], content[{}] " + LocalTime.now() + content.event() + content.id() + content.data()), error -> System.out.println("Error receiving SSE: {}" + error),
            () -> System.out.println("Completed!!!"));

    }

}
