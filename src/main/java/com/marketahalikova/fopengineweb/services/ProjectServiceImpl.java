package com.marketahalikova.fopengineweb.services;

import com.marketahalikova.fopengineweb.commands.ProjectDTO;
import com.marketahalikova.fopengineweb.exceptions.NotFoundException;
import com.marketahalikova.fopengineweb.mappers.ProjectMapper;
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
            throw new NotFoundException("Recipe Not Found");
        }

        return projectOptional.get();
    }

    @Transactional
    @Override
    public ProjectDTO saveProjectCommand(ProjectDTO projectDTO) {
        Project detachedProject = ProjectMapper.INSTANCE.projectCommandToProject(projectDTO);

        Project savedProject = projectRepository.save(detachedProject);
        log.debug("Saved ProjectId:" + savedProject.getId());
        return ProjectMapper.INSTANCE.projectToProjectCommand(savedProject);
    }

    @Transactional
    @Override
    public ProjectDTO findCommandById(Long l) {
        return ProjectMapper.INSTANCE.projectToProjectCommand(findById(l));
    }


    @Override
    public Project saveProject(Project detachedProject) {
        Project savedProject = projectRepository.save(detachedProject);
        log.debug("Saved project id: " + savedProject.getId());
        return savedProject;
    }

    @Override
    public void deleteById(Long idToDelete) {
        projectRepository.deleteById(idToDelete);
    }
}
