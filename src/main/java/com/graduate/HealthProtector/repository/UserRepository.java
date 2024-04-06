package com.graduate.HealthProtector.repository;

import com.graduate.HealthProtector.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsernameAndPassword(String username, String userpwd);

    Optional<UserEntity> findByUsername(String username);

    Boolean existsByUserName(String userName);
}
