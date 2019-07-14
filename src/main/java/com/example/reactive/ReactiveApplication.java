package com.example.reactive;

import java.time.LocalTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class ReactiveApplication implements CommandLineRunner{

    public static void main(String[] args) {
        SpringApplication.run(ReactiveApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        

       
    }

}
