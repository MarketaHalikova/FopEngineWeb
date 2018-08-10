package com.marketahalikova.fopengineweb.services;

import com.marketahalikova.fopengineweb.commands.ProjectDTO;
import com.marketahalikova.fopengineweb.model.Project;

import java.util.Set;

public interface ProjectService {
    Set<Project> getProjects();
    Project findById(Long l);
    ProjectDTO saveProjectCommand(ProjectDTO projectDTO);
    ProjectDTO findCommandById (Long l);
    Project saveProject(Project project);
    void deleteById(Long idToDelete);
}
