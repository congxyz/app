package com.xyzfood.Render.models;

public class Food {
    private String name;
    private String description;
    private float price;
    private String category;
    private String image_path;
    public Food() {
    }

    public Food(String name, String description, String category , float price, String image_path) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.image_path = image_path;
    }

    public String getCategory() {
        return category;
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

    public String getPriceText() {
        return String.format("%,.0f đ", price);
    }

    public String getImage_path() {
        return image_path;
    }
    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public void setCategory(String category) {
        this.category = category;
    }    
}
