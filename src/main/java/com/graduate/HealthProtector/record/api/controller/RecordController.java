package com.graduate.HealthProtector.record.api.controller;

import com.graduate.HealthProtector.global.template.BaseResponse;
import com.graduate.HealthProtector.record.application.RecordService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/record")
public class RecordController {
    private final RecordService recordService;

    @Autowired
    public RecordController (RecordService recordService){
        this.recordService = recordService;
    }

    @Operation(summary = "혈당 기록 api")
    @PostMapping("/record/bloodSugar")
    public BaseResponse<?> recordBloodSugar(@RequestParam("sugarLevel") Long sugarLevel){
        return recordService.recordBloodSugar(sugarLevel);
    }

    @Operation(summary = "혈압 기록 api")
    @PostMapping("/record/bloodPressure")
    public BaseResponse<?> recordBloodPressure(@RequestParam("bloodPressure") Long bloodPressure){
        return recordService.recordBloodPressure(bloodPressure);
    }

}
