package com.marketahalikova.fopengineweb.model;

import com.marketahalikova.fopengineweb.enums.ProjectStatus;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ProjectTest {

    public static final long ID = 4L;
    public static final String PROJECT_NAME = "name";
    public static final String GIT_PATH = "git path";
    public static final String DESCRIPTION = "description";
    public static final ProjectStatus PROJECT_STATUS = ProjectStatus.ARCHIVED;
    Project project;

    @Before
    public void setUp() {
        project = new Project();
    }

    @Test
    public void getId() {
        project.setId(ID);
        assertThat(project.getId(), is(ID));
    }

    @Test
    public void getProjectName() {
        project.setProjectName(PROJECT_NAME);
        assertThat(project.getProjectName(), is(PROJECT_NAME));
    }

    @Test
    public void getGitPath() {
        project.setGitPath(GIT_PATH);
        assertThat(project.getGitPath(), is(GIT_PATH));
    }

    @Test
    public void getDescription() {
        project.setDescription(DESCRIPTION);
        assertThat(project.getDescription(), is(DESCRIPTION));
    }

    @Test
    public void getProjectStatus() {
        project.setProjectStatus(PROJECT_STATUS);
        assertThat(project.getProjectStatus(), is(PROJECT_STATUS));

    }

    @Test
    public void getFontSet() {
        HashSet<Font> fontSet = new HashSet<>();
        Font font = null;
        fontSet.add(font);
        project.setFontSet(fontSet);
        assertThat(project.getFontSet(), is(fontSet));
    }
}