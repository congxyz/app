package com.xyzfood.Render.services;

import com.xyzfood.Render.dto.Response.LoginResponse;
import com.xyzfood.Render.dto.Response.RegisterResponse;

public class AuthService {
    private final ApiService apiService = ApiService.getInstance();

    public LoginResponse login(String username, String password) {
        return apiService.login(username, password);
    }

    public RegisterResponse register(String fullName, String username, String password) {
        return apiService.register(fullName, username, password);
    }
}

