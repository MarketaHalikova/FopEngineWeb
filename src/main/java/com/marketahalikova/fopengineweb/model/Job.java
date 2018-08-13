package com.marketahalikova.fopengineweb.model;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


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
}