package com.marketahalikova.fopengineweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProjectListController {

    @RequestMapping({"","/","/projectlist"})
    public String getProjectListPage(){
        return "ProjectList";
    }
}
