package com.marketahalikova.fopengineweb.controllers;

import com.marketahalikova.fopengineweb.model.Project;
import com.marketahalikova.fopengineweb.services.ProjectService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class ProjectListControllerTest {

    @Mock
    ProjectService projectService;

    @Mock
    Model model;

    ProjectListController controller;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new ProjectListController(projectService);
    }

    @Test
    public void testMockMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("projectlist"));
    }

    @Test
    public void getIndexPageTest() throws Exception {

        //given
        Set<Project> projects = new HashSet<>();
        projects.add(new Project());

        Project project = new Project();
        project.setId(1L);

        projects.add(project);

        when(projectService.getProjects()).thenReturn(projects);

        ArgumentCaptor<Set<Project>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.getIndexPage(model);


        //then
        assertEquals("projectlist", viewName);
        verify(projectService, times(1)).getProjects();
        verify(model, times(1)).addAttribute(eq("projectList"), argumentCaptor.capture());
        Set<Project> setInController = argumentCaptor.getValue();
        assertEquals(2, setInController.size());
    }
}