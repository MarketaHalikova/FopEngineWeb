package com.marketahalikova.fopengineweb.controllers;

import com.marketahalikova.fopengineweb.model.Project;
import com.marketahalikova.fopengineweb.services.ProjectService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class ProjectShowControllerTest {

    @Mock
    ProjectService projectService;

    ProjectShowController controller;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new ProjectShowController(projectService);
    }

    @Test
    public void showByIdTest() throws Exception {
        Project recipe = new Project();
        recipe.setId(1L);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        when(projectService.findById(anyLong())).thenReturn(recipe);

        mockMvc.perform(get("/project/show/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("project/show"));
    }
}