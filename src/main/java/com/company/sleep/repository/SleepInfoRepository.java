package com.company.sleep.repository;

import com.company.sleep.model.SleepInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SleepInfoRepository extends JpaRepository<SleepInfo, Long> {
    List<SleepInfo> findAllByOrderBySleepDateTimeDesc();
}