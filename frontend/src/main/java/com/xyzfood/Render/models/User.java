package com.xyzfood.Render.models;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;


public class User {
    
    public enum Role {
        CUSTOMER,
        ADMIN
    }
    @JsonProperty("CustomerId")
    private String CustomerId;
    private String fullName;
    private String username;
    private Role role;
    private LocalDateTime createdAt;

    public User(){

    }
    public User(String CustomerId, String fullName, String username, Role role, LocalDateTime createdAt) {
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

    public boolean isAdmin() {
        return Role.ADMIN == role;
    }
    public void setCustomerId(String CustomerId) {
        this.CustomerId = CustomerId;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public void setUsername(String username) {
        this.username = username;   
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
