package de.irs.fopengine.fopengineweb.services;

import de.irs.fopengine.fopengineweb.commands.ProjectDTO;
import de.irs.fopengine.fopengineweb.exceptions.FopEngineException;
import de.irs.fopengine.fopengineweb.git.GitService;
import de.irs.fopengine.fopengineweb.model.Project;
import de.irs.fopengine.fopengineweb.repositories.ProjectRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class ProjectServiceImplTest {

    public static final Long ID = 23L;
    public static final String PROJECT_DIRECTORY = "target/project-directory";

    ProjectService projectService;

    @Mock
    ProjectRepository projectRepository;
    GitService gitService;
    FileSystemService fileSystemService;
    XmlService xmlService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        projectRepository = mock(ProjectRepository.class);
        gitService = mock(GitService.class);
        fileSystemService = mock(FileSystemService.class);
        xmlService = mock(XmlService.class);

        projectService = new ProjectServiceImpl(projectRepository, gitService, fileSystemService,xmlService );
    }


    @Test
    public void getProjectsTest() throws Exception {
        Project project = new Project();
        List<Project> projectsData = new ArrayList<>();
        projectsData.add(project);

        given(projectRepository.findAllByOrderByProjectNameAsc()).willReturn(projectsData);

        Set<Project> projects = projectService.getProjects();

        assertThat(projects).hasSize(1);
        verify(projectRepository, times(1)).findAllByOrderByProjectNameAsc();
        verify(projectRepository, never()).findById(anyLong());
    }

    @Test
    public void findByIdTest() throws Exception {
        Project project = new Project();
        project.setId(1L);
        Optional<Project> projectOptional = Optional.of(project);

        when(projectRepository.findById(anyLong())).thenReturn(projectOptional);

        Project projectReturned = projectService.getProjectById(1L);

        assertThat(projectReturned).isNotNull();
        verify(projectRepository, times(1)).findById(anyLong());
        verify(projectRepository, never()).findAll();
    }

    @Test(expected = FopEngineException.class)
    public void getProjectByIdTestNotFound() throws Exception {

        Optional<Project> projectOptional = Optional.empty();

        when(projectRepository.findById(anyLong())).thenReturn(projectOptional);

        Project recipeReturned = projectService.getProjectById(1L);

        //should go boom
    }

    @Test
    public void getProjectCommandByIdTest() throws Exception {
        Project project = new Project();
        project.setId(1L);
        Optional<Project> projectOptional = Optional.of(project);

        when(projectRepository.findById(anyLong())).thenReturn(projectOptional);

        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(1L);

        ProjectDTO commandById = projectService.getProjectCommandById(1L);

        assertThat(commandById).isNotNull();
        verify(projectRepository, times(1)).findById(anyLong());
        verify(projectRepository, never()).findAll();
    }

    @Test
    public void saveProjectTest() throws Exception {
        Project project = new Project();
        project.setId(ID);
        Path projectDir = Paths.get(PROJECT_DIRECTORY);
        project.setProjectDirectory(projectDir);
        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));
        when(projectRepository.save(any())).thenReturn(project);
        Project projectSaved = projectService.updateProject(project);
        assertThat(projectSaved).isNotNull();
        verify(projectRepository, times(1)).save(any());
        verify(projectRepository, never()).saveAll(any());
    }

    @Test
    public void testDeleteProjectById() throws Exception {


        Long idToDelete = Long.valueOf(2L);
        Project project = new Project();
        project.setId(idToDelete);
        Optional<Project> projectOptional = Optional.of(project);
        when(projectRepository.findById(anyLong())).thenReturn(projectOptional);
        projectService.deleteProjectById(idToDelete);

        verify(projectRepository, times(1)).deleteById(anyLong());
    }
}