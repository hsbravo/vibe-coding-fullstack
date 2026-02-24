package com.example.vibeapp.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "Thymeleaf is working perfectly in VibeApp! ✨");
        return "home/home";
    }

    @GetMapping("/api/hello")
    @ResponseBody
    public String hello() {
        return "Hello, Vibe!";
    }
}
