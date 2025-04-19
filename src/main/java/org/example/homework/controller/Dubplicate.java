package org.example.homework.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloWorldController {

    @GetMapping("/hello")
    public String sayHello() {
        // Duplicating the return statement 4 times
        return "Hello, World!"; // Line 1
        // Duplicated Line 2
        // Line 2
        // Duplicated Line 3
        return "Hello, World!"; // Line 3
        // Duplicated Line 4
        return "Hello, World!"; // Line 4
    }
}
