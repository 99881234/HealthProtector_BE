package com.graduate.HealthProtector.protector.api.dto.request;

import com.graduate.HealthProtector.protector.api.dto.Message;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class ChatGPTRequestDto {
    private String model;
    private List<Message> messages;

    public ChatGPTRequestDto(String model, String prompt) {
        this.model = model;
        this.messages =  new ArrayList<>();
        this.messages.add(new Message("user", prompt));
    }

}