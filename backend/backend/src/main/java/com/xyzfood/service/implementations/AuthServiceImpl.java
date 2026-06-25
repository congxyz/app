package com.xyzfood.service.implementations;

import com.xyzfood.dto.request.LoginRequest;
import com.xyzfood.dto.request.RegisterRequest;
import com.xyzfood.dto.response.LoginResponse;
import com.xyzfood.dto.response.RegisterResponse;
import com.xyzfood.dto.response.UserResponse;
import org.springframework.stereotype.Service;
import com.xyzfood.repositories.UserRepository;
import com.xyzfood.service.interfaces.AuthService;
import com.xyzfood.entities.User;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import com.xyzfood.security.JwtService;


@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager  ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        Authentication authentication;
        User user = userRepository.findByUsername(request.getUsername());
        try{
            authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        } catch (BadCredentialsException e) {
            return new LoginResponse(null, false, "Tên đăng nhập hoặc mật khẩu không đúng", null);
        } catch (AuthenticationException e) {
            return new LoginResponse(null ,false,"Tên đăng nhập hoặc mật khẩu không đúng",null);
        }   
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);
        return new LoginResponse(new UserResponse(user.getCustomerId(),user.getFullName(),user.getUsername(),user.getRole(),user.getCreatedAt()) , true, "Đăng nhập thành công", token);
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {
        User user = userRepository.findByUsername(request.getUsername().trim());
        if (user != null) {
            return new RegisterResponse(false, "Tên đăng nhập đã tồn tại");
        }
        User newUser = new User();
        newUser.setFullName(request.getFullName());
        newUser.setUsername(request.getUsername().trim());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(User.Role.CUSTOMER);
        newUser = userRepository.save(newUser);
        newUser.setCustomerId("CUST" + String.format("%04d", newUser.getId()));
        userRepository.save(newUser);
        return new RegisterResponse(true, "Đăng ký thành công");
    }
}

