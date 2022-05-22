package com.company.sleep.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "sleep_info_entries")
public class SleepInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime sleepDateTime;

    @Column(unique = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime getUpDateTime;
    private Long hours;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SleepInfo sleepInfo = (SleepInfo) o;
        return hours == sleepInfo.hours
                && Objects.equals(id, sleepInfo.id)
                && Objects.equals(sleepDateTime, sleepInfo.sleepDateTime)
                && Objects.equals(getUpDateTime, sleepInfo.getUpDateTime);
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(id, sleepDateTime, getUpDateTime, hours);
    }
}
