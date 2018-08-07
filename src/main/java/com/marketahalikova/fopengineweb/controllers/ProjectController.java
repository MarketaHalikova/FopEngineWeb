package com.marketahalikova.fopengineweb.controllers;

import com.marketahalikova.fopengineweb.services.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
}
