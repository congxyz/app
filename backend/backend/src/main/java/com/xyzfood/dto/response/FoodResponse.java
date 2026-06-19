package com.xyzfood.dto.response;

public class FoodResponse {
    private String name;
    private String description;
    private float price;
    private String image_path;
    private String category;
    private boolean delete;
    public FoodResponse(String name, String description, float price, String image_path, String category, boolean delete) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image_path = image_path;
        this.category = category;
        this.delete = delete;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public float getPrice() {
        return price;
    }
    public String getImage_path() {
        return image_path;
    }
    public String getCategory() {
        return category;
    }
    public boolean getDelete() {
        return delete;
    }
}
