package com.company.sleep.service;

import com.company.sleep.model.SleepInfo;

import java.util.List;

public interface SleepInfoService {
    SleepInfo createEntry(SleepInfo sleepInfo);
    SleepInfo getEntryById(Long id);
    SleepInfo updateEntry(SleepInfo sleepInfo, Long id);
    List<SleepInfo> getAllEntries();
}
