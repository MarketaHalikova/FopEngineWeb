package com.marketahalikova.fopengineweb.services;

import com.marketahalikova.fopengineweb.model.Project;
import com.marketahalikova.fopengineweb.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ProjectServiceImpl implements ProjectService{

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Set<Project> getProjects() {
        Set<Project> projects = new HashSet<>();
        projectRepository.findAll().forEach(projects::add);
        return projects;
    }

    @Override
    public Project findById(Long l) {

        Optional<Project> projectOptional = projectRepository.findById(l);

        if (!projectOptional.isPresent()) {
            throw new RuntimeException("Recipe Not Found!");
        }

        return projectOptional.get();
    }

}
