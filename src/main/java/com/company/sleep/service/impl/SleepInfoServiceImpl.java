package com.company.sleep.service.impl;

import com.company.sleep.exception.RecordNotFoundException;
import com.company.sleep.model.SleepInfo;
import com.company.sleep.repository.SleepInfoRepository;
import com.company.sleep.service.SleepInfoService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class SleepInfoServiceImpl implements SleepInfoService {

    private final SleepInfoRepository sleepInfoRepository;

    public SleepInfoServiceImpl(SleepInfoRepository sleepInfoRepository) {
        this.sleepInfoRepository = sleepInfoRepository;
    }

    @Override
    public SleepInfo createEntry(SleepInfo sleepInfo) {


        if (sleepInfo.getGetUpDateTime() != null) {

            long hours = Duration.between(sleepInfo
                    .getSleepDateTime().toLocalDateTime(), sleepInfo
                    .getGetUpDateTime().toLocalDateTime()).getSeconds() / 3600;

            sleepInfo.setHours(hours);
        }

        return sleepInfoRepository.save(sleepInfo);
    }

    @Override
    public SleepInfo getEntryById(Long id) {

        return sleepInfoRepository.findById(id).orElseThrow(RecordNotFoundException::new);
    }

    @Override
    public SleepInfo updateEntry(SleepInfo sleepInfo, Long id) {
        SleepInfo dbSleepInfo = sleepInfoRepository.findById(id).orElseThrow(RecordNotFoundException::new);
        dbSleepInfo.setSleepDateTime(sleepInfo.getSleepDateTime());

        dbSleepInfo.setGetUpDateTime(sleepInfo.getGetUpDateTime());
        if (sleepInfo.getGetUpDateTime() != null) {

            long hours = Duration.between(sleepInfo
                    .getSleepDateTime().toLocalDateTime(), sleepInfo
                    .getGetUpDateTime().toLocalDateTime()).getSeconds() / 3600;

            dbSleepInfo.setHours(hours);
        }
        return sleepInfoRepository.save(dbSleepInfo);
    }

    @Override
    public List<SleepInfo> getAllEntries() {

        return sleepInfoRepository.findAllByOrderBySleepDateTimeDesc();
    }

    @Override
    public void deleteEntryById(Long id) {
        SleepInfo dbSleepInfo = sleepInfoRepository.findById(id).orElseThrow(RecordNotFoundException::new);
        sleepInfoRepository.delete(dbSleepInfo);
    }
}
