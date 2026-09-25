package com.example.spring_task_management.controller;

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
