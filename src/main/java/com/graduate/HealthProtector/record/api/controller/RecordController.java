package com.graduate.HealthProtector.record.api.controller;

import com.graduate.HealthProtector.global.template.BaseResponse;
import com.graduate.HealthProtector.record.application.RecordService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/record")
public class RecordController {
    private final RecordService recordService;

    @Autowired
    public RecordController (RecordService recordService){
        this.recordService = recordService;
    }

    @Operation(summary = "혈당 기록 api")
    @PostMapping("/bloodSugar")
    public BaseResponse<?> recordBloodSugar(@RequestParam("sugarLevel") Long sugarLevel, @RequestParam("userId") String loginId){
        return recordService.recordBloodSugar(sugarLevel, loginId);
    }

    @Operation(summary = "혈압 기록 api")
    @PostMapping("/bloodPressure")
    public BaseResponse<?> recordBloodPressure(@RequestParam("bloodPressure") Long bloodPressure, @RequestParam("userId") String loginId){
        return recordService.recordBloodPressure(bloodPressure, loginId);
    }

    @Operation(summary = "체중기록 api")
    @PostMapping("/weight")
    public BaseResponse<?> recordWeight(@RequestParam("weight") Long weight, @RequestParam("userId") String loginId){
        return recordService.recordWeight(weight, loginId);
    }

    @Operation(summary = "건강기록 api")
    @PostMapping("/health")
    public BaseResponse<?> healthRecord(@RequestParam("userId") String loginId){
        return recordService.recordHealth(loginId);
    }

}
