package com.xyzfood.service.interfaces;
import com.xyzfood.dto.request.LoginRequest;
import com.xyzfood.dto.request.RegisterRequest;
import com.xyzfood.dto.response.LoginResponse;
import com.xyzfood.dto.response.RegisterResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    RegisterResponse register(RegisterRequest request);
}
 