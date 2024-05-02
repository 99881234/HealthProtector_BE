package com.graduate.HealthProtector.protector.application;

import com.graduate.HealthProtector.protector.domain.repository.ReportRepository;
import org.springframework.stereotype.Service;

@Service
public class ReportGeneratorService {
    private final ReportRepository reportRepository;

    public ReportGeneratorService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }



}
