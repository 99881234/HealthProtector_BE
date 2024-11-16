package com.graduate.HealthProtector.record.domain.repository;

import com.graduate.HealthProtector.record.domain.entity.Record;
import com.graduate.HealthProtector.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RecordRepository extends JpaRepository<Record, Long> {

    List<Record> findByUserAndCreatedDate(User user, LocalDate localDate);

    Optional<Record> findFirstByUserAndCreatedDateOrderByCreatedDateAsc(User user, LocalDate date);
}
