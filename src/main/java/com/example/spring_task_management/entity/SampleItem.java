package com.example.spring_task_management.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "sample_items")
public class SampleItem {

    // フィールド
    @Id                                                         // 主キー
    @GeneratedValue(strategy = GenerationType.IDENTITY)         // 自動採番
    private Long id;                                            // Null値許容のためラッパークラスで定義
    private String name;
    private LocalDateTime createdAt = LocalDateTime.now();

    // アクセサ
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}
}
