package com.graduate.HealthProtector.record.api.controller;

import com.graduate.HealthProtector.global.template.BaseResponse;
import com.graduate.HealthProtector.record.api.dto.response.HealthScoreDto;
import com.graduate.HealthProtector.record.application.RecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/record")
public class RecordController {
    private final RecordService recordService;

    @Autowired
    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @Operation(summary = "건강 점수 기록하기", description = "사용자의 건강 정보를 기록하고 건강 점수를 반환합니다.")
    @PostMapping("/healthScore")
    public BaseResponse recordHealthScore(
            @RequestParam(name = "loginId") String loginId,
            @RequestParam(name = "steps") int steps,
            @RequestParam(name = "time") int time,
            @RequestParam(name = "stressIndex") int stressIndex,
            @RequestParam(name = "fatigueIndex") int fatigueIndex) {
        try {
            // 현재 날짜를 서버에서 자동으로 설정
            LocalDate date = LocalDate.now();

            // 사용자 건강 점수 및 피드백 계산
            HealthScoreDto response = recordService.calculateHealthScore(loginId, steps, time, stressIndex, fatigueIndex, date);
            return BaseResponse.success(response);  // 성공적으로 처리된 경우 반환
        } catch (IllegalArgumentException e) {
            return BaseResponse.fail(loginId);  // 실패한 경우
        } catch (Exception e) {
            // 일반적인 예외 처리
            return BaseResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage(), null);
        }
    }

    @GetMapping("/healthScore")
    public BaseResponse<HealthScoreDto> getHealthRecordByDate(
            @Parameter(description = "사용자 로그인 ID", required = true)
            @RequestParam(name = "loginId") String loginId,

            @Parameter(description = "조회할 날짜 (yyyy-MM-dd 형식)", required = true, example = "2024-11-13")
            @RequestParam(name = "date") String date) {

        try {
            // 문자열로 받은 날짜를 LocalDate로 변환
            LocalDate localDate = LocalDate.parse(date);

            HealthScoreDto userHealthScoreDto = recordService.getHealthScoreByDate(loginId, localDate);

            return BaseResponse.success(userHealthScoreDto);

        } catch (Exception e) {
            return BaseResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage(), null);
        }
    }

}
