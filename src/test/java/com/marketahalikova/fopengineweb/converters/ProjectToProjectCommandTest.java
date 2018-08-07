package com.marketahalikova.fopengineweb.converters;

import com.marketahalikova.fopengineweb.commands.ProjectCommand;
import com.marketahalikova.fopengineweb.enums.ProjectStatus;
import com.marketahalikova.fopengineweb.model.Font;
import com.marketahalikova.fopengineweb.model.Project;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectToProjectCommandTest {

    public static final String DESCRIPTION = "description";
    public static final Long LONG_VALUE = new Long(1L);
    public static final String PROJECT_NAME = "description";
    public static final String GIT_PATH = "description";
    public static final ProjectStatus STATUS = ProjectStatus.ARCHIVED;
    public static final Set<Font> fontSet = new HashSet<>();

    ProjectToProjectCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new ProjectToProjectCommand();
    }

    @Test
    public void testNullObjectConvert() throws Exception {
        assertThat(converter.convert(null)).isNull();
    }

    @Test
    public void testEmptyObj() throws Exception {
        assertThat(converter.convert(new Project())).isNotNull();
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
        ProjectCommand projectCommand = converter.convert(project);

        //then
        assertThat(projectCommand.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(projectCommand.getId()).isEqualTo(LONG_VALUE);
        assertThat(projectCommand.getProjectName()).isEqualTo(PROJECT_NAME);
        assertThat(projectCommand.getGitPath()).isEqualTo(GIT_PATH);
        assertThat(projectCommand.getProjectStatus()).isEqualTo(STATUS);
        assertThat(projectCommand.getFontSet().size()).isEqualTo(1);
        assertThat(projectCommand.getFontSet().contains(font1)).isTrue();
    }
}