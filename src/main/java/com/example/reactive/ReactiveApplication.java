package com.example.reactive;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.reactive.sse.client.ReactiveSSEClient;

@SpringBootApplication
public class ReactiveApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        ReactiveSSEClient client = new ReactiveSSEClient();
        client.serverSentEventClient();

    }

}
