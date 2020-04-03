package ru.jsamkt.jocdoc.schedulerapi.scheduler;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import ru.jsamkt.jocdoc.schedulerapi.exception.SchedulerValidationException;

import java.util.stream.Stream;

@Aspect
@Component
public class SchedulerSaveVerifierAspect {


    @Before("execution(* ru.jsamkt.jocdoc.schedulerapi.scheduler.SchedulerRepository.save(..) ) ")
    public void save(JoinPoint joinPoint) {
        Stream.of(joinPoint.getArgs())
                .filter(SchedulerSaveVerifierAspect::isInstanceOfScheduler)
                .map(SchedulerSaveVerifierAspect::castToScheduler)
                .filter(SchedulerSaveVerifierAspect::isWorkDaysValid)
                .findFirst()
                .orElseThrow(() -> new SchedulerValidationException("Ошибка валидации объекта Scheduler"));
    }

    private static boolean isInstanceOfScheduler(Object object) {
        if (object instanceof Scheduler) {
            return true;
        }

        throw new SchedulerValidationException("Объект не является классом Scheduler");
    }

    private static Scheduler castToScheduler(Object object) {
        return (Scheduler) object;
    }

    private static boolean isWorkDaysValid(Scheduler scheduler) {
        if (
                scheduler.getKey().getMonth().getDayOfMonth() == 1
                        &&
                        scheduler.getWorkDays()
                                .keySet()
                                .stream()
                                .map(d -> d.withDayOfMonth(1))
                                .allMatch(scheduler.getKey().getMonth()::equals)) {
            return true;
        }

        throw new SchedulerValidationException("Неверная дата");
    }

}