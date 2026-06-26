package com.xyzfood.Render.services;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.xyzfood.Render.models.Food;
import com.xyzfood.Render.models.FoodOrder;
import com.xyzfood.Render.models.Reservation;
import com.xyzfood.Render.models.Table;
import com.xyzfood.Render.models.User;
import com.xyzfood.Render.dto.Request.LoginRequest;
import com.xyzfood.Render.dto.Request.RegisterRequest;
import com.xyzfood.Render.dto.Request.ReservationRequest;
import com.xyzfood.Render.dto.Request.FoodOrderRequest;
import com.xyzfood.Render.dto.Request.ReservationStatusRequest;
import com.xyzfood.Render.dto.Response.LoginResponse;
import com.xyzfood.Render.dto.Response.RegisterResponse;
import com.xyzfood.Render.dto.Response.FoodImageUploadResponse;
import com.xyzfood.Render.config.ApiConfig;
import com.xyzfood.Render.config.SessionManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.Consumer;
import com.xyzfood.Render.dto.Response.APIResponse;

public final class ApiService {
    private static final ApiService INSTANCE = new ApiService();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public static ApiService getInstance() {
        return INSTANCE;
    }
    public LoginResponse login(String username, String password) {
        try {
            String json = OBJECT_MAPPER.writeValueAsString(new LoginRequest(username, password));
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ApiConfig.getLoginUrl()))
                    .header("Content-Type", "application/json")
                    .header("Authorization",
                            "Bearer " + SessionManager.getInstance().getToken())
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() == 200) {
                LoginResponse loginResponse = OBJECT_MAPPER.readValue(httpResponse.body(), LoginResponse.class);
                return loginResponse;
            }
            return new LoginResponse(null, "Đăng nhập thất bại: " , false, null); 
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi kết nối đến máy chủ: " + e.getMessage());   
            return new LoginResponse(null, "Lỗi kết nối đến máy chủ", false,null);
        }
    }

    public RegisterResponse register(String fullName, String username, String password) {
        try {
            String json = OBJECT_MAPPER.writeValueAsString(new RegisterRequest(fullName, username, password));
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ApiConfig.getRegisterUrl()))
                .header("Content-Type", "application/json")
                .header("Authorization",
                            "Bearer " + SessionManager.getInstance().getToken())
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                if (httpResponse.statusCode() == 200) {
                    RegisterResponse registerResponse = OBJECT_MAPPER.readValue(httpResponse.body(), RegisterResponse.class);
                    return registerResponse;
                }
                return new RegisterResponse(false, "Đăng ký thất bại: " );
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Lỗi kết nối đến máy chủ: " + e.getMessage());
                return new RegisterResponse(false, "Lỗi kết nối đến máy chủ" + e.getMessage());
            }
        
    }
    public List<User> customers() {
        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ApiConfig.getCustomersUrl()))
                    .header("Content-Type", "application/json")
                    .header("Authorization",
                            "Bearer " + SessionManager.getInstance().getToken())
                    .GET()
                    .build();
            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() == 200) {
                List<User> users = OBJECT_MAPPER.readValue(httpResponse.body(), OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, User.class));
                return users;
            } else {
                return new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi kết nối đến máy chủ: " + e.getMessage());
            return new ArrayList<>();
        }

    }

    public List<Table> tables() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ApiConfig.getTablesUrl()))
                    .header("Content-Type", "application/json")
                    .header("Authorization",
                            "Bearer " + SessionManager.getInstance().getToken())
                    .GET()
                    .build();
            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() == 200) {
                List<Table> tables = OBJECT_MAPPER.readValue(httpResponse.body(), OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, Table.class));
                return tables;
            } else {
                System.out.println("Lỗi kết nối đến máy chủ: "+httpResponse.body());
                return new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi kết nối đến máy chủ: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Food> foods() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ApiConfig.getFoodsUrl()))
                    .header("Content-Type", "application/json")
                    .header("Authorization",
                            "Bearer " + SessionManager.getInstance().getToken())
                    .GET()
                    .build();
            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() == 200) {
                List<Food> foods = OBJECT_MAPPER.readValue(httpResponse.body(), OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, Food.class));
                return foods;
            } else {
                System.out.println("Lỗi kết nối đến máy chủ: "+httpResponse.body());
                return new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi kết nối đến máy chủ: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    public Table getTable(int Tablenumber) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ApiConfig.getTableUrl() + Tablenumber))
                    .header("Content-Type", "application/json")
                    .header("Authorization",
                            "Bearer " + SessionManager.getInstance().getToken())
                    .GET()
                    .build();
            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() == 200) {
                return OBJECT_MAPPER.readValue(httpResponse.body(), Table.class);
            } else {
                System.out.println("Lỗi kết nối đến máy chủ: "+httpResponse.body());
                return new Table();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi kết nối đến máy chủ: " + e.getMessage());
            return new Table();
        }
    }
    public boolean checkReservationCode(String reservationCode) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ApiConfig.checkReservationCodeUrl() + reservationCode))
                    .header("Content-Type", "application/json")
                    .header("Authorization",
                            "Bearer " + SessionManager.getInstance().getToken())
                    .GET()
                    .build();
            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() == 200) {
                return OBJECT_MAPPER.readValue(httpResponse.body(), Boolean.class);
            } else {
                System.out.println("Lỗi kết nối đến máy chủ: "+httpResponse.body());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi kết nối đến máy chủ: " + e.getMessage());
            return false;
        }
    }
    public APIResponse createFoodOrder(String reservationCode, String foodName, int quantity) {
        try {
            String json = OBJECT_MAPPER.writeValueAsString(new FoodOrderRequest(reservationCode, foodName, quantity));
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ApiConfig.createFoodOrderUrl()))
                .header("Content-Type", "application/json")
                .header("Authorization",
                            "Bearer " + SessionManager.getInstance().getToken())
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                if (httpResponse.statusCode() == 200) {
                    APIResponse APIResponse = OBJECT_MAPPER.readValue(httpResponse.body(), APIResponse.class);
                    return APIResponse;
                }
                return new APIResponse(false, "Lỗi kết nối đến máy chủ " );
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Lỗi kết nối đến máy chủ: " + e.getMessage());
                return new APIResponse(false, "Lỗi kết nối đến máy chủ" + e.getMessage());
            }
    }
    public List<FoodOrder> foodOrderforreservationCode(String reservationCode) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ApiConfig.foodOrderforreservationCodeUrl() + reservationCode))
                    .header("Content-Type", "application/json")
                    .header("Authorization",
                            "Bearer " + SessionManager.getInstance().getToken())
                    .GET()
                    .build();
            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() == 200) {
                return OBJECT_MAPPER.readValue(httpResponse.body(), OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, FoodOrder.class));
            } else {
                System.out.println("Lỗi kết nối đến máy chủ: "+httpResponse.body());
                return new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi kết nối đến máy chủ: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public APIResponse createReservation(String reservation_code, String CustomerId, int Tablenumber, LocalDateTime reservationTime, 
        int guestCount, String status, String notes) {
        if (guestCount < 1 || guestCount > getTable(Tablenumber).getSeats()) {
            throw new IllegalArgumentException("Số người vượt quá sức chứa của bàn");
        }
        try {
            String json = OBJECT_MAPPER.writeValueAsString(new ReservationRequest(reservation_code, CustomerId, Tablenumber, reservationTime,
                                                                                    guestCount, status, notes));
                                                                                    
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ApiConfig.createReservationUrl()))
                .header("Content-Type", "application/json")
                .header("Authorization",
                            "Bearer " + SessionManager.getInstance().getToken())
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                if (httpResponse.statusCode() == 200) {
                    APIResponse APIResponse = OBJECT_MAPPER.readValue(httpResponse.body(), APIResponse.class);
                    return APIResponse;
                }
                System.out.println("Lỗi kết nối đến máy chủ: "+httpResponse.body());
                return new APIResponse(false, "Lỗi kết nối đến máy chủ " );
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Lỗi kết nối đến máy chủ: " + e.getMessage());
                return new APIResponse(false, "Lỗi kết nối đến máy chủ" + e.getMessage());
            }
        
    }

    public List<Reservation> reservations() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ApiConfig.getAllReservationsUrl()))
                    .header("Content-Type", "application/json")
                    .header("Authorization",
                            "Bearer " + SessionManager.getInstance().getToken())
                    .GET()
                    .build();
            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() == 200) {
                List<Reservation> reservations = OBJECT_MAPPER.readValue(httpResponse.body(), OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, Reservation.class));
                return reservations;
            } else {
                System.out.println("Lỗi kết nối đến máy chủ: "+httpResponse.body());
                return new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi kết nối đến máy chủ: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Reservation> reservationsFor(String customerId) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ApiConfig.reservationforCustomerIdUrl() + customerId))
                    .header("Content-Type", "application/json")
                    .header("Authorization",
                            "Bearer " + SessionManager.getInstance().getToken())
                    .GET()
                    .build();
            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() == 200) {
                return OBJECT_MAPPER.readValue(httpResponse.body(), OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, Reservation.class));
            } else {
                System.out.println("Lỗi kết nối đến máy chủ: "+httpResponse.body());
                return new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi kết nối đến máy chủ: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public APIResponse updateReservationStatus(String reservationCode, String status) {
        try {
            String json = OBJECT_MAPPER.writeValueAsString(new ReservationStatusRequest(reservationCode, status));
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ApiConfig.updateReservationStatusUrl()))
                .header("Content-Type", "application/json")
                .header("Authorization",
                            "Bearer " + SessionManager.getInstance().getToken())
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                if (httpResponse.statusCode() == 200) {
                    APIResponse APIResponse = OBJECT_MAPPER.readValue(httpResponse.body(), APIResponse.class);
                    return APIResponse;
                }
                return new APIResponse(false, "Lỗi kết nối đến máy chủ " );
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Lỗi kết nối đến máy chủ: " + e.getMessage());
                return new APIResponse(false, "Lỗi kết nối đến máy chủ" + e.getMessage());
            }
    }

    public FoodImageUploadResponse uploadFoodImage(File imageFile) {
        try {
            String boundary = "----XYZFoodBoundary" + UUID.randomUUID();
            String fileName = imageFile.getName().replace("\"", "");
            String contentType = Files.probeContentType(imageFile.toPath());
            if (contentType == null || contentType.isBlank()) {
                contentType = "application/octet-stream";
            }

            List<byte[]> body = new ArrayList<>();
            body.add(("--" + boundary + "\r\n"
                    + "Content-Disposition: form-data; name=\"image\"; filename=\"" + fileName + "\"\r\n"
                    + "Content-Type: " + contentType + "\r\n\r\n").getBytes(StandardCharsets.UTF_8));
            body.add(Files.readAllBytes(imageFile.toPath()));
            body.add(("\r\n--" + boundary + "--\r\n").getBytes(StandardCharsets.UTF_8));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ApiConfig.uploadFoodImageUrl()))
                    .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                    .header("Authorization",
                            "Bearer " + SessionManager.getInstance().getToken())
                    .POST(HttpRequest.BodyPublishers.ofByteArrays(body))
                    .build();
            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() == 200) {
                return OBJECT_MAPPER.readValue(httpResponse.body(), FoodImageUploadResponse.class);
            }
            return new FoodImageUploadResponse(false, "Upload ảnh thất bại", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new FoodImageUploadResponse(false, "Lỗi upload ảnh: " + e.getMessage(), null);
        }
    }

    public APIResponse saveFood(Food food) {
        try {
            String json = OBJECT_MAPPER.writeValueAsString(food);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ApiConfig.saveFoodUrl()))
                    .header("Content-Type", "application/json")
                    .header("Authorization",
                            "Bearer " + SessionManager.getInstance().getToken())
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() == 200) {
                return OBJECT_MAPPER.readValue(httpResponse.body(), APIResponse.class);
            }
            System.out.println("Save food status: " + httpResponse.statusCode());
            System.out.println("Save food body: " + httpResponse.body());
            return new APIResponse(false, "Lưu món ăn thất bại");
        } catch (Exception e) {
            e.printStackTrace();
            return new APIResponse(false, "Lỗi lưu món ăn: " + e.getMessage());
        }
    }

    public APIResponse deleteFood(String foodName) {
        System.out.println(SessionManager.getInstance().getToken());
        try {
            String encodedFoodName = URLEncoder.encode(foodName, StandardCharsets.UTF_8);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ApiConfig.deleteFoodUrl() + encodedFoodName))
                    .header("Content-Type", "application/json")
                    .header("Authorization",
                            "Bearer " + SessionManager.getInstance().getToken())
                    .GET()
                    .build();
            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() == 200) {
                return OBJECT_MAPPER.readValue(httpResponse.body(), APIResponse.class);
            }
            System.out.println("Status: " + httpResponse.statusCode());
            System.out.println("Body: " + httpResponse.body());
            return new APIResponse(false, "Xóa món ăn thất bại");
        } catch (Exception e) {
            e.printStackTrace();
            return new APIResponse(false, "Lỗi xóa món ăn: " + e.getMessage());
        }
    }
    public List<Food> getFoodsByCategory(String category) {
        try {
            String encodedCategory = URLEncoder.encode(category, StandardCharsets.UTF_8);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ApiConfig.getFoodsByCategoryUrl() + encodedCategory))
                    .header("Content-Type", "application/json")
                    .header("Authorization",
                            "Bearer " + SessionManager.getInstance().getToken())
                    .GET()
                    .build();
            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() == 200) {
                return OBJECT_MAPPER.readValue(httpResponse.body(), OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, Food.class));
            } else {
                System.out.println("Lỗi kết nối đến máy chủ: "+httpResponse.body());
                return new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi kết nối đến máy chủ: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    public String ask(String question){
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ApiConfig.getAIUrl()))
                    .header("Content-Type", "application/json")
                    .header("Authorization",
                            "Bearer " + SessionManager.getInstance().getToken())
                    .POST(HttpRequest.BodyPublishers.ofString("\"" + question + "\""))
                    .build();
            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() == 200) {
                return httpResponse.body();
            } else {
                System.out.println("Lỗi kết nối đến máy chủ: "+httpResponse.body()+httpResponse.statusCode());
                return "Lỗi khi kết nối tới AI";
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi kết nối đến máy chủ: " + e.getMessage());
            return "Lỗi khi kết nối tới AI";
        }
    
    }
}
