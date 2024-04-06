package com.graduate.HealthProtector.dto;

import com.graduate.HealthProtector.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDTO {
    private Long id;
    private String userName;
    private String userPwd;
    private String email;

    public static UserDTO toUserDTO(UserEntity userEntity){
        UserDTO userDTO = new UserDTO();

        userDTO.setId(userEntity.getId());
        userDTO.setUserPwd(userEntity.getUserPwd());
        userDTO.setUserName(userEntity.getUserName());
        userDTO.setEmail(userEntity.getEmail());

        return userDTO;
    }

}
