package com.graduate.HealthProtector.protector.domain.repository;

import com.graduate.HealthProtector.protector.domain.entity.Report;
import com.graduate.HealthProtector.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findByUserAndCreateDate(User user, LocalDateTime createDate);

}
