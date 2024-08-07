package com.graduate.HealthProtector.record.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Record {
    // record table에서 해야하는...
    // 혈압측정, 복약알림, 근데 이거 완전 간단 CRUD인데
    // 컬럼을 그러면 booldPressure, 이거 db저장 방법에 대해 생각을 해봐야할듯, 혈당, 건강리포트
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long sugarLevel;
    private Long bloodPressure;
    private String medicine;
    private String report;

    @Builder
    public Record(Long sugarLevel, Long bloodPressure, String medicine, String report){
        this.sugarLevel = sugarLevel;
        this.bloodPressure = bloodPressure;
        this.medicine = medicine;
        this.report = report;
    }

}
