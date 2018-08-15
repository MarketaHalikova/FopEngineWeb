package com.marketahalikova.fopengineweb.model;

import com.marketahalikova.fopengineweb.enums.FileType;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class JobTransformation extends Job {

    private String transformationName;

    @OneToOne(cascade = CascadeType.ALL)
    @Where(clause = "FILE_TYPE = 'xsl'")
    private ProjectFileMapper mainXsl;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "file_to_source",
            joinColumns = @JoinColumn(name = "source_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id")
    )
    @Where(clause = "FILE_TYPE = 'imported_xsls'")
    private Set<ProjectFileMapper> xsls;

    public JobTransformation() {
        xsls = new HashSet<>();
    }

    public JobTransformation(String jobName, String processName, String transformationName) {
        super(jobName, processName);
        xsls = new HashSet<>();
        this.transformationName = transformationName;
    }

    public void setMainXsl(ProjectFileMapper mainXsl) {
        mainXsl.setFileType(FileType.xsl);
        this.mainXsl = mainXsl;
    }

    public void setXsls(Set<ProjectFileMapper> xsls) {
        xsls.forEach(projectFileMapper -> projectFileMapper.setFileType(FileType.imported_xsls));
        this.xsls = xsls;
    }

}