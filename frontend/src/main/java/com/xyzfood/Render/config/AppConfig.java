package com.xyzfood.Render.config;

import com.xyzfood.Render.services.AdminService;
import com.xyzfood.Render.services.AuthService;
import com.xyzfood.Render.services.FoodService;
import com.xyzfood.Render.services.ReservationService;

/**
 * Tao va chia se cac service cho toan bo JavaFX app.
 * Cac service dung chung mot ApiService de du lieu tren cac man hinh dong bo.
 */
public final class AppConfig {
    private static final AppConfig INSTANCE = new AppConfig();

    private final AuthService authService = new AuthService();
    private final ReservationService reservationService = new ReservationService();
    private final FoodService foodService = new FoodService();
    private final AdminService adminService = new AdminService();

    private AppConfig() {
    }

    public static AppConfig getInstance() {
        return INSTANCE;
    }

    public AuthService getAuthService() {
        return authService;
    }

    public ReservationService getReservationService() {
        return reservationService;
    }

    public FoodService getFoodService() {
        return foodService;
    }

    public AdminService getAdminService() {
        return adminService;
    }
}

