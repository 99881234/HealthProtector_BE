package com.graduate.HealthProtector.entity;

import com.graduate.HealthProtector.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user")
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY) //지금은 안 쓸 건데 자동으로 값 1씩 증가해주며, pk 속성을 가짐
    @Id // primary key
    private Long id;

    @Column
    private String loginId;

    @Column(length = 100)
    private String password;

    @Column(length = 100)
    private String username;

    @Column (length = 10)
    private String gender;

    @Column(length = 100)
    private String email;

    @Column
    private String birthday;

}