package com.company.sleep.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestSleepInfoModel {

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Test
    void testEquals(){
        SleepInfo sleepInfo = SleepInfo.builder()
                .id(1L)
                .sleepDateTime(LocalDateTime.parse("2022-01-01 21:20", dateFormatter))
                .getUpDateTime(LocalDateTime.parse("2022-01-02 05:20", dateFormatter))
                .hours(8L)
                .build();

        SleepInfo sleepInfoAnother = SleepInfo.builder()
                .id(1L)
                .sleepDateTime(LocalDateTime.parse("2022-01-01 21:20", dateFormatter))
                .getUpDateTime(LocalDateTime.parse("2022-01-02 05:20", dateFormatter))
                .hours(8L)
                .build();

        Assertions.assertEquals(sleepInfo, sleepInfoAnother);
    }

    @Test
    void testHashCode(){
        SleepInfo sleepInfo = SleepInfo.builder()
                .id(1L)
                .sleepDateTime(LocalDateTime.parse("2022-01-01 21:20", dateFormatter))
                .getUpDateTime(LocalDateTime.parse("2022-01-02 05:20", dateFormatter))
                .hours(8L)
                .build();

        SleepInfo sleepInfoAnother = SleepInfo.builder()
                .id(1L)
                .sleepDateTime(LocalDateTime.parse("2022-01-01 21:20", dateFormatter))
                .getUpDateTime(LocalDateTime.parse("2022-01-02 05:20", dateFormatter))
                .hours(8L)
                .build();

        Assertions.assertEquals(sleepInfo.hashCode(), sleepInfoAnother.hashCode());

    }

    @Test
    void testEqualsObjectVsNull(){
        SleepInfo sleepInfo = SleepInfo.builder()
                .id(1L)
                .sleepDateTime(LocalDateTime.parse("2022-01-01 21:20", dateFormatter))
                .getUpDateTime(LocalDateTime.parse("2022-01-02 05:20", dateFormatter))
                .hours(8L)
                .build();
        Assertions.assertNotEquals(sleepInfo, null);
    }
}
