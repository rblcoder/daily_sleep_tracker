package com.company.sleep.controller;

import com.company.sleep.service.SleepInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {


    private final SleepInfoService sleepInfoService;

    public HomeController(SleepInfoService sleepInfoService) {
        this.sleepInfoService = sleepInfoService;
    }

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("entries", sleepInfoService.getAllEntries());
        return "home";
    }
}
