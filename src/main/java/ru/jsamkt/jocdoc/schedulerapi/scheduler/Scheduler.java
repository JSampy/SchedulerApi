package ru.jsamkt.jocdoc.schedulerapi.scheduler;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Document
public class Scheduler {

    @Id
    @NotNull
    private SchedulerKey key;

    @NotNull
    private Map<LocalDate, TimeRange> workDays;

    @NotNull
    private TimeRange lunchTime;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SchedulerKey implements Serializable {

        @NotNull
        private Long clinicId;

        @NotNull
        private Long doctorId;

        @NotNull
        private LocalDate month;
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class TimeRange implements Serializable {
        @NotNull
        private LocalTime from;

        @NotNull
        private LocalTime to;
    }
}