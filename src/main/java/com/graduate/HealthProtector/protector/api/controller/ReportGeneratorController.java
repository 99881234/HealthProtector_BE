package com.graduate.HealthProtector.protector.api.controller;

import com.graduate.HealthProtector.global.template.BaseResponse;
import com.graduate.HealthProtector.protector.application.ReportGeneratorService;
import com.graduate.HealthProtector.user.domain.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ReportGeneratorController {

    private final ReportGeneratorService reportGeneratorService;
    private final UserRepository userRepository;

    public ReportGeneratorController(ReportGeneratorService reportGeneratorService, UserRepository userRepository) {
        this.reportGeneratorService = reportGeneratorService;
        this.userRepository = userRepository;
    }

    @PostMapping("/createReport")
    @Operation(summary = "챗봇과 대화하기", description = "건강 챗봇과 대화합니다.")
    public BaseResponse<?> createReport(@RequestParam(name = "loginId") String loginId, @RequestParam(name = "message") String message) {
        return reportGeneratorService.getChatResponse(loginId, message);
    }

}
