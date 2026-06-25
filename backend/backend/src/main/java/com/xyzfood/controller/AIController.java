package com.xyzfood.controller;

import com.xyzfood.service.interfaces.AIService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class AIController {
    
    private final AIService aiservice;
    public AIController(AIService aiservice) {
        this.aiservice = aiservice;
    }

    @PostMapping("/ask")
    public String ask(String question) {
        return aiservice.ask(question);
    }

}
