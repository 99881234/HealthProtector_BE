package com.graduate.HealthProtector.protector.domain.entity;

import com.graduate.HealthProtector.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userMessage;
    private String botResponse;
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PrePersist
    protected void onCreate() {
        this.createDate = LocalDateTime.now();
    }

    @Builder
    public Report(String userMessage, String botResponse, User user) {
        this.userMessage = userMessage;
        this.botResponse = botResponse;
        this.user = user;
    }
}





