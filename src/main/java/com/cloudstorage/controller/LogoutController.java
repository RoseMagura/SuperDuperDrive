package com.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/logout")
public class LogoutController {

    @PostMapping("/logout")
    public String logout(){
        return "redirect:/login?logout";
    }
    @GetMapping("/logout")
    public String logoutView() {
        return "redirect:/login?logout";
    }
}
