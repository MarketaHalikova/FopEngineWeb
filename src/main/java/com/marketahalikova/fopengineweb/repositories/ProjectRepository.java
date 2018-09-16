package com.marketahalikova.fopengineweb.repositories;

import com.marketahalikova.fopengineweb.model.Project;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Long> {
    List<Project> findAllByOrderByProjectNameAsc();
}
