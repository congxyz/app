package com.xyzfood.Render.services;

public class XYZAIService {
    private final ApiService apiService = ApiService.getInstance();


    public String ask(String question){
        return apiService.ask(question);
    }
}
