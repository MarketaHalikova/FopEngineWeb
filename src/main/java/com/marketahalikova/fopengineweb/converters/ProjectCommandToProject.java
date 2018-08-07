package com.marketahalikova.fopengineweb.converters;

import com.marketahalikova.fopengineweb.commands.ProjectCommand;
import com.marketahalikova.fopengineweb.model.Project;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ProjectCommandToProject implements Converter<ProjectCommand, Project> {

    @Nullable
    @Synchronized
    @Override
    public Project convert(ProjectCommand projectCommand) {
        if(projectCommand == null){
            return null;
        }
        final Project project = new Project();
        project.setFontSet(projectCommand.getFontSet());
        project.setProjectStatus(projectCommand.getProjectStatus());
        project.setDescription(projectCommand.getDescription());
        project.setGitPath(projectCommand.getGitPath());
        project.setProjectName(projectCommand.getProjectName());
        project.setId(projectCommand.getId());

//        if (projectCommand.getFontSet().isEmpty()){
//        }

        return project;
    }
}
