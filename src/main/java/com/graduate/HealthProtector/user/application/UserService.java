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
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 기존 사용자 정보를 업데이트
        existingUser = User.builder()
                .id(existingUser.getId())
                .loginId(existingUser.getLoginId())
                .password(existingUser.getPassword())
                .username(existingUser.getUsername())
                .email(existingUser.getEmail())
                .birthday(healthInfoDto.getBirthday())
                .height(healthInfoDto.getHeight())
                .weight(healthInfoDto.getWeight())
                .gender(healthInfoDto.getGender())
                .exerciseCycle(healthInfoDto.getExerciseCycle())
                .exerciseTime(healthInfoDto.getExerciseTime())
                .build();

        userRepository.save(existingUser);
        return true;
    }

    public boolean login(String loginId, String password) {
        Optional<User> optionalUserEntity = userRepository.findByLoginIdAndPassword(loginId, password);
        return optionalUserEntity.isPresent();
    }

    public boolean checkId(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }


}
