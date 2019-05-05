package de.irs.fopengine.fopengineweb.controllers;

import de.irs.fopengine.fopengineweb.commands.ProjectDTO;
import de.irs.fopengine.fopengineweb.exceptions.FopEngineException;
import de.irs.fopengine.fopengineweb.exceptions.GitException;
import de.irs.fopengine.fopengineweb.exceptions.XmlException;
import de.irs.fopengine.fopengineweb.model.Project;
import de.irs.fopengine.fopengineweb.services.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Set;

/**
 * Controller to handle project requests
 */
@Slf4j
@Controller
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * Handle project list as the initial screen.
     * TODO Should be changed for MS Exchange or simple user management
     * @param model
     * @return template for list of projects
     * @throws GitException
     * @throws FopEngineException
     */
    @GetMapping({"", "/", "/project"})
    public String getIndexPage(Model model) throws GitException, FopEngineException {

        Set<Project> projects;
        try {
            projects = projectService.getProjects();
        } catch (FopEngineException e) {
            e.printStackTrace();
            // TODO handle exception
            throw new RuntimeException(e);
        } catch (GitException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
            // TODO handle exception
        }
        log.debug("controller getProjects called");
        model.addAttribute("projectList", projects);

        return "projectlist";
    }

    /**
     * Show project
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/project/{id}/show")
    public String showById(@PathVariable String id, Model model) {
        Project project = null;
        try {
            project = projectService.getProjectById(new Long(id));
        } catch (FopEngineException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
            // TODO handle exception
        } catch (GitException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
            // TODO handle exception
        }
        log.debug("controller getProjectById called. Id=" + id);
        model.addAttribute("project", project);
        return "project/show";
    }

    /**
     * Prepare data for new project. Template projectform is returned
     * @param model model
     * @return  Template projectform
     */
    @GetMapping({"/project/new"})
    public String newProject(Model model) {
        model.addAttribute("project", new ProjectDTO());
        log.debug("controller new Project called.");
        return "project/projectform";
    }

    /**
     * Prepare data for project updating. Template projectform is returned
     * @param id id
     * @param model model
     * @return  Template projectform
     */
    @GetMapping("project/{id}/update")
    public String updateProject(@PathVariable String id, Model model){
        ProjectDTO projectCommand = null;
        try {
            projectCommand = projectService.getProjectCommandById(new Long(id));
        } catch (FopEngineException e) {
            // TODO handle exception
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (GitException e) {
            // TODO handle exception
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        model.addAttribute("project", projectCommand);
        log.debug("controller edit Project called. Id=" + id);
        return "project/projectform";
    }

    /**
     * Save an edited project or create a new one
     * @param projectCommand ProjectDTO
     * @return template to show edited/created project
     */
    @PostMapping("project")
    public String saveOrUpdateProject(@ModelAttribute ProjectDTO projectCommand) {
        try {
            if (projectCommand.getId() == null) {
                Project newProject = null;
                System.out.println(projectCommand.getDescription());
                newProject = projectService.registerNewProject(projectCommand);

                log.debug("controller new Project called. New Project id = " + newProject.getId());
                return "redirect:/project/" + newProject.getId() + "/show";
            } else {
                Project tmp = projectService.getProjectById(projectCommand.getId());
                tmp.setDescription(projectCommand.getDescription());

                Project savedProject = projectService.updateProject(tmp);
                log.debug("controller update Project called. Updated Project id = " + savedProject.getId());
                return "redirect:/project/" + savedProject.getId() + "/show";
            }
        } catch (FopEngineException e) {
            // TODO handle exception
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (XmlException e) {
            // TODO handle exception
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (GitException e) {
            // TODO handle exception
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete project
     * @param id project id
     * @return screen list of project
     */
    @GetMapping("project/{id}/delete")
    public String deleteById(@PathVariable String id)  {

        log.debug("Deleting project id:" + id);
        try {
            projectService.deleteProjectById(Long.valueOf(id));
        } catch (FopEngineException e) {
            // TODO handle exception
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (GitException e) {
            // TODO handle exception
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return "redirect:/project";
    }
}
