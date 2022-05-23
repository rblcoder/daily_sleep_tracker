package com.company.sleep.controller;

import com.company.sleep.exception.DateAndTimeNeedsToBeUnique;
import com.company.sleep.model.SleepInfo;
import com.company.sleep.service.impl.SleepInfoServiceImpl;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

@WebMvcTest(controllers = SleepInfoController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TestSleepInfoController {

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SleepInfoServiceImpl sleepInfoService;

    @Autowired
    private WebApplicationContext context;


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

    @Test
    @WithMockUser
    @Disabled
    void shouldSaveEntry() throws Exception, DateAndTimeNeedsToBeUnique {
        SleepInfo sleepInfo = SleepInfo.builder()
                .id(null)
                .sleepDateTime(LocalDateTime.parse("2022-01-01 21:20", dateFormatter))
                .build();
        when(sleepInfoService.dateValidation(sleepInfo.getSleepDateTime(), null))
                .thenReturn("");

        mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//                        .sessionAttr("sleepInfo", sleepInfo)
                        .param("sleepDateTime", sleepInfo.getSleepDateTime().toString())
                        ).andDo(print());
//                .andExpect(status().isOk());
//        verify(sleepInfoService, times(1)).createEntry(sleepInfo);

    }
}
