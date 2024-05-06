package com.graduate.HealthProtector.protector.domain.entity;

import com.graduate.HealthProtector.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
public class Report {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;
    private String content;
    @Enumerated(EnumType.STRING)
    private State state;

}
