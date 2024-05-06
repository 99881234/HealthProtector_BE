package com.graduate.HealthProtector.protector.api.controller;

import com.graduate.HealthProtector.global.template.CustomRestTemplate;
import com.graduate.HealthProtector.protector.api.dto.request.ChatGPTRequest;
import com.graduate.HealthProtector.protector.api.dto.response.ChatGPTResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/bot")
public class CustomBotController {

    @Value("${spring.openai.model}")
    private String model;

    @Value("${spring.openai.api.url}")
    private String apiURL;

    private final CustomRestTemplate customRestTemplate;

    public CustomBotController(CustomRestTemplate customRestTemplate) {
        this.customRestTemplate = customRestTemplate;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam(name = "prompt") String prompt) {
        ChatGPTRequest request = new ChatGPTRequest(model, prompt);
        try {
            ChatGPTResponse chatGPTResponse = customRestTemplate.postForObject(apiURL, request, ChatGPTResponse.class, apiKey);
            return chatGPTResponse.getChoices().get(0).getMessage().getContent();
        } catch (HttpClientErrorException e) {
            return "Error: " + e.getStatusCode() + " " + e.getResponseBodyAsString();
        }
    }
    

}
