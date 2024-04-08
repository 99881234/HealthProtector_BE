package com.graduate.HealthProtector.repository;

import com.graduate.HealthProtector.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsById(Long id);
    boolean existsByLoginId(String loginId);

    Optional<UserEntity> findByLoginIdAndPassword(String loginId, String password);
}
