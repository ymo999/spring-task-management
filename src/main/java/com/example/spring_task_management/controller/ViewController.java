package com.example.spring_task_management.controller;

import com.example.spring_task_management.entity.Task;
import com.example.spring_task_management.entity.User;
import com.example.spring_task_management.repository.UserRepository;
import com.example.spring_task_management.service.TaskService;
import com.example.spring_task_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class ViewController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";         // ～/login.htmlを表示
    }

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }

    @GetMapping("/profile")
    public String profilePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("email", userDetails.getUsername());
        return "profile";
    }

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserRepository userRepository;

    /**
     * タスク登録動作確認用
     * @param title
     * @param description
     * @param dueDate
     * @param userDetails
     * @param model
     * @return 遷移先ページ名称（簡易表示用テンプレート名称）
     */
    @GetMapping("/task-create-test")
    private String taskCreateTest(@RequestParam String title,
                                  @RequestParam String description,
                                  @RequestParam String dueDate,
                                  @AuthenticationPrincipal UserDetails userDetails,
                                  Model model) {
        // ログイン後なので例外がスローされることはないはずだが念のため（型として安全に扱うため）
        User loginUser = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        Task task = taskService.createTask(title, description, LocalDate.parse(dueDate), loginUser);
        model.addAttribute("message",
                "登録完了! id=" + task.getId() + ", title=" + task.getTitle());
        return "task-create-result";        // 簡易表示用テンプレート
    }

    /**
     * 会員登録動作確認用
     * @return 遷移先ページ名称
     */
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @Autowired
    private UserService userService;

    /**
     * フォーム送信（POST）を受け取るエンドポイント
     * @param username
     * @param email
     * @param password
     * @return 遷移先URL
     */
    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String email,
                           @RequestParam String password) {
        userService.registerUser(username, email, password);
        return "redirect:/login";
    }
}
