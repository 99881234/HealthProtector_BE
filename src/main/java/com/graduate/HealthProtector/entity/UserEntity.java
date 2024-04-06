package com.graduate.HealthProtector.entity;

import com.graduate.HealthProtector.dto.UserDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user")
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY) //지금은 안 쓸 건데 자동으로 값 1씩 증가해주며, pk 속성을 가짐
    @Id // primary key
    private Long id;

    @Column(length = 100)
    private String userName;

    @Column(length = 100)
    private String userPwd;

    @Column(length = 100)
    private String email;

    @Builder
    public static UserEntity toUserEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();

        userEntity.id = userDTO.getId();
        userEntity.userPwd = userDTO.getUserPwd();
        userEntity.userName = userDTO.getUserName();
        userEntity.email = userDTO.getEmail();

        return userEntity;
    }

}