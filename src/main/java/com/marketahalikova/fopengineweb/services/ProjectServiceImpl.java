package com.marketahalikova.fopengineweb.services;

import com.marketahalikova.fopengineweb.commands.ProjectCommand;
import com.marketahalikova.fopengineweb.converters.ProjectCommandToProject;
import com.marketahalikova.fopengineweb.converters.ProjectToProjectCommand;
import com.marketahalikova.fopengineweb.exceptions.NotFoundException;
import com.marketahalikova.fopengineweb.model.Project;
import com.marketahalikova.fopengineweb.repositories.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class ProjectServiceImpl implements ProjectService{

    private final ProjectRepository projectRepository;
    private final ProjectToProjectCommand projectToProjectCommand;
    private final ProjectCommandToProject projectCommandToProject;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectCommandToProject projectCommandToProject, ProjectToProjectCommand projectToProjectCommand) {
        this.projectRepository = projectRepository;
        this.projectCommandToProject = projectCommandToProject;
        this.projectToProjectCommand = projectToProjectCommand;
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
            throw new NotFoundException("Recipe Not Found");
        }

        return projectOptional.get();
    }

    @Transactional
    @Override
    public ProjectCommand saveProjectCommand(ProjectCommand projectCommand) {
        Project detachedProject = projectCommandToProject.convert(projectCommand);

        Project savedProject = projectRepository.save(detachedProject);
        log.debug("Saved ProjectId:" + savedProject.getId());
        return projectToProjectCommand.convert(savedProject);
    }

    @Transactional
    @Override
    public ProjectCommand findCommandById(Long l) {
        return projectToProjectCommand.convert(findById(l));
    }


    @Override
    public Project saveProject(Project detachedProject) {
        Project savedProject = projectRepository.save(detachedProject);
        log.debug("Saved project id: " + savedProject.getId());
        return savedProject;
    }
}
