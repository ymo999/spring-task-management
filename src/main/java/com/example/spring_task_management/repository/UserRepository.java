package com.example.spring_task_management.repository;

import com.example.spring_task_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // メールアドレスで検索する処理（クエリメソッド機能）
    Optional<User> findByEmail(String email);
}
