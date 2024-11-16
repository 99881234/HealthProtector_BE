package com.graduate.HealthProtector.global.template;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.graduate.HealthProtector.record.api.dto.response.HealthScoreDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)  // null인 필드를 제외하도록 설정
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Data
public class BaseResponse<T> {
    HttpStatus status;
    String message;
    T result;

    // 성공 응답 생성
    public static <T> BaseResponse<T> success(T result) {
        return new BaseResponse<>(HttpStatus.OK, "성공", result);
    }

    // 실패 응답 생성 (권한 문제 등)
    public static <T> BaseResponse<T> fail(String message) {
        return new BaseResponse<>(HttpStatus.FORBIDDEN, message, null);  // result는 null
    }

    // 사용자 지정 응답 생성 (상태 코드와 메시지를 커스터마이즈)
    public static <T> BaseResponse<T> of(HttpStatus status, String message, T result) {
        return new BaseResponse<>(status, message, result);
    }

    // 내부 서버 오류 응답 생성 (에러 메시지 포함)
    public static <T> BaseResponse<T> internalServerError(String message) {
        return new BaseResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, message, null);
    }
}
