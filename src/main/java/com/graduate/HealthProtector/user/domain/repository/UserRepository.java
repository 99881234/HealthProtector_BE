package com.graduate.HealthProtector.user.domain.repository;

import com.graduate.HealthProtector.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsById(Long id);
    boolean existsByLoginId(String loginId);
    Optional<User> findByLoginIdAndPassword(String loginId, String password);
}
