package com.marketahalikova.fopengineweb.controllers;

import com.marketahalikova.fopengineweb.services.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProjectListController {

    private final ProjectService projectService;

    public ProjectListController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @RequestMapping({"", "/", "/project"})
    public String getIndexPage(Model model) {

        model.addAttribute("projectList", projectService.getProjects());

        return "projectlist";
    }
}
