package com.marketahalikova.fopengineweb.services;

import com.marketahalikova.fopengineweb.commands.ProjectCommand;
import com.marketahalikova.fopengineweb.converters.ProjectCommandToProject;
import com.marketahalikova.fopengineweb.converters.ProjectToProjectCommand;
import com.marketahalikova.fopengineweb.exceptions.NotFoundException;
import com.marketahalikova.fopengineweb.model.Project;
import com.marketahalikova.fopengineweb.repositories.ProjectRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ProjectServiceImplTest {

    ProjectServiceImpl projectService;

    @Mock
    ProjectRepository projectRepository;
    @Mock
    ProjectCommandToProject projectCommandToProject;
    @Mock
    ProjectToProjectCommand projectToProjectCommand;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
       // projectRepository = mock(ProjectRepository.class);
        projectService = new ProjectServiceImpl(projectRepository, projectCommandToProject, projectToProjectCommand);
    }

    @Test
    public void getProjectsTest() throws Exception {
        Project project = new Project();
        HashSet projectsData = new HashSet();
        projectsData.add(project);

        when(projectService.getProjects()).thenReturn(projectsData);

        Set<Project> recipes = projectService.getProjects();

        assertThat(recipes).hasSize(1);
        verify(projectRepository, times(1)).findAll();
        verify(projectRepository, never()).findById(anyLong());
    }

    @Test
    public void findByIdTest() throws Exception {
        Project project = new Project();
        project.setId(1L);
        Optional<Project> projectOptional = Optional.of(project);

        when(projectRepository.findById(anyLong())).thenReturn(projectOptional);

        Project projectReturned = projectService.findById(1L);

        assertThat(projectReturned).isNotNull();
        verify(projectRepository, times(1)).findById(anyLong());
        verify(projectRepository, never()).findAll();
    }

    @Test(expected = NotFoundException.class)
    public void getProjectByIdTestNotFound() throws Exception {

        Optional<Project> projectOptional = Optional.empty();

        when(projectRepository.findById(anyLong())).thenReturn(projectOptional);

        Project recipeReturned = projectService.findById(1L);

        //should go boom
    }

    @Test
    public void getProjectCommandByIdTest() throws Exception {
        Project project = new Project();
        project.setId(1L);
        Optional<Project> projectOptional = Optional.of(project);

        when(projectRepository.findById(anyLong())).thenReturn(projectOptional);

        ProjectCommand projectCommand = new ProjectCommand();
        projectCommand.setId(1L);

        when(projectToProjectCommand.convert(any())).thenReturn(projectCommand);

        ProjectCommand commandById = projectService.findCommandById(1L);

        assertThat(commandById).isNotNull();
        verify(projectRepository, times(1)).findById(anyLong());
        verify(projectRepository, never()).findAll();
    }

    @Test
    public void saveProjectCommandTest() throws Exception {

        Project project = new Project();
        ProjectCommand projectCommand = new ProjectCommand();
        when(projectCommandToProject.convert(any())).thenReturn(project);
        when(projectToProjectCommand.convert(any())).thenReturn(projectCommand);
        when(projectRepository.save(any())).thenReturn(project);

        ProjectCommand projectCommandSaved = projectService.saveProjectCommand(projectCommand);

        assertThat(projectCommandSaved).isNotNull();
        verify(projectRepository, times(1)).save(any());
        verify(projectRepository, never()).saveAll(any());
        verify(projectCommandToProject, times(1)).convert(any());
        verify(projectToProjectCommand, times(1)).convert(any());
    }

    @Test
    public void saveProjectTest() throws Exception {
        Project project = new Project();
        when(projectRepository.save(any())).thenReturn(project);
        Project projectSaved = projectService.saveProject(any());
        assertThat(projectSaved).isNotNull();
        verify(projectRepository, times(1)).save(any());
        verify(projectRepository, never()).saveAll(any());
    }

    @Test
    public void testDeleteById() throws Exception {

        //given
        Long idToDelete = Long.valueOf(2L);
        //when
        projectService.deleteById(idToDelete);
        //no 'when', since method has void return type
        //then
        verify(projectRepository, times(1)).deleteById(anyLong());
    }
}