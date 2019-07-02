package com.example.reactive.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReactiveController {

    @RequestMapping(name = "event", method = RequestMethod.GET)
    public String sentEventStream() {
        return "Working";
    }

}
