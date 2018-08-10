package com.marketahalikova.fopengineweb.commands;

import com.marketahalikova.fopengineweb.enums.ProjectStatus;
import com.marketahalikova.fopengineweb.model.Font;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@Data
public class ProjectDTO {
    private Long id;
    private String projectName;
    private String gitPath;
    private String description;
    private ProjectStatus projectStatus = ProjectStatus.OK;
    private Set<Font> fontSet = new HashSet<>();
}