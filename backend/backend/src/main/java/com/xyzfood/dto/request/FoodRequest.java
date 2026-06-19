package com.xyzfood.dto.request;

public class FoodRequest {
    private String name;
    private String description;
    private float price;
    private String image_path;
    private String category;
    private boolean delete;
    public FoodRequest() {
    }

    public FoodRequest(String name, String description, float price, String image_path, String category, boolean delete) {
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

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImage_path() {
        return image_path;
    }

    public boolean getDelete() {
        return delete;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }
}
