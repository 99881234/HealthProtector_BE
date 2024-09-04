package com.graduate.HealthProtector.record.domain.entity;

import com.graduate.HealthProtector.user.domain.entity.User;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long sugarLevel;
    @Nullable
    private Long bloodPressure;
    @Nullable
    private Long weight;
    @Nullable
    private String medicine;
    @Nullable
    private String report;
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PrePersist
    protected void onCreate() {
        this.createDate = LocalDateTime.now();
    }

    @Builder
    public Record(Long sugarLevel, Long bloodPressure, Long weight, String medicine, String report, User user){
        this.sugarLevel = sugarLevel;
        this.bloodPressure = bloodPressure;
        this.weight = weight;
        this.medicine = medicine;
        this.report = report;
        this.user = user;
    }

}
