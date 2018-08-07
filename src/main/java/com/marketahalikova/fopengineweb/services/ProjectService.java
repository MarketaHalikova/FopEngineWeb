package com.marketahalikova.fopengineweb.services;

import com.marketahalikova.fopengineweb.commands.ProjectCommand;
import com.marketahalikova.fopengineweb.model.Project;

import java.util.Set;

public interface ProjectService {
    Set<Project> getProjects();
    Project findById(Long l);
    ProjectCommand saveProjectCommand(ProjectCommand projectCommand);
}
