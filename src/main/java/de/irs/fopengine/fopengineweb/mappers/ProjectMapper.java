package de.irs.fopengine.fopengineweb.mappers;


import de.irs.fopengine.fopengineweb.commands.ProjectDTO;
import de.irs.fopengine.fopengineweb.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProjectMapper {

    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    ProjectDTO projectToProjectDTO(Project project);
    Project projectDTOToProject(ProjectDTO project);
}
