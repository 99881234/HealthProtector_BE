package com.graduate.HealthProtector.user.domain.entity;

import com.graduate.HealthProtector.protector.domain.entity.Report;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String loginId;
    private String password;
    private String username;
    private String email;
    private String phone;


    // User Entity에 cascade = CascadeType.ALL, orphanRemoval = true 추가
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reports = new ArrayList<>();

    @Column(nullable = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    @Column(nullable = true)
    private String height;
    @Column(nullable = true)
    private String weight;
    @Column(nullable = true)
    private String gender;
    @Column(nullable = true)
    private String exerciseCycle;
    @Column(nullable = true)
    private String exerciseTime;

}