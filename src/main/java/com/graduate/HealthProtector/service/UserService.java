package com.graduate.HealthProtector.service;

import com.graduate.HealthProtector.dto.UserDTO;
import com.graduate.HealthProtector.entity.UserEntity;
import com.graduate.HealthProtector.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository; // jpa, MySQL dependency 추가

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public boolean save(UserDTO userDTO) {
        if(!userRepository.existsByUserName(userDTO.getUserName())){
            return false;
        }

        return true;

    }
}
