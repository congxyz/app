package com.xyzfood.Render.config;

import com.xyzfood.Render.models.User;


public final class SessionManager {
    private static final SessionManager INSTANCE = new SessionManager();
    private User currentUser;
    private String token; 

    private SessionManager() {
    }
    public static SessionManager getInstance() {
        return INSTANCE;
    }

    public void login(User user,String token) {
        currentUser = user;
        this.token = token;
    }

    public User getCurrentUser() {
        if (currentUser == null) {
            throw new IllegalStateException("Chưa có người dùng đăng nhập");
        }
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public void logout() {
        currentUser = null;
        token = null;
    }
    public String getToken(){
        return token;
    }
}

