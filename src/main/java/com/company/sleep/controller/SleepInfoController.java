package com.company.sleep.controller;

import com.company.sleep.config.Constants;
import com.company.sleep.exception.DateAndTimeNeedsToBeUnique;
import com.company.sleep.model.SleepInfo;
import com.company.sleep.service.SleepInfoService;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Objects;


@Controller
public class SleepInfoController {

    private final SleepInfoService sleepInfoService;

    public SleepInfoController(SleepInfoService sleepInfoService) {
        this.sleepInfoService = sleepInfoService;
    }

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("entries", sleepInfoService.getAllEntries());
        return "home";
    }

    @PostMapping("/")
    public String saveEntry(SleepInfo sleepInfo,
                            RedirectAttributes redirectAttributes, HttpSession session)
            throws DateAndTimeNeedsToBeUnique {
        String message = sleepInfoService
                .dateValidation(sleepInfo.getSleepDateTime(), sleepInfo.getGetUpDateTime());


        if (!Objects.equals(message, Constants.EMPTY_MESSAGE.toString())) {

            if (sleepInfo.getId() == null) {
                session.setAttribute("sleepInfo", sleepInfo);
                redirectAttributes.addFlashAttribute("message", message);
                return "redirect:/create";
            } else {

                session.setAttribute("sleepInfo", sleepInfo);
                redirectAttributes.addFlashAttribute("message", message);
                return "redirect:/update/" + sleepInfo.getId();
            }

        }
        sleepInfoService.createEntry(sleepInfo);

        return "redirect:/";
    }

    @GetMapping("/create")
    public String showCreateEntryForm(Model model, HttpServletRequest request) {

        if (request.getSession().getAttribute("sleepInfo") == null) {
            SleepInfo sleepInfo = new SleepInfo();
            model.addAttribute("sleepInfo", sleepInfo);
        } else {
            model.addAttribute("sleepInfo", request.getSession().getAttribute("sleepInfo"));
            request.getSession().removeAttribute("sleepInfo");

        }

        return "create_entry";
    }

    @GetMapping("/update/{Id}")
    public String showUpdateEntryForm(@PathVariable(value = "Id") Long Id, Model model,
                                      HttpServletRequest request) {
        if (request.getSession().getAttribute("sleepInfo") == null) {
            SleepInfo sleepInfo = sleepInfoService.getEntryById(Id);
            model.addAttribute("sleepInfo", sleepInfo);
        } else {
            model.addAttribute("sleepInfo", request.getSession().getAttribute("sleepInfo"));
            request.getSession().removeAttribute("sleepInfo");
        }
        return "update_entry";
    }

    @GetMapping("/delete/{Id}")
    public String deleteEntry(@PathVariable(value = "Id") Long Id) {
        sleepInfoService.deleteEntryById(Id);

        return "redirect:/";
    }

}
