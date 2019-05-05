package de.irs.fopengine.fopengineweb.model;

import de.irs.fopengine.fopengineweb.enums.FileType;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Job in project
 */
@Data
@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jobName;
    private String processName;

    @OneToOne(cascade = CascadeType.ALL)
    @Where(clause="FILE_TYPE = 'schema'")
    private ProjectFileMapper schema;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "file_to_source",
            joinColumns = @JoinColumn(name = "source_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id")
    )
    @Where(clause = "FILE_TYPE = 'imported_schemas'")
    private Set<ProjectFileMapper> schemas;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "file_to_source",
            joinColumns = @JoinColumn(name = "source_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id")
    )
    @Where(clause = "FILE_TYPE = 'graphics'")
    private Set<ProjectFileMapper> graphics;

    public Job() {
        schemas = new HashSet<>();
        graphics = new HashSet<>();
    }

    public Job(String jobName, String processName) {
        this();
        this.jobName = jobName;
        this.processName = processName;
    }

    /**
     * Setter for Xsd schema of data for this job. This method sets the type of ProjectFileMapper to schema.
     * @param schema mapper for font files
     */
    public void setSchema(ProjectFileMapper schema) {
        schema.setFileType(FileType.schema);
        this.schema = schema;
    }

    /**
     * Setter for subsidiary Xsd schemas of data for this job. This method sets the type
     * of ProjectFileMappers to imported_schemas.
     * The old set is replaced
     * @param schemas set of mappers for font files
     */
    public void setSchemas(Set<ProjectFileMapper> schemas) {
        schemas.forEach(projectFileMapper -> projectFileMapper.setFileType(FileType.imported_schemas));
        this.schemas = schemas;
    }

    /**
     * Setter for images for this job. This method sets the type
     * of ProjectFileMappers to graphics.
     * The old set is replaced
     * @param graphics set of mappers for images
     */
    public void setGraphics(Set<ProjectFileMapper> graphics) {
        graphics.forEach(projectFileMapper -> projectFileMapper.setFileType(FileType.graphics));
        this.graphics = graphics;
    }


}