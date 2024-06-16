package com.graduate.HealthProtector.user.domain.entity;

import com.graduate.HealthProtector.protector.domain.entity.Report;
import com.graduate.HealthProtector.protector.domain.entity.Report;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long Id;

    @Column(length = 100)
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