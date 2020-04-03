package ru.jsamkt.jocdoc.schedulerapi.scheduler;

import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDate;

@Component
public class SchedulerKeyConverter implements BackendIdConverter {
    @Override
    public Serializable fromRequestId(String s, Class<?> type) {
        return getSchedulerKeyByString(s);
    }

    @Override
    public String toRequestId(Serializable serializable, Class<?> type) {
        Scheduler.SchedulerKey key = (Scheduler.SchedulerKey) serializable;
        return getSchedulerKeyString(key.getClinicId(), key.getDoctorId(), key.getMonth());
    }

    @Override
    public boolean supports(Class<?> type) {
        return Scheduler.class.equals(type);
    }

    public static String getSchedulerKeyString(Long clinicId, Long doctorId, LocalDate month) {
        return String.format("%s_%s_%s",
                clinicId,
                doctorId,
                month
        );
    }

    public static Scheduler.SchedulerKey getSchedulerKeyByString(String str) {
        String[] vars = str.split("_");
        return new Scheduler.SchedulerKey(
                Long.valueOf(vars[0]),
                Long.valueOf(vars[1]),
                LocalDate.parse(vars[2])
        );
    }
}