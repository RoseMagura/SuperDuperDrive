package com.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/home")
public class HomeController {
    // private XService xService;

    // public HomeController(XService xService) {
//        this.xService = Service;
//}

    @GetMapping
    public String getHomePage() {
        // get files, notes, etc. with method on xService
        return "home";
    }

    @PostMapping
    public String post() {
        //post something
        return "home";
    }
}
