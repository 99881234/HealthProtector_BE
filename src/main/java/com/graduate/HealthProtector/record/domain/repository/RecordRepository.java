package com.graduate.HealthProtector.record.domain.repository;

import com.graduate.HealthProtector.record.domain.entity.Record;
import com.graduate.HealthProtector.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecordRepository extends JpaRepository<Record, Long> {

    // 사용자의 최신 기록을 가져오는 메서드
    Optional<Record> findTopByUserOrderByCreatedDateDesc(User user);
}
