package com.graduate.HealthProtector.protector.api.controller;

import com.graduate.HealthProtector.global.template.BaseResponse;
import com.graduate.HealthProtector.protector.application.ReportGeneratorService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/calendar")
public class ReportController {
    private final ReportGeneratorService reportGeneratorService;

    @Autowired
    public ReportController(ReportGeneratorService reportGeneratorService) {
        this.reportGeneratorService = reportGeneratorService;
    }

    @GetMapping("/report")
    @Operation(summary = "달력에서 상태보기", description = "해당 날짜의 리포트를 출력합니다")
    public BaseResponse<?> getReportByDate(
            @RequestParam("userId") String userId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return reportGeneratorService.getReportByDate(userId, date);
    }
}
