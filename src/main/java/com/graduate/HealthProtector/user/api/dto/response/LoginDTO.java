package com.graduate.HealthProtector.user.api.dto.response;

import com.graduate.HealthProtector.user.domain.entity.User;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDto {
    private String loginId;
    private String password;

    public static LoginDto loginDTO(User userEntity){
        return LoginDto.builder()
                .loginId(userEntity.getLoginId())
                .password(userEntity.getPassword())
                .build();
    }

}
