package com.xyzfood.dto.response;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xyzfood.dto.response.UserResponse;


public class LoginResponse {
    private UserResponse user;
    private boolean success;
    private String message;
    private String token;

    public LoginResponse(UserResponse user, boolean success, String message, String token) {
        this.user = user;
        this.success = success;
        this.message = message;
        this.token = token;
    }

    public UserResponse getUser() {
        return user;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

}