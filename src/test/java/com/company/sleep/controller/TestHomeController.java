package com.company.sleep.controller;

import com.company.sleep.model.SleepInfo;
import com.company.sleep.service.impl.SleepInfoServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = HomeController.class)
public class TestHomeController {

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SleepInfoServiceImpl sleepInfoService;

    @Test
    @WithMockUser
    void shouldLoadHomePage() throws Exception {

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Sleep Tracker")));
    }

    @Test
    @WithMockUser
    void shouldLoadCreateEntryPage() throws Exception {

        mockMvc.perform(get("/create"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Create Entry for Sleep Tracker")));
    }

    @Test
    @WithMockUser
    void shouldLoadUpdateEntryPage() throws Exception {
        SleepInfo sleepInfo = SleepInfo.builder()
                .id(1L)
                .sleepDateTime(LocalDateTime.parse("2022-01-01 21:20", dateFormatter))
                .build();

        when(sleepInfoService.getEntryById(1L)).thenReturn(sleepInfo);

        mockMvc.perform(get("/update/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Update Entry for Sleep Tracker")));
    }

    @Test
    @WithMockUser
    void shouldDeleteEntryById() throws Exception {
        mockMvc.perform(get("/delete/1"));


        verify(sleepInfoService,
                times(1)).deleteEntryById(1L);

    }
}
