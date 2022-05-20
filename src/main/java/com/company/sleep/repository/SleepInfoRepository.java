package com.company.sleep.repository;

import com.company.sleep.model.SleepInfo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SleepInfoRepository extends JpaRepository<SleepInfo, Long> {
}