package de.irs.fopengine.fopengineweb.repositories;

import de.irs.fopengine.fopengineweb.model.Project;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Long> {
    List<Project> findAllByOrderByProjectNameAsc();
}
