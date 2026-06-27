package com.xyzfood.Render.config;


public final class ApiConfig {
    public static final String BASE_URL = "https://xyz-food.onrender.com/api";

    private ApiConfig() {
        
    }
    public static String getLoginUrl() {
        return BASE_URL + "/auth/login";
    }
    public static String getRegisterUrl() {
        return BASE_URL + "/auth/register";
    }
    public static String getCustomersUrl() {
        return BASE_URL + "/admin/users";
    }
    public static String getTablesUrl() {
        return BASE_URL + "/general/tables";
    }
    public static String getFoodsUrl() {
        return BASE_URL + "/general/foods";
    }
    public static String getAllReservationsUrl() {
        return BASE_URL + "/admin/reservations";
    }
    public static String getTableUrl() {
        return BASE_URL + "/general/getTable?request=";
    }
    public static String checkReservationCodeUrl() {
        return BASE_URL + "/general/checkReservationCode?request=";
    }
    public static String createReservationUrl() {
        return BASE_URL + "/customer/createReservation";
    }
    public static String reservationforCustomerIdUrl() {
        return BASE_URL + "/general/reservationforCustomorId?request=";
    }      
    public static String createFoodOrderUrl() {
        return BASE_URL + "/customer/createFoodOrder";
    }
    public static String foodOrderforreservationCodeUrl() {
        return BASE_URL + "/general/foodOrderforreservationCode?request=";
    }
    public static String updateReservationStatusUrl() {
        return BASE_URL + "/general/updateReservationStatus";
    }  
    public static String uploadFoodImageUrl() {
        return BASE_URL + "/admin/foods/upload-image";
    }
    public static String saveFoodUrl() {
        return BASE_URL + "/admin/foods/save";
    }
    public static String deleteFoodUrl() {
        return BASE_URL + "/admin/foods/delete?foodName=";
    }
    public static String getFoodsByCategoryUrl() {
        return BASE_URL + "/general/foodsByCategory?request=";
    }
    public static String getAIUrl() {
        return BASE_URL + "/ai/ask";
    }
    public static String saveTableUrl() {
        return BASE_URL + "/admin/tables/save";
    }
}
