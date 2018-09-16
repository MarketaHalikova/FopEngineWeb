package com.marketahalikova.fopengineweb.services;

import com.marketahalikova.fopengineweb.commands.ProjectDTO;
import com.marketahalikova.fopengineweb.enums.ProjectStatus;
import com.marketahalikova.fopengineweb.exceptions.FopEngineException;
import com.marketahalikova.fopengineweb.exceptions.GitException;
import com.marketahalikova.fopengineweb.exceptions.XmlException;
import com.marketahalikova.fopengineweb.git.GitService;
import com.marketahalikova.fopengineweb.mappers.ProjectMapper;
import com.marketahalikova.fopengineweb.model.Project;
import com.marketahalikova.fopengineweb.model.User;
import com.marketahalikova.fopengineweb.repositories.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final GitService gitService;
    private final FileSystemService fileSystemService;
    private final XmlService xmlService;
    private User user = new User("testUser", "");
    public static final String COMMIT_MESSAGE = "Commit updated project";

    public ProjectServiceImpl(ProjectRepository projectRepository, GitService gitService,
                              FileSystemService fileSystemService, XmlService xmlService) {
        this.projectRepository = projectRepository;
        this.gitService = gitService;
        this.fileSystemService = fileSystemService;
        this.xmlService = xmlService;
    }

    @Override
    public Set<Project> getProjects() throws GitException {
        Set<Project> projects = new LinkedHashSet<>();
        projectRepository.findAllByOrderByProjectNameAsc().iterator().forEachRemaining(projects::add);
        gitService.matchAllProjects(projects);
        return projects;
    }

    @Override
    public Project getProjectById(Long id) throws FopEngineException, GitException {
        Optional<Project> result = projectRepository.findById(id);
        Project project = result.orElseThrow(() -> new FopEngineException("Project not found. Id = " + id));
        gitService.matchRemote(project.getProjectDirectory());
        return project;
    }

    @Override
    public ProjectDTO getProjectCommandById(Long id) throws FopEngineException, GitException {
        return ProjectMapper.INSTANCE.projectToProjectDTO(getProjectById(id));
    }


    @Override
    public void deleteProjectById(Long id) throws FopEngineException, GitException {
        Project project = getProjectById(id);
        fileSystemService.deleteProjectDirectory(project);
        projectRepository.deleteById(id);
    }

    @Override
    public Project updateProject(Project detachedProject) throws FopEngineException, GitException, XmlException {
        Optional<Project> result = projectRepository.findById(detachedProject.getId());
        Project backupProjectFromDatabase = result.orElseThrow(() -> new FopEngineException("Project not found. Id = "
                + detachedProject.getId()));
        try {
            gitService.matchRemote(detachedProject.getProjectDirectory());
        } catch (GitException e) {
            log.error("Matching project with id:" + detachedProject.getId() + " was unsuccessful, project was not updated");
            throw e;
        }
        try {
            xmlService.updateProjectInXml(detachedProject);
        } catch (XmlException e) {
            log.error("Updating xml of project withid: " + detachedProject.getId() + " was unsuccessful, project was not updated");
            throw e;
        }
        try {
            gitService.commitProject(detachedProject.getProjectDirectory(),
                    COMMIT_MESSAGE, user);
            detachedProject.setProjectStatus(ProjectStatus.CHANGED);
            Project savedProject = projectRepository.save(detachedProject);
            log.debug("Saved updated project id: " + savedProject.getId());
            return savedProject;
        } catch (GitException e) {
            log.error("Error while committing project with id: " + detachedProject.getId()+ " - project was not updated");
            xmlService.updateProjectInXml(backupProjectFromDatabase);
            throw e;
        }
    }

    @Override
    public Project registerNewProject(ProjectDTO projectCommand) throws FopEngineException, XmlException {
        Path projectPath = fileSystemService.createProjectDirectory(projectCommand.getGitPath());
        try {
            gitService.cloneRepository(projectCommand.getGitPath(), projectPath);
        } catch (GitException e) {
            throw new FopEngineException(String.format("Can't clone remote git repository %s to %s ", projectCommand.getGitPath(), projectPath), e);
        }
        Project newProject = xmlService.readProject(projectCommand.getGitPath(), projectPath);
        if(projectCommand.getDescription()!= null && !projectCommand.getDescription().equals("")){
            newProject.setDescription(projectCommand.getDescription());
        }
        return projectRepository.save(newProject);
    }

}
