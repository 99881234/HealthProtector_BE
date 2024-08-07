package com.graduate.HealthProtector.record.application;

import com.graduate.HealthProtector.global.template.BaseResponse;
import com.graduate.HealthProtector.record.domain.entity.Record;
//import com.graduate.HealthProtector.record.domain.repository.RecordRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class RecordService {
//    private final RecordRepository recordRepository;
//
//    public RecordService(RecordRepository recordRepository){
//        this.recordRepository = recordRepository;
//    }


    public BaseResponse<?> recordBloodSugar(Long sugarLevel) {
        // 맵핑할 식별자 memberId 불러오게 만들어야 할듯
        // 지금 뭘해야하면 sugarLevel을 record에 저장하기만 하면 됨
        // builder 패턴 사용해야할 거 같은데..
        Record record = Record.builder().sugarLevel(sugarLevel).build();
        return new BaseResponse<>(HttpStatus.OK, "Success", sugarLevel);
    }

    public BaseResponse<?> recordBloodPressure(Long bloodPressure){
        Record record = Record.builder().bloodPressure(bloodPressure).build();
        return new BaseResponse<>(HttpStatus.OK, "Success", bloodPressure);

    }
}
