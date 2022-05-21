package com.company.sleep.service;

import com.company.sleep.model.SleepInfo;
import com.company.sleep.repository.SleepInfoRepository;
import com.company.sleep.service.impl.SleepInfoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestSleepInfoService {

    @Mock
    private SleepInfoRepository sleepInfoRepository;

    @InjectMocks
    private SleepInfoServiceImpl sleepInfoService;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Test
    void givenNewRecord_whenSaveRecord_thenReturnSavedRecord() {

        SleepInfo sleepInfo = SleepInfo.builder()
                .id(null)
                .sleepDateTime(LocalDateTime.parse("2022-01-01 21:20", dateFormatter))
                .build();

        SleepInfo saveSleepInfo = SleepInfo.builder()
                .id(1L)
                .sleepDateTime(LocalDateTime.parse("2022-01-01 21:20", dateFormatter))
                .build();

        when(sleepInfoRepository.save(sleepInfo)).thenReturn(saveSleepInfo);

        SleepInfo actualEntry = sleepInfoService.createEntry(sleepInfo);

        assertEquals(saveSleepInfo, actualEntry);

    }

    @Test
    void givenNewRecordWithBothSleepAndGetupTime_whenSaveRecord_thenReturnSavedRecordWithHours() {
        SleepInfo sleepInfo = SleepInfo.builder()
                .id(null)
                .sleepDateTime(LocalDateTime.parse("2022-01-01 21:20", dateFormatter))
                .getUpDateTime(LocalDateTime.parse("2022-01-02 05:20", dateFormatter))
                .build();

        SleepInfo saveSleepInfo = SleepInfo.builder()
                .id(null)
                .sleepDateTime(LocalDateTime.parse("2022-01-01 21:20", dateFormatter))
                .getUpDateTime(LocalDateTime.parse("2022-01-02 05:20", dateFormatter))
                .hours(8L)
                .build();

        SleepInfo repositoryReturnSleepInfo = SleepInfo.builder()
                .id(1L)
                .sleepDateTime(LocalDateTime.parse("2022-01-01 21:20", dateFormatter))
                .getUpDateTime(LocalDateTime.parse("2022-01-02 05:20", dateFormatter))
                .hours(8L)
                .build();

        when(sleepInfoRepository.save(saveSleepInfo)).thenReturn(repositoryReturnSleepInfo);

        SleepInfo actualEntry = sleepInfoService.createEntry(sleepInfo);

        assertEquals(repositoryReturnSleepInfo, actualEntry);

    }


    @Test
    void givenId_whenFindById_thenReturnRecordById() {
        SleepInfo sleepInfo = SleepInfo.builder()
                .id(1L)
                .sleepDateTime(LocalDateTime.parse("2022-01-01 21:20", dateFormatter))
                .build();


        when(sleepInfoRepository.findById(1L)).thenReturn(Optional.ofNullable(sleepInfo));

        SleepInfo actualEntry = sleepInfoService.getEntryById(1L);

        assertEquals(sleepInfo, actualEntry);

    }

    @Test
    void givenNewRecord_whenSaveRecord_thenReturnUpdatedRecordWithHoursCalculated() {
        SleepInfo dbsleepInfo = SleepInfo.builder()
                .id(1L)
                .sleepDateTime(LocalDateTime.parse("2022-01-01 21:20", dateFormatter))
                .build();
        SleepInfo sleepInfo = SleepInfo.builder()
                .id(1L)
                .sleepDateTime(LocalDateTime.parse("2022-01-01 21:20", dateFormatter))
                .getUpDateTime(LocalDateTime.parse("2022-01-02 05:20", dateFormatter))
                .build();

        SleepInfo saveSleepInfo = SleepInfo.builder()
                .id(1L)
                .sleepDateTime(LocalDateTime.parse("2022-01-01 21:20", dateFormatter))
                .getUpDateTime(LocalDateTime.parse("2022-01-02 05:20", dateFormatter))
                .hours(8L)
                .build();

        when(sleepInfoRepository.findById(1L)).thenReturn(Optional.ofNullable(dbsleepInfo));
        when(sleepInfoRepository.save(saveSleepInfo)).thenReturn(saveSleepInfo);

        SleepInfo actualEntry = sleepInfoService.updateEntry(sleepInfo, 1L);

        assertEquals(saveSleepInfo, actualEntry);

    }

    @Test
    void whenFindAllRecords_thenReturnAllRecords() {
        SleepInfo dbsleepInfoFirstRecord = SleepInfo.builder()
                .id(1L)
                .sleepDateTime(LocalDateTime.parse("2022-01-01 21:20", dateFormatter))
                .getUpDateTime(LocalDateTime.parse("2022-01-02 05:20", dateFormatter))
                .hours(8L)
                .build();

        SleepInfo dbsleepInfoSecondRecord = SleepInfo.builder()
                .id(2L)
                .sleepDateTime(LocalDateTime.parse("2022-01-02 21:20", dateFormatter))
                .build();

        List<SleepInfo> sleepInfoList = new ArrayList<>();
        sleepInfoList.add(dbsleepInfoFirstRecord);
        sleepInfoList.add(dbsleepInfoSecondRecord);

        when(sleepInfoRepository.findAllByOrderBySleepDateTimeDesc()).thenReturn(sleepInfoList);

        List<SleepInfo> actualSleepInfoList = sleepInfoService.getAllEntries();

        assertEquals(sleepInfoList, actualSleepInfoList);

    }

    @Test
    void givenId_whenDeleteRecord_thenDeleteRecord() {
        SleepInfo dbsleepInfo = SleepInfo.builder()
                .id(1L)
                .sleepDateTime(LocalDateTime.parse("2022-01-01 21:20", dateFormatter))
                .build();

        when(sleepInfoRepository.findById(1L)).thenReturn(Optional.ofNullable(dbsleepInfo));

        doNothing().when(sleepInfoRepository).delete(dbsleepInfo);

        sleepInfoService.deleteEntryById(1L);

        verify(sleepInfoRepository, times(1)).delete(dbsleepInfo);

    }
}
