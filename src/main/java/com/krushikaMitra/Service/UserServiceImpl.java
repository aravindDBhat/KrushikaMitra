package com.krushikaMitra.Service;
import com.krushikaMitra.Service.UserService;
import com.krushikaMitra.DTO.RegisterRequest;
import com.krushikaMitra.DTO.LoginRequest;
import com.krushikaMitra.Entity.Role;
import com.krushikaMitra.Entity.User;
import com.krushikaMitra.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.krushikaMitra.JWT.JWTService;
import com.krushikaMitra.Exception.UserAlreadyExistException;
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public void registerUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistException("User with email " + request.getEmail() + " already exists");
        }
        if (request.getRole() == Role.ADMIN) {
            throw new IllegalArgumentException("Admin registration is not allowed.");
        }
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .district(request.getDistrict())
                .state(request.getState())
                .role(Role.FARMER) // Default role is FARMER
                .active(true)
                .build();
        userRepository.save(user);
    }
    public String loginUser(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        String token = jwtService.generateToken(user.getEmail()); // Assuming you have a JWT service to generate tokens
        return token;
}
}