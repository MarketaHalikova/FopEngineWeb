package com.marketahalikova.fopengineweb.repositories;

import com.marketahalikova.fopengineweb.model.Job;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface JobRepository extends CrudRepository<Job, Long> {

    Optional<Job> findJobByJobName(String jobName);
}