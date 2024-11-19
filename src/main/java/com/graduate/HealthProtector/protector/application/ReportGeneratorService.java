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

            // 초기 프롬프트 설정
            String enhancedMessage;
            if (!sessionHealthInfoSent.contains(loginId)) { // 사용자가 처음 대화할 때
                enhancedMessage = String.format(
                        "당신은 건강 상담 챗봇이에요! 사용자의 질문에 따뜻하고 친근하게 답변해주고, 대화를 자연스럽게 이어가도록 도와주세요. 사용자가 계속 이야기하고 싶도록 유도하는 것도 잊지 마세요! 😊\n" +
                                "예시: '오! 운동 자주 하시네요? 운동 끝나고 몸이 어때요? 스트레칭도 잘 해주고 있죠? 💪✨'\n" +
                                "예시: '잠은 잘 자고 계세요? 요즘 잠이 부족하면 하루 7~8시간 자는 게 좋다고 해요! 😴💤 충분히 자고 일어나면 기분도 좋고 피로도 풀려요~'\n" +
                                "예시: '몸무게가 딱 적당해 보여요! 그런데 식사는 규칙적으로 잘 챙기고 있나요? 🍎🥗 균형 잡힌 식사가 중요하니까요~'\n" +
                                "예시: '운동 강도를 조금 높여볼까요? 한 단계 더 도전하면 더 큰 효과를 볼 수 있을 거예요! 🏋️‍♀️🎯 괜찮을까요?'\n\n" +
                                "사용자 정보: %s\n" +
                                "사용자 질문: %s\n\n" +
                                "답변:",
                        healthInfo, message
                );
                sessionHealthInfoSent.add(loginId); // 건강 정보를 전송했다고 기록
            } else { // 이후 메시지
                enhancedMessage = String.format(
                        "당신은 건강 상담 챗봇입니다. 사용자가 제공하는 정보를 바탕으로 친절하고 신뢰성 있는 상담을 제공해주세요.\n" +
                                "사용자 질문: %s\n\n" +
                                "답변:",
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
            if (responseContent.length() > 200) {
                responseContent = responseContent.substring(0, 200) + "...";
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