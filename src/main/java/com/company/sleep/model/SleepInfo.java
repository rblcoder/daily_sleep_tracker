package com.company.sleep.model;

import lombok.*;
import lombok.Builder;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name="sleep_info_entries")
public class SleepInfo  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private Timestamp sleepDateTime;


    private Timestamp getUpDateTime;
    private Long hours;

    public SleepInfo(Timestamp sleepDateTime) {
        this.sleepDateTime = sleepDateTime;

    }

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
