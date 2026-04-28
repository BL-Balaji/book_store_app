package com.bookstore.user.service;

import com.bookstore.common.exception.BadRequestException;
import com.bookstore.common.exception.ResourceNotFoundException;
import com.bookstore.common.exception.UnauthorizedException;
import com.bookstore.common.util.JwtUtil;
import com.bookstore.user.dto.AuthResponse;
import com.bookstore.user.dto.LoginRequest;
import com.bookstore.user.dto.RegisterRequest;
import com.bookstore.user.dto.UserResponse;
import com.bookstore.user.entity.User;
import com.bookstore.user.entity.UserRole;
import com.bookstore.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * User Service Implementation
 *
 * @author BL Balaji
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        log.info("Registering new user: {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail()))
            throw new BadRequestException("Email already registered: " + request.getEmail());

        if (userRepository.existsByUsername(request.getUsername()))
            throw new BadRequestException("Username already taken: " + request.getUsername());

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .role(request.getRole() != null ? request.getRole() : UserRole.USER)
                .active(true)
                .emailVerified(false)
                .build();

        user = userRepository.save(user);
        log.info("User registered successfully: {}", user.getEmail());

        String token = JwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return AuthResponse.builder()
                .token(token).username(user.getUsername())
                .email(user.getEmail()).role(user.getRole().name())
                .message("User registered successfully")
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        log.info("Login attempt: {}", request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new UnauthorizedException("Invalid email or password");

        if (!user.getActive())
            throw new UnauthorizedException("Account is deactivated");

        String token = JwtUtil.generateToken(user.getEmail(), user.getRole().name());
        log.info("Login successful: {}", user.getEmail());

        return AuthResponse.builder()
                .token(token).username(user.getUsername())
                .email(user.getEmail()).role(user.getRole().name())
                .message("Login successful")
                .build();
    }

    public UserResponse getUserByEmail(String email) {
        return toResponse(userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email)));
    }

    public UserResponse getUserById(Long id) {
        return toResponse(userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id)));
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public UserResponse updateUser(Long id, RegisterRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        if (request.getFirstName() != null) user.setFirstName(request.getFirstName());
        if (request.getLastName() != null) user.setLastName(request.getLastName());
        if (request.getPhoneNumber() != null) user.setPhoneNumber(request.getPhoneNumber());
        return toResponse(userRepository.save(user));
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userRepository.delete(user);
        log.info("User deleted: {}", user.getEmail());
    }

    private UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId()).username(user.getUsername()).email(user.getEmail())
                .firstName(user.getFirstName()).lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber()).role(user.getRole().name())
                .active(user.getActive()).emailVerified(user.getEmailVerified())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
