package com.marketahalikova.fopengineweb.bootstrap;

import com.marketahalikova.fopengineweb.model.Font;
import com.marketahalikova.fopengineweb.model.Project;
import com.marketahalikova.fopengineweb.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectBootstrap(ProjectRepository projectRepository) {
        System.out.println("ProjectBootstrap constructor");
        this.projectRepository = projectRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("Bootstrap project data");
        projectRepository.saveAll(getProjects());
    }

    private List<Project> getProjects(){
        List<Project> projects = new ArrayList<>();
        Project project1 = new Project("Project1", "git1");
        project1.setDescription("description1");

        Font font1 = new Font("Font1");
        project1.addFont(font1);
        projects.add(project1);

        Project project2 = new Project("Project2", "git2");
        project2.setDescription("description2");

        Font font2 = new Font("Font2");
        Font font3 = new Font("Font3");
        project2.addFont(font2);;
        project2.addFont(font3);
        projects.add(project2);

        return  projects;
    }
}
