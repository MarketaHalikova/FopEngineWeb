package com.marketahalikova.fopengineweb.model;

import com.marketahalikova.fopengineweb.enums.FileType;
import com.marketahalikova.fopengineweb.enums.ProjectStatus;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String gitPath;
    private String projectName = "";
    private String description;
    private LocalDateTime lastChange;

    @Enumerated(value = EnumType.STRING)
    private ProjectStatus projectStatus;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Set<Font> fontSet;

    @OneToOne(cascade = CascadeType.ALL)
    @Where(clause="FILE_TYPE = 'userconfig'")
    private ProjectFileMapper userConfig;

    @OneToOne(cascade = CascadeType.ALL)
    private MavenArtifact mavenArtifact;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<MavenArtifact> dependencies;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    public Set<Job> jobs;


    public Project() {
        jobs = new HashSet<>();
        fontSet = new HashSet<>();
        projectStatus = ProjectStatus.OK;
        dependencies = new HashSet<>();
    }

    public Project(String gitPath, String projectName) {
        this();
        this.gitPath = gitPath;
        this.projectName = projectName;
    }

    public void addFont(Font font) {
        fontSet.add(font);
        font.setProject(this);
    }

    public void setUserConfig(ProjectFileMapper projectFileMapper){
        projectFileMapper.setFileType(FileType.userconfig);
        this.userConfig = projectFileMapper;

    }

}
