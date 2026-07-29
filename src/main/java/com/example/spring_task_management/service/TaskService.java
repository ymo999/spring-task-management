/// ログイン中のユーザーが、新しい個人タスクを作成できる機能
package com.example.spring_task_management.service;

import com.example.spring_task_management.entity.Task;
import com.example.spring_task_management.entity.User;
import com.example.spring_task_management.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(String title,
                           String description,
                           LocalDate dueDate,
                           User loginUser) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setDueDate(dueDate);
        task.setStatus("未着手");
        task.setAssignee(loginUser);            // 作成者=担当者
        task.setCreatedBy(loginUser);

        return taskRepository.save(task);
    }
}
