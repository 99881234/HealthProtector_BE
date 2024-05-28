package com.graduate.HealthProtector.user.api.dto.response;

import com.graduate.HealthProtector.user.domain.entity.User;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String loginId;
    private String password;
    private String username;
    private String gender;
    private String email;
    private String birthday;

}
