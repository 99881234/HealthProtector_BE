package com.graduate.HealthProtector.protector.api.controller;

import com.graduate.HealthProtector.global.template.BaseResponse;
import com.graduate.HealthProtector.protector.application.ReportGeneratorService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/chat")
public class ReportGeneratorController {
    private final ReportGeneratorService reportGeneratorService;

    public ReportGeneratorController(ReportGeneratorService reportGeneratorService, RestTemplate restTemplate) {
        this.reportGeneratorService = reportGeneratorService;
    }

    @GetMapping("/createReport")
    @Operation(summary = "건강 리포트 생성", description = "건강 리포트를 생성합니다.")
    public BaseResponse<?> createReport(@RequestParam(name = "loginId") String loginId, @RequestParam(name = "message") String message) {
        return reportGeneratorService.getChatResponse(loginId, message);
    }
}
