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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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
        ChatGPTRequestDto request = new ChatGPTRequestDto(model, message);
        try {
            ChatGPTResponseDto response = restTemplate.postForObject(apiURL, request, ChatGPTResponseDto.class);
            String responseContent = response != null ? response.getChoices().get(0).getMessage().getContent() : null;

            User user = userRepository.findByLoginId(loginId)
                    .orElseThrow(() -> new IllegalArgumentException("ID: " + loginId));

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
}



