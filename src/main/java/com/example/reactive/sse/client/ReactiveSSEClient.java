package com.example.reactive.sse.client;

import java.time.LocalTime;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

public class ReactiveSSEClient {

    public void serverSentEventClient() {

        WebClient client = WebClient.create("http://localhost:8080");

        Flux<ServerSentEvent<String>> eventStream = client.get()
            .uri("/stream-sse")
            .retrieve()
            .bodyToFlux(new ParameterizedTypeReference<ServerSentEvent<String>>() {});

        eventStream.subscribe(content -> System.out.println(LocalTime.now() + content.event() + content.id() + content.data()), error -> System.out.println("Error receiving SSE: {}" + error), () -> System.out.println("Completed!!!"));

    }

}
