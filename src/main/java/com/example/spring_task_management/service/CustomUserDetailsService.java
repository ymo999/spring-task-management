package com.example.spring_task_management.service;

import com.example.spring_task_management.entity.User;
import com.example.spring_task_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * メールアドレスをもとに、DBからユーザー情報を取得しSpring Securityへ渡す
     * （パスワード照合はSpring Security側が自動で行う）
     * @param email the username identifying the user whose data is required.
     * @return 認証に必要な情報を持つUserDetailsオブジェクト
     * @throws UsernameNotFoundException  該当するユーザーが見つからない場合にスローされる
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 指定したメールアドレスに合致したUserを検索して取得、空（該当なし）だったら例外をスローする
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("ユーザーが見つかりません: " + email));

        // 取得したUserエンティティを、戻り値のUserDetails型に整形（Spring Securityが用意しているUserクラスを利用）
        return org.springframework.security.core.userdetails.User           // Userエンティティとの名前衝突回避のためパッケージ名を付加
                .withUsername(user.getEmail())              // emailを実質的なログインIDとして扱っているため
                .password(user.getPassword())
                .authorities("USER")                        // ユーザ権限=USER
                .build();                                   // UserDetailsオブジェクトに整形して返す
    }
}
