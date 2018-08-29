package com.marketahalikova.fopengineweb.services;

import com.marketahalikova.fopengineweb.commands.ProjectDTO;
import com.marketahalikova.fopengineweb.exceptions.FopEngineException;
import com.marketahalikova.fopengineweb.exceptions.GitException;
import com.marketahalikova.fopengineweb.exceptions.XmlException;
import com.marketahalikova.fopengineweb.model.Project;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
public class ProjectServiceImpl implements ProjectService{


    @Override
    public Set<Project> getProjects() throws FopEngineException, GitException {
        return null;
    }

    @Override
    public Project getProjectById(Long id) throws FopEngineException, GitException {
        return null;
    }

    @Override
    public ProjectDTO getProjectCommandById(Long id) throws FopEngineException, GitException {
        return null;
    }

    @Override
    public void deleteProjectById(Long id) throws FopEngineException, GitException {

    }

    @Override
    public Project updateProject(Project project) throws FopEngineException, GitException, XmlException {
        return null;
    }

    @Override
    public Project registerNewProject(ProjectDTO project) throws FopEngineException, XmlException {
        return null;
    }


//    public ProjectServiceImpl(ProjectRepository projectRepository) {
//        this.projectRepository = projectRepository;
//
//    }
//
//    @Override
//    public Set<Project> getProjects() {
//        Set<Project> projects = new HashSet<>();
//        projectRepository.findAll().forEach(projects::add);
//        return projects;
//    }
//
//    @Override
//    public Project findById(Long l) {
//
//        Optional<Project> projectOptional = projectRepository.findById(l);
//
//        if (!projectOptional.isPresent()) {
//            throw new NotFoundException("Recipe Not Found");
//        }
//
//        return projectOptional.get();
//    }
//
//    @Transactional
//    @Override
//    public ProjectDTO saveProjectCommand(ProjectDTO projectDTO) {
//        Project detachedProject = ProjectMapper.INSTANCE.projectDTOToProject(projectDTO);
//
//        Project savedProject = projectRepository.save(detachedProject);
//        log.debug("Saved ProjectId:" + savedProject.getId());
//        return ProjectMapper.INSTANCE.projectToProjectDTO(savedProject);
//    }
//
//    @Transactional
//    @Override
//    public ProjectDTO findCommandById(Long l) {
//        return ProjectMapper.INSTANCE.projectToProjectDTO(findById(l));
//    }
//
//
//    @Override
//    public Project saveProject(Project detachedProject) {
//        Project savedProject = projectRepository.save(detachedProject);
//        log.debug("Saved project id: " + savedProject.getId());
//        return savedProject;
//    }
//
//    @Override
//    public void deleteById(Long idToDelete) {
//        projectRepository.deleteById(idToDelete);
//    }
}
