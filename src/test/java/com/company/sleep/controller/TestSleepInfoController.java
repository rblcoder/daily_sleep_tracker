package com.company.sleep.controller;

import com.company.sleep.config.Constants;
import com.company.sleep.exception.DateAndTimeNeedsToBeUnique;
import com.company.sleep.model.SleepInfo;
import com.company.sleep.service.impl.SleepInfoServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = SleepInfoController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TestSleepInfoController {

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

    @Test
    @WithMockUser
    void whenUpdatedEntryIncorrect_shouldRedirectToUpdate() throws Exception {
        SleepInfo sleepInfo = SleepInfo.builder()
                .id(1L)
                .sleepDateTime(LocalDateTime.parse("2022-01-01 21:20", dateFormatter))
                .getUpDateTime(LocalDateTime.parse("2022-01-01 05:20", dateFormatter))
                .build();
        when(sleepInfoService.dateValidation(sleepInfo.getSleepDateTime(), sleepInfo.getGetUpDateTime()))
                .thenReturn(Constants.GET_UP_TIME_CANNOT_BE_LESS_THAN_SLEEP_TIME.toString());


        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("id", "1");
        multiValueMap.add("sleepDateTime", "2022-01-01 21:20");
        multiValueMap.add("getUpDateTime", "2022-01-01 05:20");

        mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .params(multiValueMap)).andExpect(redirectedUrl("/update/1"));


    }

    @Test
    @WithMockUser
    void whenCreateEntryIncorrect_shouldRedirectToCreate() throws Exception {
        SleepInfo sleepInfo = SleepInfo.builder()
                .sleepDateTime(LocalDateTime.parse("2022-01-01 21:20", dateFormatter))
                .getUpDateTime(LocalDateTime.parse("2022-01-01 05:20", dateFormatter))
                .build();
        when(sleepInfoService.dateValidation(sleepInfo.getSleepDateTime(), sleepInfo.getGetUpDateTime()))
                .thenReturn(Constants.GET_UP_TIME_CANNOT_BE_LESS_THAN_SLEEP_TIME.toString());


        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();

        multiValueMap.add("sleepDateTime", "2022-01-01 21:20");
        multiValueMap.add("getUpDateTime", "2022-01-01 05:20");

        mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .params(multiValueMap)).andExpect(redirectedUrl("/create"));

    }

    @Test
    void shouldCreateEntry() throws Exception, DateAndTimeNeedsToBeUnique {
        SleepInfo sleepInfo = SleepInfo.builder()
                .sleepDateTime(LocalDateTime.parse("2022-01-01 21:20", dateFormatter))
                .getUpDateTime(LocalDateTime.parse("2022-01-02 05:20", dateFormatter))
                .build();

        SleepInfo saveSleepInfo = SleepInfo.builder()
                .id(1L)
                .sleepDateTime(LocalDateTime.parse("2022-01-01 21:20", dateFormatter))
                .getUpDateTime(LocalDateTime.parse("2022-01-02 05:20", dateFormatter))
                .build();

        when(sleepInfoService.dateValidation(sleepInfo.getSleepDateTime(), sleepInfo.getGetUpDateTime()))
                .thenReturn(Constants.EMPTY_MESSAGE.toString());

        when(sleepInfoService.createEntry(sleepInfo)).thenReturn(saveSleepInfo);

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("sleepDateTime", "2022-01-01 21:20");
        multiValueMap.add("getUpDateTime", "2022-01-02 05:20");

        mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .params(multiValueMap)).andExpect(redirectedUrl("/"));

        verify(sleepInfoService, times(1)).createEntry(sleepInfo);
    }
}
