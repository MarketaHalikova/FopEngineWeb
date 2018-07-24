package com.marketahalikova.fopengineweb.model;

import com.marketahalikova.fopengineweb.enums.ProjectStatus;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String projectName;
    private String gitPath;
    private String description;
    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;
    @OneToMany (mappedBy = "project", cascade = CascadeType.ALL)
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

    public void addFont(Font font){
        fontSet.add(font);
        font.setProject(this);
    }

}
