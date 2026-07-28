package com.example.spring_task_management.service;

import com.example.spring_task_management.entity.User;
import com.example.spring_task_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// 会員登録機能（サインアップ）
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * ユーザー情報の登録
     * @param username
     * @param email
     * @param rawPassword
     * @return データベースへの登録が正常に完了し、確定した最新のユーザー情報
     */
    public User registerUser(String username, String email, String rawPassword) {

        // 重複登録チェック
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalStateException("このメールアドレスはすでに登録されています");
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));      // パスワードはハッシュ化して保存

        return userRepository.save(user);                           // 登録
    }
}
