package com.marketahalikova.fopengineweb.services;

import com.marketahalikova.fopengineweb.commands.ProjectDTO;
import com.marketahalikova.fopengineweb.exceptions.FopEngineException;
import com.marketahalikova.fopengineweb.exceptions.GitException;
import com.marketahalikova.fopengineweb.exceptions.XmlException;
import com.marketahalikova.fopengineweb.model.Project;

import java.util.Set;

public interface ProjectService {

    Set<Project> getProjects() throws FopEngineException, GitException;
    Project getProjectById(Long id) throws FopEngineException, GitException;
    ProjectDTO getProjectCommandById(Long id) throws FopEngineException, GitException;
    void deleteProjectById(Long id) throws FopEngineException, GitException;
    Project updateProject(Project project) throws FopEngineException, GitException, XmlException;
    Project registerNewProject(ProjectDTO project) throws FopEngineException, XmlException;

}