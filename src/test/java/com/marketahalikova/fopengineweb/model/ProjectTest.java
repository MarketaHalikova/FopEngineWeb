package com.marketahalikova.fopengineweb.model;

import com.marketahalikova.fopengineweb.enums.ProjectStatus;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ProjectTest {

    Project project;

    @Before
    public void setUp() {
        project = new Project();
    }

    @Test
    public void getId() {
        Long id = 4L;
        project.setId(id);
        assertThat(project.getId(), is(id));
    }

    @Test
    public void getProjectName() {
        String projectName = "name";
        project.setProjectName(projectName);
        assertThat(project.getProjectName(), is(projectName));
    }

    @Test
    public void getGitPath() {
        String path = "git path";
        project.setGitPath(path);
        assertThat(project.getGitPath(), is(path));
    }

    @Test
    public void getDescription() {
        String description = "description";
        project.setDescription(description);
        assertThat(project.getDescription(), is(description));
    }

    @Test
    public void getProjectStatus() {
        ProjectStatus projectStatus = ProjectStatus.ARCHIVED;
        project.setProjectStatus(projectStatus);
        assertThat(project.getProjectStatus(), is(projectStatus));

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