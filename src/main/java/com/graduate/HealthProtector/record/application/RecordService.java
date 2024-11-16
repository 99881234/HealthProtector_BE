package com.graduate.HealthProtector.record.application;

import com.graduate.HealthProtector.protector.api.dto.request.ChatGPTRequestDto;
import com.graduate.HealthProtector.protector.api.dto.response.ChatGPTResponseDto;
import com.graduate.HealthProtector.record.api.dto.response.HealthScoreDto;
import com.graduate.HealthProtector.record.domain.entity.Record;
import com.graduate.HealthProtector.record.domain.repository.RecordRepository;
import com.graduate.HealthProtector.user.domain.entity.User;
import com.graduate.HealthProtector.user.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Service
public class RecordService {

    private final UserRepository userRepository;
    private final RecordRepository recordRepository;
    private final RestTemplate restTemplate;

    @Value("${spring.openai.model}")
    private String model;

    @Value("${spring.openai.api.url}")
    private String apiURL;

    @Autowired
    public RecordService(UserRepository userRepository, RecordRepository recordRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.recordRepository = recordRepository;
        this.restTemplate = restTemplate;
    }

    /**
     * 사용자의 건강 정보를 바탕으로 건강 점수를 계산하는 메소드
     */
    public HealthScoreDto calculateHealthScore(String loginId, int steps, int time, int stressIndex, int fatigueIndex, LocalDate date) {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("ID: " + loginId + "인 사용자를 찾을 수 없습니다."));

        // 사용자의 건강 기록 생성
        Record record = Record.builder()
                .user(user)
                .steps(steps)
                .time(time)
                .stressIndex(stressIndex)
                .fatigueIndex(fatigueIndex)
                .createdDate(date)
                .build();

        // 건강 점수 계산
        int healthScore = calculateScore(record);

        // 피드백을 요청하여 저장
        String feedback = getChatFeedback(user, steps, time, stressIndex, fatigueIndex);

        // 건강 기록 저장
        record.setHealthScore(healthScore);  // 건강 점수 설정
        record.setFeedback(feedback);        // 피드백 설정
        recordRepository.save(record);       // 기록 저장

        return new HealthScoreDto(user.getUsername(), healthScore, feedback);
    }

    private String getChatFeedback(User user, int steps, int time, int stressIndex, int fatigueIndex) {
        try {
            String healthInfo = String.format(
                    "오늘건강 - 걸음수: %d, 수면시간: %d, 스트레스지수: %d, 피로지수: %d",
                    steps, time, stressIndex, fatigueIndex
            );

            String prompt = String.format("사용자의 건강 정보에 대한 피드백을 주세요. 답변은 255자 이내로 %s", healthInfo);

            ChatGPTRequestDto request = new ChatGPTRequestDto(model, prompt);
            ChatGPTResponseDto response = restTemplate.postForObject(apiURL, request, ChatGPTResponseDto.class);
            return response != null ? response.getChoices().get(0).getMessage().getContent() : "피드백을 받을 수 없습니다.";
        } catch (HttpClientErrorException e) {
            return "GPT 피드백 요청 중 오류가 발생했습니다: " + e.getResponseBodyAsString();
        } catch (Exception e) {
            return "피드백 요청 중 예기치 않은 오류가 발생했습니다: " + e.getMessage();
        }
    }

    private int calculateScore(Record record) {
        double stepWeight = 0.3;     // 걸음 수에 30% 가중치
        double timeWeight = 0.2;      // 운동 시간에 20% 가중치
        double stressWeight = 0.4;   // 스트레스 지수에 40% 가중치
        double fatigueWeight = 0.3;  // 피로도 지수에 30% 가중치

        // 최대 10,000 걸음을 기준으로 점수를 산출합니다. (10,000 걸음일 때 100점)
        int stepScore = Math.min(100, record.getSteps() / 100);

        // 운동 시간 점수 (60분 기준으로 점수 계산)
        int timeScore = Math.min(100, record.getTime() / 60);

        // 스트레스와 피로도 지수는 100에서 빼는 방식으로 낮을수록 높은 점수 부여
        int stressScore = 100 - record.getStressIndex();  // 스트레스가 0이면 100점
        int fatigueScore = 100 - record.getFatigueIndex(); // 피로도가 0이면 100점

        // 각 요소에 가중치를 적용한 후 평균 점수를 계산
        double weightedScore = (stepScore * stepWeight) + (timeScore * timeWeight) +
                (stressScore * stressWeight) + (fatigueScore * fatigueWeight);

        // 최종 점수를 정수로 반환
        return (int) Math.round(weightedScore);
    }

    public HealthScoreDto getHealthScoreByDate(String loginId, LocalDate date) {
        // 사용자 조회
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("ID: " + loginId + "인 사용자를 찾을 수 없습니다."));

        // 해당 날짜의 첫 번째 건강 기록을 조회
        Record record = recordRepository.findFirstByUserAndCreatedDateOrderByCreatedDateAsc(user, date)
                .orElseThrow(() -> new IllegalArgumentException("해당 날짜에 건강 기록이 존재하지 않습니다."));

        // 저장된 건강 점수와 피드백을 그대로 반환
        int healthScore = record.getHealthScore();
        String feedback = record.getFeedback();
        String username = user.getUsername(); // username 추가

        // HealthScoreDto에 username을 포함하여 반환
        return new HealthScoreDto(username, healthScore, feedback);
    }





}


