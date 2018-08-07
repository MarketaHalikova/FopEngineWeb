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

public class ProjectCommandToProjectTest {

    public static final String DESCRIPTION = "description";
    public static final Long LONG_VALUE = new Long(1L);
    public static final String PROJECT_NAME = "description";
    public static final String GIT_PATH = "description";
    public static final ProjectStatus STATUS = ProjectStatus.ARCHIVED;
    public static final Set<Font> fontSet = new HashSet<>();

    ProjectCommandToProject converter;

    @Before
    public void setUp() throws Exception {
        converter = new ProjectCommandToProject();
    }

    @Test
    public void testNullObjectConvert() throws Exception {
        assertThat(converter.convert(null)).isNull();
    }

    @Test
    public void testEmptyObj() throws Exception {
        assertThat(converter.convert(new ProjectCommand())).isNotNull();
    }

    @Test
    public void convert() {
        Font font1 = new Font("font1");
        fontSet.add(font1);

        //given
        ProjectCommand projectCommand = new ProjectCommand();
        projectCommand.setId(LONG_VALUE);
        projectCommand.setDescription(DESCRIPTION);
        projectCommand.setProjectName(PROJECT_NAME);
        projectCommand.setGitPath(GIT_PATH);
        projectCommand.setProjectStatus(STATUS);
        projectCommand.setFontSet(fontSet);
        //when
        Project project = converter.convert(projectCommand);

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