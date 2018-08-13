package com.marketahalikova.fopengineweb.mappers;

import com.marketahalikova.fopengineweb.commands.ProjectDTO;
import com.marketahalikova.fopengineweb.enums.ProjectStatus;
import com.marketahalikova.fopengineweb.model.Font;
import com.marketahalikova.fopengineweb.model.Project;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectMapperTest {


    public static final String DESCRIPTION = "description";
    public static final Long LONG_VALUE = new Long(1L);
    public static final String PROJECT_NAME = "description";
    public static final String GIT_PATH = "description";
    public static final ProjectStatus STATUS = ProjectStatus.ARCHIVED;
    public static final Set<Font> fontSet = new HashSet<>();

    ProjectMapper converter;

    @Before
    public void setUp() throws Exception {
        converter = ProjectMapper.INSTANCE;
    }

    @Test
    public void testNullObjectConvert() throws Exception {
        assertThat(converter.projectToProjectDTO(null)).isNull();
        assertThat(converter.projectDTOToProject(null)).isNull();
    }

    @Test
    public void testEmptyObj() throws Exception {
        assertThat(converter.projectToProjectDTO(new Project())).isNotNull();
        assertThat(converter.projectDTOToProject(new ProjectDTO())).isNotNull();
    }

    @Test
    public void convertProjectToProjectDTO() {
        Font font1 = new Font("font1");
        fontSet.add(font1);

        //given
        Project project = new Project();
        project.setId(LONG_VALUE);
        project.setDescription(DESCRIPTION);
        project.setProjectName(PROJECT_NAME);
        project.setGitPath(GIT_PATH);
        project.setProjectStatus(STATUS);
        project.setFontSet(fontSet);
        //when
        ProjectDTO projectDTO = converter.projectToProjectDTO(project);

        //then
        assertThat(projectDTO.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(projectDTO.getId()).isEqualTo(LONG_VALUE);
        assertThat(projectDTO.getProjectName()).isEqualTo(PROJECT_NAME);
        assertThat(projectDTO.getGitPath()).isEqualTo(GIT_PATH);
        assertThat(projectDTO.getProjectStatus()).isEqualTo(STATUS);
        assertThat(projectDTO.getFontSet().size()).isEqualTo(1);
        assertThat(projectDTO.getFontSet().contains(font1)).isTrue();
    }

    @Test
    public void convertProjectDTOToProject() {
        Font font1 = new Font("font1");
        fontSet.add(font1);

        //given
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(LONG_VALUE);
        projectDTO.setDescription(DESCRIPTION);
        projectDTO.setProjectName(PROJECT_NAME);
        projectDTO.setGitPath(GIT_PATH);
        projectDTO.setProjectStatus(STATUS);
        projectDTO.setFontSet(fontSet);
        //when
        Project project = converter.projectDTOToProject(projectDTO);

        //then
        assertThat(project.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(project.getId()).isEqualTo(LONG_VALUE);
        assertThat(project.getProjectName()).isEqualTo(PROJECT_NAME);
        assertThat(project.getGitPath()).isEqualTo(GIT_PATH);
        assertThat(project.getProjectStatus()).isEqualTo(STATUS);
        assertThat(project.getFontSet().size()).isEqualTo(1);
        assertThat(project.getFontSet().contains(font1)).isTrue();

    }
}