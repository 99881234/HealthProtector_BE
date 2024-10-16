package com.graduate.HealthProtector.global.template;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Data
public class BaseResponse<T> {
    HttpStatus status;
    String message;
    T result;

    // 성공 응답 생성
    public static <T> BaseResponse<T> success(T result) {
        return new BaseResponse<>(HttpStatus.OK, "success", result);
    }

    // 실패 응답 생성 (권한 문제 등)
    public static <T> BaseResponse<T> fail(T result) {
        return new BaseResponse<>(HttpStatus.FORBIDDEN, "fail", result);
    }

    // 사용자 지정 응답 생성
    public static <T> BaseResponse<T> of(HttpStatus status, String message, T result) {
        return new BaseResponse<>(status, message, result);
    }
}
