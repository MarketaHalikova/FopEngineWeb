package com.marketahalikova.fopengineweb.controllers;

import com.marketahalikova.fopengineweb.commands.ProjectCommand;
import com.marketahalikova.fopengineweb.services.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("project", new ProjectCommand());
        log.debug("controller new Project called.");
        return "project/projectform";
    }

    @PostMapping
    @RequestMapping("project")
    public String saveOrUpdateProject(@ModelAttribute ProjectCommand projectCommand) {
        ProjectCommand savedProjectCommand = projectService.saveProjectCommand(projectCommand);
        log.debug("controller new Project called. New Project id = " + savedProjectCommand.getId());
        return "redirect:/project/" + savedProjectCommand.getId() + "/show";
    }
}
