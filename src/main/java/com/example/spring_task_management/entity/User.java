package com.example.spring_task_management.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    // フィールド
    @Id                                                         // 主キー
    @GeneratedValue(strategy = GenerationType.IDENTITY)         // 自動採番
    private Long id;                                            // Null値許容のためラッパークラスで定義
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    private LocalDateTime createdAt = LocalDateTime.now();

    // アクセサ
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}
}
