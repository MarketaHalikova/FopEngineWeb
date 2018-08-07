package com.marketahalikova.fopengineweb.services;

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

        assertEquals(recipes.size(), 1);
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

        assertNotNull("Null recipe returned", projectReturned);
        verify(projectRepository, times(1)).findById(anyLong());
        verify(projectRepository, never()).findAll();
    }

    @Test(expected = NotFoundException.class)
    public void getRecipeByIdTestNotFound() throws Exception {

        Optional<Project> projectOptional = Optional.empty();

        when(projectRepository.findById(anyLong())).thenReturn(projectOptional);

        Project recipeReturned = projectService.findById(1L);

        //should go boom
    }
}