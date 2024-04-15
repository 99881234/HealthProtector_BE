package com.graduate.HealthProtector.user.api.dto.response;

import com.graduate.HealthProtector.user.domain.UserEntity;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDTO {
    private String loginId;
    private String password;

    public static LoginDTO loginDTO(UserEntity userEntity){
        return LoginDTO.builder()
                .loginId(userEntity.getLoginId())
                .password(userEntity.getPassword())
                .build();
    }

}
