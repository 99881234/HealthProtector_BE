package com.graduate.HealthProtector.dto;

import com.graduate.HealthProtector.entity.UserEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserDTO {
    private Long id;
    private String loginId;
    private String password;
    private String username;
    private String gender;
    private String email;
    private String birthday;

    public static UserDTO fromEntity(UserEntity userEntity) {
        return UserDTO.builder()
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
