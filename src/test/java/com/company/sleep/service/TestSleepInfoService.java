package com.company.sleep.service;

import com.company.sleep.model.SleepInfo;
import com.company.sleep.repository.SleepInfoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestSleepInfoService {

    @Mock
    private SleepInfoRepository sleepInfoRepository;

    @InjectMocks
    private SleepInfoServiceImpl sleepInfoService;

    @Test
    void givenNewRecord_whenSaveRecord_thenReturnSavedRecord(){
        SleepInfo saveSleepInfo = SleepInfo.builder()
                .id(1L)
                .sleepDateTime(Timestamp.valueOf("2022-01-01 21:20:00"))
                .build();

        when(sleepInfoRepository.save(saveSleepInfo)).thenReturn(saveSleepInfo);

        SleepInfo actualEntry = sleepInfoService.createEntry(saveSleepInfo);

        assertEquals(saveSleepInfo, actualEntry);

    }

    @Test
    void givenNewRecord_whenSaveRecord_thenReturnUpdatedRecordWithHoursCalculated() {
        SleepInfo dbsleepInfo = SleepInfo.builder()
                .id(1L)
                .sleepDateTime(Timestamp.valueOf("2022-01-01 21:20:00"))
                .build();
        SleepInfo sleepInfo = SleepInfo.builder()
                .id(1L)
                .sleepDateTime(Timestamp.valueOf("2022-01-01 21:20:00"))
                .getUpDateTime(Timestamp.valueOf("2022-01-02 05:20:00"))
                .build();

        SleepInfo saveSleepInfo = SleepInfo.builder()
                .id(1L)
                .sleepDateTime(Timestamp.valueOf("2022-01-01 21:20:00"))
                .getUpDateTime(Timestamp.valueOf("2022-01-02 05:20:00"))
                .hours(8L)
                .build();

        when(sleepInfoRepository.findById(1L)).thenReturn(Optional.ofNullable(dbsleepInfo));
        when(sleepInfoRepository.save(saveSleepInfo)).thenReturn(saveSleepInfo);

        SleepInfo actualEntry = sleepInfoService.updateEntry(sleepInfo, 1L);

        assertEquals(saveSleepInfo, actualEntry);

    }
}
