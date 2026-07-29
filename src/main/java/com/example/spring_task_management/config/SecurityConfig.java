package com.example.spring_task_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /**
     * パスワードを安全にハッシュ化する仕組み
     * @return BCryptPasswordEncoder のインスタンス→Springコンテナに登録される
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 認証、認可のルール定義
     * @param http
     * @return 実際のセキュリティフィルターの鎖（SecurityFilterChain）を生成し戻り値とする
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        /*
            .authorizeHttpRequests
            ここで指定したエンドポイントは認証不要とする

            .formLogin
            フォームログイン機能の設定
                .loginPage("/login")                    // ログインURLを定義
                .defaultSuccessUrl("/home", true)      // ログイン成功したら必ず「～/home」に遷移
                .permitAll()                            // このURLは認証不要

            .logout
            ログアウト機能の設定
                .logoutSuccessUrl("/login?logout")      // ログアウト成功したらこのURLへ遷移（クエリパラメータlogoutを付加）
                .permitAll()                            // このURLは認証不要
         */
        http
                .authorizeHttpRequests(
                    auth -> auth.requestMatchers(

                            "/error",
                            "/login",
                            "/register",
                            "/tasks/new"
                    ).permitAll().anyRequest().authenticated()
                )
                .formLogin(
                    form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                .logout(logout -> logout
                    .logoutSuccessUrl("/login?logout")
                    .permitAll()
                )
        ;
        return http.build();
    }
}
