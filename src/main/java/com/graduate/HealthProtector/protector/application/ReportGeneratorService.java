package com.graduate.HealthProtector.protector.application;

import com.graduate.HealthProtector.global.template.BaseResponse;
import com.graduate.HealthProtector.protector.api.dto.request.ChatGPTRequestDto;
import com.graduate.HealthProtector.protector.api.dto.response.ChatGPTResponseDto;
import com.graduate.HealthProtector.protector.domain.entity.Report;
import com.graduate.HealthProtector.protector.domain.repository.ReportRepository;
import com.graduate.HealthProtector.user.domain.entity.User;
import com.graduate.HealthProtector.user.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class ReportGeneratorService {
    @Value("${spring.openai.model}")
    private String model;

    @Value("${spring.openai.api.url}")
    private String apiURL;

    private final UserRepository userRepository;
    private final ReportRepository reportRepository;
    private final RestTemplate restTemplate;

    public ReportGeneratorService(UserRepository userRepository, ReportRepository reportRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.reportRepository = reportRepository;
        this.restTemplate = restTemplate;
    }

    public BaseResponse<?> getChatResponse(String loginId, String message) {
        try {
            User user = userRepository.findByLoginId(loginId)
                    .orElseThrow(() -> new IllegalArgumentException("ID: " + loginId));

            String healthInfo = String.format(
                    "Height: %s, Weight: %s, Gender: %s, Exercise Cycle: %s, Exercise Time: %s",
                    user.getHeight(), user.getWeight(), user.getGender(), user.getExerciseCycle(), user.getExerciseTime()
            );

            String enhancedMessage = String.format(
                    "User Information: %s. Message: %s",
                    healthInfo, message
            );

            ChatGPTRequestDto request = new ChatGPTRequestDto(model, enhancedMessage);

            ChatGPTResponseDto response = restTemplate.postForObject(apiURL, request, ChatGPTResponseDto.class);
            String responseContent = response != null ? response.getChoices().get(0).getMessage().getContent() : null;

            Report report = Report.builder()
                    .userMessage(message)
                    .botResponse(responseContent)
                    .user(user)
                    .build();
            reportRepository.save(report);

            return new BaseResponse<>(HttpStatus.CREATED, "Chat response retrieved successfully", responseContent);
        } catch (HttpClientErrorException e) {
            return new BaseResponse<>((HttpStatus) e.getStatusCode(), "Error: " + e.getResponseBodyAsString(), null);
        } catch (Exception e) {
            return new BaseResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred: " + e.getMessage(), null);
        }
    }


    public BaseResponse<?> getReportByDate(String loginId, String createDate) {
        try {
            // 사용자를 조회
            User user = userRepository.findByLoginId(loginId)
                    .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. ID: " + loginId));

            // DateTimeFormatter를 사용하여 날짜 문자열을 LocalDate로 변환
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(createDate, formatter); // 문자열을 LocalDate로 변환

            // 날짜와 사용자로 리포트 조회
            Optional<Report> report = reportRepository.findByUserAndCreateDate(user, localDate.atStartOfDay());

            if (report.isPresent()) {
                return new BaseResponse<>(HttpStatus.OK, "리포트 조회 성공", report.get());
            } else {
                return new BaseResponse<>(HttpStatus.NOT_FOUND, "해당 날짜의 리포트가 없습니다.", null);
            }

        } catch (Exception e) {
            return new BaseResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, "에러 발생: " + e.getMessage(), null);
        }
    }
}



