package de.irs.fopengine.fopengineweb.services;

import de.irs.fopengine.fopengineweb.commands.ProjectDTO;
import de.irs.fopengine.fopengineweb.enums.ProjectStatus;
import de.irs.fopengine.fopengineweb.exceptions.FopEngineException;
import de.irs.fopengine.fopengineweb.exceptions.GitException;
import de.irs.fopengine.fopengineweb.exceptions.XmlException;
import de.irs.fopengine.fopengineweb.git.GitService;
import de.irs.fopengine.fopengineweb.mappers.ProjectMapper;
import de.irs.fopengine.fopengineweb.model.Project;
import de.irs.fopengine.fopengineweb.model.User;
import de.irs.fopengine.fopengineweb.repositories.ProjectRepository;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Project> getProjects() throws GitException {
        Set<Project> projects = new LinkedHashSet<>();
        projectRepository.findAllByOrderByProjectNameAsc().iterator().forEachRemaining(projects::add);
        gitService.matchAllProjects(projects);
        return projects;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Project getProjectById(Long id) throws FopEngineException, GitException {
        Optional<Project> result = projectRepository.findById(id);
        Project project = result.orElseThrow(() -> new FopEngineException("Project not found. Id = " + id));
        gitService.matchRemote(project.getProjectDirectory());
        return project;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectDTO getProjectCommandById(Long id) throws FopEngineException, GitException {
        return ProjectMapper.INSTANCE.projectToProjectDTO(getProjectById(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteProjectById(Long id) throws FopEngineException, GitException {
        Project project = getProjectById(id);
        fileSystemService.deleteProjectDirectory(project);
        projectRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
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
            log.error("Error while committing project with id: " + detachedProject.getId() + " - project was not updated");
            xmlService.updateProjectInXml(backupProjectFromDatabase);
            throw e;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Project registerNewProject(ProjectDTO projectCommand) throws FopEngineException, XmlException {
        Path projectPath = fileSystemService.createProjectDirectory(projectCommand.getGitUrl());
        try {
            gitService.cloneRepository(projectCommand.getGitUrl(), projectPath);
        } catch (GitException e) {
            throw new FopEngineException(String.format("Can't clone remote git repository %s to %s ", projectCommand.getGitUrl(), projectPath), e);
        }
        Project newProject = xmlService.readProject(projectCommand.getGitUrl(), projectPath);
        if (projectCommand.getDescription() != null && !projectCommand.getDescription().equals("")) {
            newProject.setDescription(projectCommand.getDescription());
        }
        return projectRepository.save(newProject);
    }

}
