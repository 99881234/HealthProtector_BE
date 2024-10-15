package com.graduate.HealthProtector.record.api.controller;

import com.graduate.HealthProtector.global.template.BaseResponse;
import com.graduate.HealthProtector.record.api.dto.response.HealthScoreDto;
import com.graduate.HealthProtector.record.application.RecordService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public BaseResponse<HealthScoreDto> recordHealthScore(
            @RequestParam(name = "loginId") String loginId,
            @RequestParam(name = "steps") int steps,
            @RequestParam(name = "time") int time,
            @RequestParam(name = "stressIndex") int stressIndex,
            @RequestParam(name = "fatigueIndex") int fatigueIndex) {
        try {
            // 사용자 건강 점수 및 피드백 계산
            HealthScoreDto response = recordService.calculateHealthScore(loginId, steps, time, stressIndex, fatigueIndex);
            return BaseResponse.success(response);
        } catch (IllegalArgumentException e) {
            return BaseResponse.fail(new HealthScoreDto(0, e.getMessage()));
        } catch (Exception e) {
            return BaseResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage(), null);
        }
    }


}
