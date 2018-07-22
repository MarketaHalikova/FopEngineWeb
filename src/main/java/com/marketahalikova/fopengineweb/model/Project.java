package com.marketahalikova.fopengineweb.model;

import com.marketahalikova.fopengineweb.enums.ProjectStatus;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Project {

    private Long id;
    private String projectName;
    private String gitPath;
    private String description;
    private ProjectStatus projectStatus;
    private Set<Font> fontSet;


    public Project() {
        fontSet = new HashSet<>();
        this.projectStatus = ProjectStatus.OK;
    }

    public Project(String projectName, String gitPath) {
        this();
        this.projectName = projectName;
        this.gitPath = gitPath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getGitPath() {
        return gitPath;
    }

    public void setGitPath(String gitPath) {
        this.gitPath = gitPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Set<Font> getFontSet() {
        return fontSet;
    }

    public void setFontSet(Set<Font> fontSet) {
        this.fontSet = fontSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(id, project.id) &&
                Objects.equals(projectName, project.projectName) &&
                Objects.equals(gitPath, project.gitPath) &&
                Objects.equals(description, project.description) &&
                projectStatus == project.projectStatus &&
                Objects.equals(fontSet, project.fontSet);
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", projectName='" + projectName + '\'' +
                ", gitPath='" + gitPath + '\'' +
                ", description='" + description + '\'' +
                ", projectStatus=" + projectStatus +
                ", fontSet=" + fontSet +
                '}';
    }
}
