package ru.jsamkt.jocdoc.schedulerapi.scheduler;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RunWith(SpringRunner.class)
@DataMongoTest()
public class SchedulerRepositoryTest extends BaseInitTest {


    @Test
    public void testGetByKey() {
        Scheduler.SchedulerKey key = Scheduler.SchedulerKey.builder()
                .month(month)
                .doctorId(doctorID)
                .clinicId(clinicID)
                .build();

        Scheduler scheduler = repository.findById(key).get();

        Assert.assertEquals(Scheduler.TimeRange.builder()
                        .from(LocalTime.parse("13:00"))
                        .to(LocalTime.parse("14:00"))
                        .build(),
                scheduler.getLunchTime());

        Assert.assertEquals(4,
                scheduler.getWorkDays().size());
    }

    @Test
    public void testGetAll() {
        List<Scheduler> schedulers = StreamSupport.stream(repository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        Assert.assertEquals(
                1,
                schedulers.size()
        );
    }

}