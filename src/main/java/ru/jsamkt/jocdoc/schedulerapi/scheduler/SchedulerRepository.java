package ru.jsamkt.jocdoc.schedulerapi.scheduler;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "scheduler", path = "scheduler")
public interface SchedulerRepository extends MongoRepository<Scheduler, Scheduler.SchedulerKey> {
}