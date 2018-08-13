package com.marketahalikova.fopengineweb.mappers;


import com.marketahalikova.fopengineweb.commands.ProjectDTO;
import com.marketahalikova.fopengineweb.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProjectMapper {

    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    ProjectDTO projectToProjectDTO(Project project);
    Project projectDTOToProject(ProjectDTO project);
}
