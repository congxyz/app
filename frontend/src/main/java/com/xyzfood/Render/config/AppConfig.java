package com.xyzfood.Render.config;

import com.xyzfood.Render.services.AdminService;
import com.xyzfood.Render.services.AuthService;
import com.xyzfood.Render.services.FoodService;
import com.xyzfood.Render.services.ReservationService;
import com.xyzfood.Render.services.XYZAIService;


public final class AppConfig {
    private static final AppConfig INSTANCE = new AppConfig();

    private final AuthService authService = new AuthService();
    private final ReservationService reservationService = new ReservationService();
    private final FoodService foodService = new FoodService();
    private final AdminService adminService = new AdminService();
    private final XYZAIService xyzaiService = new XYZAIService();

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

    public XYZAIService getXYZAIService() {
        return xyzaiService;
    }
}

