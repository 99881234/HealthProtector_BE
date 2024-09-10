package com.graduate.HealthProtector.protector.api.dto.request;

import com.graduate.HealthProtector.protector.api.dto.Message;
import lombok.*;

@Data
public class Choices {
    private int index;
    private Message message;
}
