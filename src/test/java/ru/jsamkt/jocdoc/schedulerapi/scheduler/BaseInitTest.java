package ru.jsamkt.jocdoc.schedulerapi.scheduler;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

public class BaseInitTest {

    static final Long clinicID = 123L;
    static final Long doctorID = 321L;
    static final LocalDate month = LocalDate.parse("2020-02-01");

    @Autowired
    SchedulerRepository repository;

    @Before
    public void setUp() {

        Scheduler scheduler = Scheduler.builder()
                .key(Scheduler.SchedulerKey.builder()
                        .clinicId(clinicID)
                        .doctorId(doctorID)
                        .month(month)
                        .build())
                .workDays(
                        new HashMap<LocalDate, Scheduler.TimeRange>() {{
                            put(month.withDayOfMonth(1),
                                    Scheduler.TimeRange.builder()
                                            .from(LocalTime.parse("08:00"))
                                            .to(LocalTime.parse("20:00"))
                                            .build());

                            put(month.withDayOfMonth(2),
                                    Scheduler.TimeRange.builder()
                                            .from(LocalTime.parse("08:00"))
                                            .to(LocalTime.parse("20:00"))
                                            .build());

                            put(month.withDayOfMonth(3),
                                    Scheduler.TimeRange.builder()
                                            .from(LocalTime.parse("09:00"))
                                            .to(LocalTime.parse("21:00"))
                                            .build());

                            put(month.withDayOfMonth(4),
                                    Scheduler.TimeRange.builder()
                                            .from(LocalTime.parse("13:00"))
                                            .to(LocalTime.parse("19:00"))
                                            .build());
                        }}
                )
                .lunchTime(Scheduler.TimeRange.builder()
                        .from(LocalTime.parse("13:00"))
                        .to(LocalTime.parse("14:00"))
                        .build())
                .build();

        repository.save(scheduler);
    }
}
