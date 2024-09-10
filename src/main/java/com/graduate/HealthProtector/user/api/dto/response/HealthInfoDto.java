package com.graduate.HealthProtector.user.api.dto.response;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthInfoDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String height;
    private String weight;
    private String gender;
    private String exerciseCycle;
    private String exerciseTime;
}
