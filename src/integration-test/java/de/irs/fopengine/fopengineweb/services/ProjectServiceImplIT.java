package de.irs.fopengine.fopengineweb.services;

import de.irs.fopengine.fopengineweb.commands.ProjectDTO;
import de.irs.fopengine.fopengineweb.enums.ProjectStatus;
import de.irs.fopengine.fopengineweb.exceptions.FopEngineException;
import de.irs.fopengine.fopengineweb.exceptions.XmlException;
import de.irs.fopengine.fopengineweb.repositories.ProjectRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectServiceImplIT {

    public static final String GIT_LINK = "https://github.com/MarketaTest/fopengine-test-repository_2.git";
    public static final ProjectStatus NEW_PROJECT_STATUS = ProjectStatus.ARCHIVED;
    public static final String NEW_PROJECT_NAME = "new name";

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectService projectService;

    @Transactional
    @Test
    public void registerNewProject() throws FopEngineException, XmlException {
        ProjectDTO dto = new ProjectDTO();
        dto.setGitUrl(GIT_LINK);
        projectService.registerNewProject(dto);

//        // given
//        Iterable<Project> projects = projectRepository.findAll();
//        Project project = projects.iterator().next();
//        ProjectDTO projectDTO = ProjectMapper.INSTANCE.projectToProjectDTO(project);
//        //when
//        projectDTO.setProjectStatus(NEW_PROJECT_STATUS);
//        projectDTO.setProjectName(NEW_PROJECT_NAME);
//        //then
//        Project savedProject = projectService.registerNewProject(projectDTO);
//        assertThat(savedProject.getProjectName()).isEqualTo(NEW_PROJECT_NAME);
//        assertThat(savedProject.getProjectStatus()).isEqualTo(NEW_PROJECT_STATUS);
//        assertThat(savedProject.getFontSet().size()).isEqualTo(project.getFontSet().size());
    }

}