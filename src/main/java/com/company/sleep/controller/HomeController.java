package com.company.sleep.controller;

import com.company.sleep.model.SleepInfo;
import com.company.sleep.service.SleepInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


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

    @PostMapping("/")
    public String saveEntry(@ModelAttribute("sleepInfo") SleepInfo sleepInfo){
        sleepInfoService.createEntry(sleepInfo);
        return "redirect:/";
    }

    @GetMapping("/create")
    public String showCreateEntryForm(Model model){
        SleepInfo sleepInfo = new SleepInfo();
        model.addAttribute("sleepInfo", sleepInfo);
        return "create_entry";
    }

    @GetMapping("/update/{Id}")
    public String showUpdateEntryForm(@PathVariable(value = "Id") Long Id, Model model){
        SleepInfo sleepInfo = sleepInfoService.getEntryById(Id);
        model.addAttribute("sleepInfo", sleepInfo);
        return "update_entry";
    }


}
