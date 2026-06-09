package com.xyzfood.Render.dto.Response;

import com.xyzfood.Render.models.User;

public class LoginResponse {
    private User user;
    private String message;
    private boolean success;
    private String token;
    public LoginResponse (){

    }
    public LoginResponse(User user, String message, boolean success,String token) {
        this.user = user;
        this.message = message;
        this.success = success;
    }
    public User getUser() {
        return user;
    }
    public String getMessage() {
        return message;
    }
    public boolean isSuccess() {
        return success;
    }
    public String getToken() {
        return token;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
