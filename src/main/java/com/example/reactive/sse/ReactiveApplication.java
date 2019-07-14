package com.example.reactive.sse;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.reactive.sse.client.ReactiveClient;

@SpringBootApplication
public class ReactiveApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        ReactiveClient client = new ReactiveClient();
        client.serverSentEventClient();

    }

}
