package com.example.demo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HolaController {
    @GetMapping("/hola")
    public String hola() {
        return "¡Hola desde Docker!";
    }

    @GetMapping("/info")
    public String info() {
        return "Java version: " + System.getProperty("java.version")
                + " | OS: " + System.getProperty("os.name");
    }
}