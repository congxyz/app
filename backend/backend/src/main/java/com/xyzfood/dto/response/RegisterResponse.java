package com.xyzfood.dto.response;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterResponse {
    private boolean success;
    private String message;
    public RegisterResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    public boolean isSuccess() {
        return success;
    }
    public String getMessage() {
        return message;
    }
    
}
