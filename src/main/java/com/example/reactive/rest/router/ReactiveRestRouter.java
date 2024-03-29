package com.example.reactive.rest.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.reactive.rest.handler.ReactiveRestHandler;

@Configuration
public class ReactiveRestRouter {

    @Bean
    public RouterFunction<ServerResponse> route(ReactiveRestHandler restHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/hello").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), restHandler::hello);
    }

}
