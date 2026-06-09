package com.xyzfood.Render.dto.Request;

public class RegisterRequest {
    private String username;
    private String password;
    private String fullName;

    public RegisterRequest(String fullName, String username, String password) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getFullName() {
        return fullName;
    }
}
