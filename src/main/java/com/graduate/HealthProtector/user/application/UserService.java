package com.graduate.HealthProtector.user.application;

import com.graduate.HealthProtector.user.api.dto.response.HealthInfoDto;
import com.graduate.HealthProtector.user.api.dto.response.JoinDto;
import com.graduate.HealthProtector.user.domain.entity.User;
import com.graduate.HealthProtector.user.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public boolean save(JoinDto joinDTO) {
        if (userRepository.existsById(joinDTO.getId())) {
            return false;
        }

        User userEntity = User.builder()
                .loginId(joinDTO.getLoginId())
                .password(joinDTO.getPassword())
                .username(joinDTO.getUsername())
                .email(joinDTO.getEmail())
                .build();

        userRepository.save(userEntity);
        return true;
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public boolean healthInfoSave(Long id, HealthInfoDto healthInfoDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));

        if (healthInfoDto.getBirthday() != null) {
            existingUser.setBirthday(healthInfoDto.getBirthday());
        }
        if (healthInfoDto.getHeight() != null) {
            existingUser.setHeight(healthInfoDto.getHeight());
        }
        if (healthInfoDto.getWeight() != null) {
            existingUser.setWeight(healthInfoDto.getWeight());
        }
        if (healthInfoDto.getGender() != null) {
            existingUser.setGender(healthInfoDto.getGender());
        }
        if (healthInfoDto.getExerciseCycle() != null) {
            existingUser.setExerciseCycle(healthInfoDto.getExerciseCycle());
        }
        if (healthInfoDto.getExerciseTime() != null) {
            existingUser.setExerciseTime(healthInfoDto.getExerciseTime());
        }

        userRepository.save(existingUser);
        return true; // Indicate successful update
    }


    public JoinDto authenticateUser(String loginId, String password) {
        // 사용자 조회
        User user = userRepository.findByLoginIdAndPassword(loginId, password)
                .orElseThrow(() -> new IllegalArgumentException("Invalid login credentials"));

        return new JoinDto(
                user.getId(),
                user.getLoginId(),
                user.getPassword(),
                user.getUsername(),
                user.getEmail()
        );
    }

    public boolean checkId(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }


}
