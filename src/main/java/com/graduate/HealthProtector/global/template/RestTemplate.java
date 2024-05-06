package com.graduate.HealthProtector.global.template;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class RestTemplate<T> {
    int statusCode;
    String message;
    T data;

    public RestTemplate(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
