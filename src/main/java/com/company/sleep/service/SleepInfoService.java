package com.company.sleep.service;

import com.company.sleep.exception.DateAndTimeNeedsToBeUnique;
import com.company.sleep.model.SleepInfo;

import java.time.LocalDateTime;
import java.util.List;

public interface SleepInfoService {
    SleepInfo createEntry(SleepInfo sleepInfo) throws DateAndTimeNeedsToBeUnique;

    SleepInfo getEntryById(Long id);

    SleepInfo updateEntry(SleepInfo sleepInfo, Long id);

    List<SleepInfo> getAllEntries();

    void deleteEntryById(Long id);

    String dateValidation(LocalDateTime sleepDateTime, LocalDateTime getUpDateTime);
}
