package com.marketahalikova.fopengineweb.controllers;

import com.marketahalikova.fopengineweb.commands.ProjectDTO;
import com.marketahalikova.fopengineweb.model.Project;
import com.marketahalikova.fopengineweb.services.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping({"", "/", "/project"})
    public String getIndexPage(Model model) {

        model.addAttribute("projectList", projectService.getProjects());

        return "projectlist";
    }

    @GetMapping("/project/{id}/show")
    public String showById(@PathVariable String id, Model model){
        model.addAttribute("project", projectService.findById(new Long(id)));
        return "project/show";
    }

    @GetMapping({"/project/new"})
    public String newProject(Model model) {
        model.addAttribute("project", new ProjectDTO());
        log.debug("controller new Project called.");
        return "project/projectform";
    }

    @GetMapping("project/{id}/update")
    public String updateProject(@PathVariable String id, Model model){
        model.addAttribute("project", projectService.findCommandById(Long.valueOf(id)));
        log.debug("controller update Project called.");
        return  "project/projectform";
    }

    @PostMapping("project")
    public String saveOrUpdateProject(@ModelAttribute ProjectDTO projectDTO) {
        ProjectDTO savedProjectDTO;
        if(projectDTO.getId()==null) {
            savedProjectDTO = projectService.saveProjectCommand(projectDTO);
            log.debug("controller new Project called. New Project id = " + savedProjectDTO.getId());
            return "redirect:/project/" + savedProjectDTO.getId() + "/show";
        } else {
            Project tmp = projectService.findById(projectDTO.getId());
            tmp.setDescription(projectDTO.getDescription());
            Project savedProject = projectService.saveProject(tmp);
            log.debug("controller update Project called. Updated Project id = " + savedProject.getId());
            return "redirect:/project/" + savedProject.getId() + "/show";
        }
    }

    @GetMapping("project/{id}/delete")
    public String deleteById(@PathVariable String id){

        log.debug("Deleting project with id: " + id);

        projectService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }
}
