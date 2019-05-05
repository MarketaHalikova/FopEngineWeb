package de.irs.fopengine.fopengineweb.commands;

import de.irs.fopengine.fopengineweb.enums.ProjectStatus;
import de.irs.fopengine.fopengineweb.model.Font;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Data Transfer object for Project
 */
@Getter
@Setter
@NoArgsConstructor
@Data
public class ProjectDTO {
    private Long id;
    private String projectName;
    private String gitUrl;
    private String description;
    private ProjectStatus projectStatus = ProjectStatus.OK;
    private Set<Font> fontSet = new HashSet<>();
}
