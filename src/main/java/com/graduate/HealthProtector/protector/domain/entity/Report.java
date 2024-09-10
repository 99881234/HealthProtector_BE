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

    /*
        user entity의 pk를 join하는데 그 이름을 user_id
        cascade = CascadeType.ALL 추가
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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





