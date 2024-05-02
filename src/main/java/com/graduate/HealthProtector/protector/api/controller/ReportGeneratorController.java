package com.graduate.HealthProtector.protector.api.controller;

import com.graduate.HealthProtector.protector.application.ReportGeneratorService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ReportGeneratorController {
    private final ReportGeneratorService reportGeneratorService;

    public ReportGeneratorController(ReportGeneratorService reportGeneratorService) {
        this.reportGeneratorService = reportGeneratorService;
    }

    @Operation(summary = "건강 리포트 생성", description = "건강 리포트를 생성합니다.")
    @PostMapping(value = "/create")
    public ResponseEntity<?> create (){
        // 건강 리포트를 생성하기 위해서 필요한 점
        // 받아와서 세팅해야될게 뭔지를 생각


    }

}
