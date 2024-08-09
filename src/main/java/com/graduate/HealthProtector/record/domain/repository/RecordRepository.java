package com.graduate.HealthProtector.record.domain.repository;

import com.graduate.HealthProtector.record.domain.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long>{

}
