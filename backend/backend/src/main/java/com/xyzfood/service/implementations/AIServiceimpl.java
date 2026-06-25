package com.xyzfood.service.implementations;

import com.xyzfood.service.interfaces.AIService;
import org.springframework.stereotype.Service;
import com.xyzfood.repositories.TableRepository;
import com.xyzfood.repositories.FoodRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import java.util.List;
import com.xyzfood.entities.Food;
import com.xyzfood.entities.Restaurant_table;

@Service
public class AIServiceimpl implements AIService {
    @Value("${gemini.api-key:}")
    private String ApiKey;
    private final FoodRepository foodRepository;
    private final TableRepository tableRepository;
    
    public AIServiceimpl(FoodRepository foodRepository, TableRepository tableRepository){
        this.foodRepository = foodRepository;
        this.tableRepository = tableRepository;
    }
    @Override
    public String ask(String question) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Bạn là trợ lí nhà hàng XYZ Food.Danh sách món ăn:");
        List<Food> foods = foodRepository.findAll().stream().filter(food -> !food.getDelete()).toList();
        List<Restaurant_table> tables = tableRepository.findAll();
        for(Food food : foods) {
            prompt.append(food.getName() + " - " + food.getDescription() + " - " + food.getCategory() + " - " + food.getPrice()+"\n");
        }
        prompt.append("Danh sách bàn:");
        for(Restaurant_table table : tables) {
            prompt.append("Bàn số " + table.getNumber() + " thuộc tầng " + table.getFloor() + " có số chỗ ngồi là " + table.getSeats() + "\n" );
        }
        prompt.append("Tầng 1 không có điều hòa,tầng 2 có điều hòa.Bàn 1,2,3 ở gần cửa chính , bàn 4,5 ở giữa còn bàn 6,7,8 ở trong cùng gần màn chiếu,bếp và nhà vệ sinh.Bàn 9,10,11 ở tầng 2 gần cửa sổ có view hồ đối diện siêu đẹp,bàn 12,13 ở giữa,bàn 14,15 ở trong cùng gần màn chiếu,dàn karaoke,nhà vệ sinh.");
        prompt.append("Cách đặt bàn của app là:bấm vào nút đặt bàn rồi chọn bàn muốn chọn và điền thông tin như ngày,giờ,mấy người,ghi chú,rồi chọn món chuẩn bị trước nếu muốn.Và đặt món là đặt món chuẩn bị trước theo kèm với đơn đặt bàn nên không có chức năng đặt món mà phải đặt trước thông qua đặt bàn.");
        prompt.append("Câu hỏi của khách hàng:"+ question);
        String url ="https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + ApiKey;
        String body = """
        {
        "contents": [
            {
            "parts": [
                {
                "text": "%s"
                }
            ]
            }
        ]
        }
        """.formatted(prompt.toString().replace(" \"", "\\\""));
        
        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(body))
                        .build();
        try {
            HttpResponse<String> response =
                HttpClient.newHttpClient()
                        .send(request,
                            HttpResponse.BodyHandlers.ofString());
                System.out.println("Error:"+response.body());
                System.out.println("Status code:"+response.statusCode());
                            ObjectMapper mapper =
                new ObjectMapper();

            JsonNode root = mapper.readTree(response.body());

            return root.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();
        } catch (Exception e) {
             e.printStackTrace();
            return "AI hiện không khả dụng.";   
        }
        
    }
}
