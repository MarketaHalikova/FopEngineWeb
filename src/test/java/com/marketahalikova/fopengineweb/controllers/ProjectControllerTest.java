package com.marketahalikova.fopengineweb.controllers;

import com.marketahalikova.fopengineweb.commands.ProjectDTO;
import com.marketahalikova.fopengineweb.model.Project;
import com.marketahalikova.fopengineweb.services.ProjectService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProjectControllerTest {

    public static final Long ID = 1L;
    @Mock
    ProjectService projectService;

    @Mock
    Model model;

    ProjectController controller;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new ProjectController(projectService);
    }

    @Test
    public void testMockMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("projectlist"));
    }

    @Test
    public void getProjectsTest() throws Exception {

        //given

        Project project1 = new Project();
        project1.setId(1L);
        Project project2 = new Project();
        project2.setId(2L);

        Set<Project> projects = new HashSet<>();
        projects.add(project1);
        projects.add(project2);

        Set<Project> projects1 = new HashSet<>();
        projects1.add(project1);
        projects1.add(project2);

        when(projectService.getProjects()).thenReturn(projects);

        // -------------------------------------------------------------

//        ArgumentCaptor<Set<Project>> argumentCaptor = ArgumentCaptor.forClass(Set.class);
//
//        //when
//        String viewName = controller.getIndexPage(model);
//
//
//        //then
//        assertEquals("projectlist", viewName);
//        verify(projectService, times(1)).getProjects();
//        verify(model, times(1)).addAttribute(eq("projectList"), argumentCaptor.capture());
//        Set<Project> setInController = argumentCaptor.getValue();
//        assertEquals(2, setInController.size());


        // ------------------------------------------------

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
       when(projectService.getProjects()).thenReturn(projects);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("projectlist"))
                .andExpect(model().attribute("projectList", projects1))
                .andExpect(model().attributeExists("projectList"));

        mockMvc.perform(get(""))
                .andExpect(status().isOk());
        mockMvc.perform(get("/project"))
                .andExpect(status().isOk());
    }


    @Test
    public void showByIdTest() throws Exception {
        // given
        Project project = new Project();
        project.setId(ID);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        when(projectService.findById(eq(1L))).thenReturn(project);

        // when
        mockMvc.perform(get("/project/1/show"))
                //then
                .andExpect(status().isOk())
                .andExpect(model().attribute("project", project))
                .andExpect(model().attributeExists("project"))
                .andExpect(view().name("project/show"));
    }

    @Test
    public void newProjectFormTest() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(get("/project/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("project/projectform"))
                .andExpect(model().attributeExists("project"));
    }

    @Test
    public void saveOrUpdateProjectTest() throws Exception {
        // given
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(ID);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        when(projectService.saveProjectCommand(any())).thenReturn(projectDTO);

        // when
        mockMvc.perform(post("/project")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("gitPath", "some gitPath")
                    .param("description", "some string"))
                //then
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/project/" + projectDTO.getId() + "/show"));
        verify(projectService, times(1)).saveProjectCommand(any());
       }

    @Test
    public void testDeleteAction() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(get("/project/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(projectService, times(1)).deleteById(anyLong());
    }



}

