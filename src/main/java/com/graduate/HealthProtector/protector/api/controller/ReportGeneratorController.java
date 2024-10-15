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
    @Operation(summary = "챗봇과 대화하기", description = "건강 챗봇과 대화합니다.")
    public BaseResponse<?> createReport(@RequestParam(name = "loginId") String loginId, @RequestParam(name = "message") String message) {
        return reportGeneratorService.getChatResponse(loginId, message);
    }

    @GetMapping("/calendar/{createDate}")
    @Operation(summary = "해당 날짜 리포트 보기", description = "해당 날짜의 리포트를 출력합니다.")
    public BaseResponse<?> getReportByDate(@RequestParam(name = "loginId") String loginId, @PathVariable(name = "createDate") String createDate) {
        return reportGeneratorService.getReportByDate(loginId, createDate);
    }

}
