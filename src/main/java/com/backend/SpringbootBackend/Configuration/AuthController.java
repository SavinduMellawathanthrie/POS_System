package com.backend.SpringbootBackend.Configuration;

import com.backend.SpringbootBackend.Data.Entity.Employee;
import com.backend.SpringbootBackend.Repository.EmployeeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/auth")

public class AuthController {
    private static final Logger LOGGER = Logger.getLogger(AuthController.class.getName());
    private final EmployeeRepository employeeRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(EmployeeRepository employeeRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        Optional<Employee> employee = employeeRepository.findByUsername(authRequest.getUsername());

        if (employee.isPresent() && authRequest.getPassword().equals(employee.get().getPassword())) { // No password hashing for now
            String token = jwtUtil.generateToken(employee.get().getUsername());
            LOGGER.log(Level.WARNING, "Login successful : ", employee.get().getUsername());
            return ResponseEntity.ok(new AuthResponse(token));
        } else {
            LOGGER.log(Level.INFO, "Error occurred. Invalid credentials");
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
