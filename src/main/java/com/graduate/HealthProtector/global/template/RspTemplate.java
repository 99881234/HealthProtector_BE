package com.graduate.HealthProtector.global.template;

import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Data
public class RspTemplate<T> {
    int statusCode;
    String message;
    T data;

    public RspTemplate(HttpStatus httpStatus, String message, T data) {
        this.statusCode = httpStatus.value();
        this.message = message;
        this.data = data;
    }
}
