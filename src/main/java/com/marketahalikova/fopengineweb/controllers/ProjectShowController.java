package com.marketahalikova.fopengineweb.controllers;

import com.marketahalikova.fopengineweb.services.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProjectShowController {

    private final ProjectService projectService;

    public ProjectShowController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @RequestMapping("/project/show/{id}")
    public String showById(@PathVariable String id, Model model){
        model.addAttribute("project", projectService.findById(new Long(id)));
        return "project/show";
    }

}
