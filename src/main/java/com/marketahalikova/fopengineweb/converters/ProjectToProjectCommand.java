package com.marketahalikova.fopengineweb.converters;

import com.marketahalikova.fopengineweb.commands.ProjectCommand;
import com.marketahalikova.fopengineweb.model.Project;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ProjectToProjectCommand  implements Converter<Project, ProjectCommand> {
    @Nullable
    @Synchronized
    @Override
    public ProjectCommand convert(Project project) {
        if(project == null){
            return null;
        }
        final ProjectCommand projectCommand = new ProjectCommand();
        projectCommand.setFontSet(project.getFontSet());
        projectCommand.setProjectStatus(project.getProjectStatus());
        projectCommand.setDescription(project.getDescription());
        projectCommand.setGitPath(project.getGitPath());
        projectCommand.setProjectName(project.getProjectName());
        projectCommand.setId(project.getId());

//        if (projectCommand.getFontSet().isEmpty()){
//        }

        return projectCommand;
    }
}
