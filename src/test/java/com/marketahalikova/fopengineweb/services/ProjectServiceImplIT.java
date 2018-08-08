package com.marketahalikova.fopengineweb.services;

import com.marketahalikova.fopengineweb.commands.ProjectCommand;
import com.marketahalikova.fopengineweb.converters.ProjectToProjectCommand;
import com.marketahalikova.fopengineweb.enums.ProjectStatus;
import com.marketahalikova.fopengineweb.model.Project;
import com.marketahalikova.fopengineweb.repositories.ProjectRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectServiceImplIT {
    public static final ProjectStatus NEW_PROJECT_STATUS = ProjectStatus.ARCHIVED;
    public static final String NEW_PROJECT_NAME = "new name";

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectToProjectCommand projectToProjectCommand;


    @Transactional
    @Test
    public void saveProjectCommand() {
        // given
        Iterable<Project> projects = projectRepository.findAll();
        Project project = projects.iterator().next();
        ProjectCommand projectCommand = projectToProjectCommand.convert(project);
        //when
        projectCommand.setProjectStatus(NEW_PROJECT_STATUS);
        projectCommand.setProjectName(NEW_PROJECT_NAME);
        //then
        ProjectCommand savedProject = projectService.saveProjectCommand(projectCommand);
        assertThat(savedProject.getProjectName()).isEqualTo(NEW_PROJECT_NAME);
        assertThat(savedProject.getProjectStatus()).isEqualTo(NEW_PROJECT_STATUS);
        assertThat(savedProject.getFontSet().size()).isEqualTo(project.getFontSet().size());
    }

}