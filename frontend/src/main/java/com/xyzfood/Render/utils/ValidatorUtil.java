package com.xyzfood.Render.utils;

public final class ValidatorUtil {
    private ValidatorUtil() {
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static String requireLogin(String username, String password) {
        if (isBlank(username) || isBlank(password)) {
            return "Vui lòng nhập tên đăng nhập và mật khẩu";
        }
        return null;
    }

    public static String requireRegister(String fullName, String username, String password, String confirmPassword) {
        if (isBlank(fullName) || isBlank(username) || isBlank(password) || isBlank(confirmPassword)) {
            return "Vui lòng nhập đầy đủ thông tin";
        }
        if (password.length() < 6) {
            return "Mật khẩu phải có ít nhất 6 ký tự";
        }
        if (!password.equals(confirmPassword)) {
            return "Mật khẩu xác nhận không khớp";
        }
        return null;
    }
}

