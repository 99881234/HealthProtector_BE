package com.graduate.HealthProtector.user.api.dto.response;

import com.graduate.HealthProtector.user.domain.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserDto {
    private Long id;
    private String loginId;
    private String password;
    private String username;
    private String gender;
    private String email;
    private String birthday;

    public static UserDto fromEntity(User userEntity) {
        return UserDto.builder()
                .id(userEntity.getId())
                .loginId(userEntity.getLoginId())
                .password(userEntity.getPassword())
                .username(userEntity.getUsername())
                .gender(userEntity.getGender())
                .email(userEntity.getEmail())
                .birthday(userEntity.getBirthday())
                .build();
    }
}
