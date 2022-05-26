package com.company.sleep.service.impl;

import com.company.sleep.config.Constants;
import com.company.sleep.exception.DateAndTimeNeedsToBeUnique;
import com.company.sleep.exception.GetUpTimeLessThanSleepTime;
import com.company.sleep.exception.RecordNotFoundException;
import com.company.sleep.model.SleepInfo;
import com.company.sleep.repository.SleepInfoRepository;
import com.company.sleep.service.SleepInfoService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SleepInfoServiceImpl implements SleepInfoService {

    private final SleepInfoRepository sleepInfoRepository;

    public SleepInfoServiceImpl(SleepInfoRepository sleepInfoRepository) {
        this.sleepInfoRepository = sleepInfoRepository;
    }

    @Override
    public SleepInfo createEntry(SleepInfo sleepInfo) throws DateAndTimeNeedsToBeUnique {
        try {
            if (sleepInfo.getId() != null) {
                return updateEntry(sleepInfo, sleepInfo.getId());
            }

            if ("" != dateValidation(sleepInfo.getSleepDateTime(), sleepInfo.getGetUpDateTime())) {
                throw new GetUpTimeLessThanSleepTime();
            }

            if (sleepInfo.getGetUpDateTime() != null) {

                calculateHours(sleepInfo);
            }
            return sleepInfoRepository.save(sleepInfo);
        } catch (DataIntegrityViolationException exception) {
            throw new DateAndTimeNeedsToBeUnique();
        }

    }

    public void calculateHours(SleepInfo sleepInfo) {
        sleepInfo.setHours(Duration.between(sleepInfo.getSleepDateTime(), sleepInfo.getGetUpDateTime()).getSeconds()
                / 3600);

    }

    @Override
    public SleepInfo getEntryById(Long id) {

        return sleepInfoRepository.findById(id).orElseThrow(RecordNotFoundException::new);
    }

    @Override
    public SleepInfo updateEntry(SleepInfo sleepInfo, Long id) {

        if ("" != dateValidation(sleepInfo.getSleepDateTime(), sleepInfo.getGetUpDateTime())) {
            throw new GetUpTimeLessThanSleepTime();
        }

        SleepInfo dbSleepInfo = sleepInfoRepository.findById(id).orElseThrow(RecordNotFoundException::new);
        dbSleepInfo.setSleepDateTime(sleepInfo.getSleepDateTime());

        dbSleepInfo.setGetUpDateTime(sleepInfo.getGetUpDateTime());

        if (dbSleepInfo.getGetUpDateTime() != null) {

            calculateHours(dbSleepInfo);

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

    @Override
    public String dateValidation(LocalDateTime sleepDateTime, LocalDateTime getUpDateTime) {
        String message = "";
        if (sleepDateTime == null) {
            message = Constants.SLEEP_TIME_CANNOT_BE_EMPTY.toString();
        } else if (getUpDateTime != null && sleepDateTime.isAfter(getUpDateTime)) {
            message = Constants.GET_UP_TIME_CANNOT_BE_LESS_THAN_SLEEP_TIME.toString();
        }
        return message;
    }
}
