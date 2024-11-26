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
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ReportGeneratorService {
    @Value("${spring.openai.model}")
    private String model;

    @Value("${spring.openai.api.url}")
    private String apiURL;

    private final UserRepository userRepository;
    private final ReportRepository reportRepository;
    private final RestTemplate restTemplate;
    private final Set<String> sessionHealthInfoSent = new HashSet<>();


    public ReportGeneratorService(UserRepository userRepository, ReportRepository reportRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.reportRepository = reportRepository;
        this.restTemplate = restTemplate;
    }

    // 건강 정보 전송 상태를 추적하는 HashSet
    public BaseResponse<String> getChatResponse(String loginId, String message) {
        try {
            // 사용자 정보 가져오기
            User user = userRepository.findByLoginId(loginId)
                    .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. ID: " + loginId));

            // 건강 정보 생성
            String healthInfo = String.format(
                    "키: %s cm, 몸무게: %s kg, 성별: %s, 운동 빈도: 주 %s회, 운동 시간: %s 분",
                    Optional.ofNullable(user.getHeight()).orElse("알 수 없음"),
                    Optional.ofNullable(user.getWeight()).orElse("알 수 없음"),
                    Optional.ofNullable(user.getGender()).orElse("알 수 없음"),
                    Optional.ofNullable(user.getExerciseCycle()).orElse("알 수 없음"),
                    Optional.ofNullable(user.getExerciseTime()).orElse("알 수 없음")
            );

            // 프롬프트 설정
            String enhancedMessage;
            if (!sessionHealthInfoSent.contains(loginId)) {
                enhancedMessage = String.format(
                        "당신은 건강 상담을 전문으로 하는 의사입니다. 사용자가 질문하거나 정보를 제공하면 의학적 지식을 기반으로 신뢰할 수 있는 답변을 제공합니다.\n" +
                                "답변은 간결하고 명확하며, 환자의 상태를 분석해 필요한 조언을 제공합니다. 답변은 400자 이하로 무조건 끝나게 답변해줘\n" +
                                "예시:\n" +
                                "- '현재 운동 빈도가 주당 3회라면, 관절 건강을 위해 하루 30분씩 걷기 운동을 추가하는 것을 추천합니다.'\n" +
                                "- '수면 시간이 6시간 이하라면, 규칙적인 취침 습관을 통해 수면의 질을 높이는 방법을 고려해야 합니다.'\n" +
                                "- '스트레스가 지속되면 심박수와 혈압을 체크하고, 심리적 안정을 위한 취미 활동을 권장합니다.'\n" +
                                "\n사용자 정보: %s\n" +
                                "사용자 질문: %s\n" +
                                "\n답변:",
                        healthInfo, message
                );
                sessionHealthInfoSent.add(loginId);
            } else {
                enhancedMessage = String.format(
                        "당신은 건강 상담을 전문으로 하는 의사입니다. 사용자가 질문하거나 정보를 제공하면 신뢰할 수 있는 답변을 제공합니다. 답변은 400자 이하로 무조건 끝나게 답변해줘\n" +
                                "사용자 질문: %s\n\n답변:",
                        message
                );
            }

            // GPT API 요청
            ChatGPTRequestDto request = new ChatGPTRequestDto(model, enhancedMessage);
            ChatGPTResponseDto response = restTemplate.postForObject(apiURL, request, ChatGPTResponseDto.class);

            // 응답 처리: 200자 이내로 응답
            String responseContent = response != null && response.getChoices() != null
                    ? response.getChoices().get(0).getMessage().getContent()
                    : "응답을 받을 수 없습니다.";

            // 응답 길이를 200자 이내로 조정
            if (responseContent.length() > 400) {
                responseContent = responseContent.substring(0, 400) + "...";
            }

            // 대화 기록 저장
            Report report = Report.builder()
                    .userMessage(message)
                    .botResponse(responseContent)
                    .user(user)
                    .build();
            reportRepository.save(report);

            return new BaseResponse<>(HttpStatus.CREATED, "챗봇 응답 성공", responseContent);

        } catch (Exception e) {
            return new BaseResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "예기치 않은 오류가 발생했습니다: " + e.getMessage(),
                    null
            );
        }
    }
}