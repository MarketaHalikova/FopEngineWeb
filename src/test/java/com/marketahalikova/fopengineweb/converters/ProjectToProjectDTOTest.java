package com.marketahalikova.fopengineweb.converters;

import com.marketahalikova.fopengineweb.commands.ProjectDTO;
import com.marketahalikova.fopengineweb.enums.ProjectStatus;
import com.marketahalikova.fopengineweb.mappers.ProjectMapper;
import com.marketahalikova.fopengineweb.model.Font;
import com.marketahalikova.fopengineweb.model.Project;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectToProjectDTOTest {

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
        assertThat(converter.projectToProjectCommand(null)).isNull();
    }

    @Test
    public void testEmptyObj() throws Exception {
        assertThat(converter.projectToProjectCommand(new Project())).isNotNull();
    }

    @Test
    public void convert() {
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
        ProjectDTO projectDTO = converter.projectToProjectCommand(project);

        //then
        assertThat(projectDTO.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(projectDTO.getId()).isEqualTo(LONG_VALUE);
        assertThat(projectDTO.getProjectName()).isEqualTo(PROJECT_NAME);
        assertThat(projectDTO.getGitPath()).isEqualTo(GIT_PATH);
        assertThat(projectDTO.getProjectStatus()).isEqualTo(STATUS);
        assertThat(projectDTO.getFontSet().size()).isEqualTo(1);
        assertThat(projectDTO.getFontSet().contains(font1)).isTrue();
    }
}