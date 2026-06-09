package com.xyzfood.dto.response;
import java.time.LocalDateTime;

import com.xyzfood.entities.User.Role;


public class UserResponse {
    private String CustomerId;
    private String fullName;
    private String username;
    private Role role;
    private LocalDateTime createdAt;
    
    public UserResponse(String CustomerId, String fullName, String username, Role role, LocalDateTime createdAt) {
        this.CustomerId = CustomerId;
        this.fullName = fullName;
        this.username = username;
        this.role = role;
        this.createdAt = createdAt;
    }
    public String getCustomerId() {
        return CustomerId;
    }
    public String getFullName() {
        return fullName;
    }
    public String getUsername() {
        return username;
    }
    public Role getRole() {
        return role;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
}
