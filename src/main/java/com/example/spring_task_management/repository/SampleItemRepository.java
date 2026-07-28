package com.example.spring_task_management.repository;

import com.example.spring_task_management.entity.SampleItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleItemRepository extends JpaRepository<SampleItem, Long> {
}
