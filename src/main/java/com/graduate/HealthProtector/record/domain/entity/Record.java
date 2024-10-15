package com.graduate.HealthProtector.record.domain.entity;

import com.graduate.HealthProtector.user.domain.entity.User;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private int steps;
    private int time;
    private int stressIndex;
    private int fatigueIndex;
    private int healthScore;

    private String feedback;

    private LocalDateTime createdDate = LocalDateTime.now();


    @Builder
    public Record(User user, int steps, int time, int stressIndex, int healthScore, int fatigueIndex, String feedback) {
        this.user = user;
        this.steps = steps;
        this.time = time;
        this.stressIndex = stressIndex;
        this.healthScore = healthScore;
        this.fatigueIndex = fatigueIndex;
        this.feedback = feedback;
    }
}
