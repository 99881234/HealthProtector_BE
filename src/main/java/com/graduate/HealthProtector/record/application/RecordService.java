package com.graduate.HealthProtector.record.application;

import com.graduate.HealthProtector.global.template.BaseResponse;
import com.graduate.HealthProtector.record.domain.entity.Record;
import com.graduate.HealthProtector.record.domain.repository.RecordRepository;
import com.graduate.HealthProtector.user.domain.entity.User;
import com.graduate.HealthProtector.user.domain.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class RecordService {
    private final RecordRepository recordRepository;
    private final UserRepository userRepository;

    public RecordService(RecordRepository recordRepository, UserRepository userRepository){
        this.recordRepository = recordRepository;
        this.userRepository = userRepository;
    }

    public BaseResponse<?> recordBloodSugar(Long sugarLevel, String loginId) {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("ID: " + loginId));

        Record record = Record.builder()
                .sugarLevel(sugarLevel)
                .user(user)
                .build();
        recordRepository.save(record);

        return new BaseResponse<>(HttpStatus.OK, "Success", sugarLevel);
    }

    public BaseResponse<?> recordBloodPressure(Long bloodPressure, String loginId){
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("ID: " + loginId));

        Record record = Record.builder()
                .bloodPressure(bloodPressure)
                .user(user)
                .build();
        recordRepository.save(record);

        return new BaseResponse<>(HttpStatus.OK, "Success", bloodPressure);

    }
}
