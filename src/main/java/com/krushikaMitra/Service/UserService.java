package com.krushikaMitra.Service;
import com.krushikaMitra.DTO.LoginRequest;
import com.krushikaMitra.DTO.RegisterRequest;
public interface UserService {
    void registerUser(RegisterRequest request);
    String loginUser(LoginRequest request);
}