package de.irs.fopengine.fopengineweb.repositories;

import de.irs.fopengine.fopengineweb.model.Job;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface JobRepository extends CrudRepository<Job, Long> {

    Optional<Job> findJobByJobName(String jobName);
}