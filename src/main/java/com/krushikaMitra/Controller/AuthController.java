package com.krushikaMitra.Controller;
import com.krushikaMitra.DTO.RegisterRequest;
import com.krushikaMitra.Service.UserService;
import org.springframework.web.bind.annotation.*;
import com.krushikaMitra.DTO.LoginRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody RegisterRequest request) {
        userService.registerUser(request);
        return "User registered successfully";
    }
    @PostMapping("/login")
    public String loginUser(@RequestBody LoginRequest request) {
        return userService.loginUser(request);
    }
}