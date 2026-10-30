package com.krushikaMitra.Controller;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api")
public class TestController {
    @GetMapping("/test")
    public String testEndpoint() {
        return "JWT Test endpoint is working!";
    }
}