package com.graduate.HealthProtector.protector.domain.repository;

import com.graduate.HealthProtector.protector.domain.entity.Report;
import com.graduate.HealthProtector.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByUserAndCreateDateBetween(User user, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
