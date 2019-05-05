package de.irs.fopengine.fopengineweb.model;

import de.irs.fopengine.fopengineweb.enums.FileType;
import de.irs.fopengine.fopengineweb.enums.ProjectStatus;
import de.irs.fopengine.fopengineweb.utils.PathConverter;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * FopEngine project
 */
@Data
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Git web URI
     */
    private String gitUrl;
    private String projectName = "";
    private String description;
    private LocalDateTime lastChange;

    @Convert(converter = PathConverter.class)
    private Path projectDirectory;

    @Enumerated(value = EnumType.STRING)
    private ProjectStatus projectStatus;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    @OrderBy
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
        fontSet = new LinkedHashSet<>();
        projectStatus = ProjectStatus.OK;
        dependencies = new HashSet<>();
    }

    public Project(String gitUrl, String projectName) {
        this();
        this.gitUrl = gitUrl;
        this.projectName = projectName;
    }

    /**
     * Add font and create a link from font to project
     * @param font font
     */
    public void addFont(Font font) {
        fontSet.add(font);
        font.setProject(this);
    }

    /**
     * Set userconfig and set the type of ProjectFileMapper to userconfig
     * @param projectFileMapper
     */
    public void setUserConfig(ProjectFileMapper projectFileMapper){
        projectFileMapper.setFileType(FileType.userconfig);
        this.userConfig = projectFileMapper;

    }

}
