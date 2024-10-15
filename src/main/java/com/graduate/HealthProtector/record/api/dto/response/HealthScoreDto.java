package com.graduate.HealthProtector.record.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HealthScoreDto {
    private int healthScore;
    private String feedback;
}