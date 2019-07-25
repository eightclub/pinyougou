package cn.itcast.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/info")
    public String info(){
        return "Hello Spring Boot!";
    }
}
