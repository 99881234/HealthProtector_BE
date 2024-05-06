package com.graduate.HealthProtector.user.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id // primary key
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