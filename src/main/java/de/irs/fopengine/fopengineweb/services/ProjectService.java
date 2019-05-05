package de.irs.fopengine.fopengineweb.services;

import de.irs.fopengine.fopengineweb.commands.ProjectDTO;
import de.irs.fopengine.fopengineweb.exceptions.FopEngineException;
import de.irs.fopengine.fopengineweb.exceptions.GitException;
import de.irs.fopengine.fopengineweb.exceptions.XmlException;
import de.irs.fopengine.fopengineweb.model.Project;

import java.util.Set;

/**
 * Service layer for project handling
 */
public interface ProjectService {

    /**
     * Return all projects
     * @return all projects
     * @throws FopEngineException error in FOP engine
     * @throws GitException error in git
     */
    Set<Project> getProjects() throws FopEngineException, GitException;

    /**
     * Return project by Id
     * @param id project id
     * @return project by id
     * @throws FopEngineException
     * @throws GitException
     */
    Project getProjectById(Long id) throws FopEngineException, GitException;

    /**
     * Return project command by Id
     * @param id
     * @return project command by Id
     * @throws FopEngineException error in FOP engine
     * @throws GitException error in git
     */
    ProjectDTO getProjectCommandById(Long id) throws FopEngineException, GitException;

    /**
     * Delete project by Id
     * @param id project id
     * @throws FopEngineException error in FOP engine
     * @throws GitException error in git
     */
    void deleteProjectById(Long id) throws FopEngineException, GitException;

    /**
     * Update project
     * @param project project
     * @return updated project
     * @throws FopEngineException error in FOP engine
     * @throws GitException error in git
     * @throws XmlException error in configuration or in userconfig
     */
    Project updateProject(Project project) throws FopEngineException, GitException, XmlException;

    /**
     * Create a new project
     * @param project
     * @return new project
     * @throws FopEngineException error in FOP engine
     * @throws XmlException error in configuration or in userconfig
     */
    Project registerNewProject(ProjectDTO project) throws FopEngineException, XmlException;

}