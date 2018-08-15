package com.marketahalikova.fopengineweb.model;

import com.marketahalikova.fopengineweb.enums.FileType;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class JobTemplate extends Job {

    private String templateName;

    @OneToOne(cascade = CascadeType.ALL)
    @Where(clause = "FILE_TYPE = 'template'")
    private ProjectFileMapper mainTemplate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "file_to_source",
            joinColumns = @JoinColumn(name = "source_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id")
    )
    @Where(clause = "FILE_TYPE = 'imported_templates'")
    private Set<ProjectFileMapper> templates;

    public JobTemplate() {
        templates = new HashSet<>();
    }

    public JobTemplate(String jobName, String processName, String templateName) {
        super(jobName, processName);
        templates = new HashSet<>();
        this.templateName = templateName;
    }

    public void setMainTemplate(ProjectFileMapper mainTemplate) {
        mainTemplate.setFileType(FileType.template);
        this.mainTemplate = mainTemplate;
    }
    public void setTemplates(Set<ProjectFileMapper> templates) {
        templates.forEach(projectFileMapper -> projectFileMapper.setFileType(FileType.imported_templates));
        this.templates = templates;
    }
}