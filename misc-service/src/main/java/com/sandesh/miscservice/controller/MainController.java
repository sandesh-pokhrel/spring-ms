package com.sandesh.miscservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/main")
public class MainController {

    @GetMapping("/hello")
    public String greet() {
        return "Hi Dude!";
    }
}
