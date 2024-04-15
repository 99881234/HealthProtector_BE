package com.graduate.HealthProtector.user.application;

import com.graduate.HealthProtector.user.api.dto.response.UserDTO;
import com.graduate.HealthProtector.user.domain.UserEntity;
import com.graduate.HealthProtector.user.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository; // jpa, MySQL dependency 추가

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public boolean save(UserDTO userDTO) {
        if (userRepository.existsById(userDTO.getId())) {
            return false;
        }

        // 존재하지 않는 경우 회원 엔티티를 생성
        UserEntity userEntity = UserEntity.builder()
                .loginId(userDTO.getLoginId())
                .password(userDTO.getPassword())
                .username(userDTO.getUsername())
                .gender(userDTO.getGender())
                .email(userDTO.getEmail())
                .birthday(userDTO.getBirthday())
                .build();

        // 회원 엔티티 저장
        userRepository.save(userEntity);
        return true;
    }

    public boolean login(String loginId, String password) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByLoginIdAndPassword(loginId, password);
        return optionalUserEntity.isPresent();
    }

    public boolean checkId(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }
}
