package com.graduate.HealthProtector.global.template;

import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@Data
public class BaseResponse<T> {
    private HttpStatus status;
    String message;
    T data;

    public BaseResponse(HttpStatus httpStatus, String message, T data) {
//        this.status = status;
        this.message = message;
        this.data = data;
    }
}
