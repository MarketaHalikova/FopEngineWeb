package com.marketahalikova.fopengineweb.bootstrap;

import com.marketahalikova.fopengineweb.model.Font;
import com.marketahalikova.fopengineweb.model.MavenArtifact;
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

        addMavenArtifacts(project1, project2);

        return  projects;
    }

    private void addMavenArtifacts(Project project1, Project project2) {
        MavenArtifact mavenArtifactProject1 = new MavenArtifact( "groupProject1", "artifactProject1","versionProject1");
        project1.setMavenArtifact(mavenArtifactProject1);

        MavenArtifact dependency1_Project1 = new MavenArtifact("dependency1_groupProject1", "dependency1_artifactProject1", "dependency1_versionProject1");
        //    dependency1_Project1 = mavenArtifactRepository.save(dependency1_Project1);
        MavenArtifact dependency2_Project1 = new MavenArtifact( "dependency2_groupProject1", "dependency2_artifactProject1","dependency2_versionProject1");
        project1.getDependencies().add(dependency1_Project1);
        //   dependency2_Project1 = mavenArtifactRepository.save(dependency2_Project1);
        project1.getDependencies().add(dependency2_Project1);

        MavenArtifact mavenArtifactProject2 = new MavenArtifact("groupProject2","artifactProject2",  "versionProject2");
        project2.setMavenArtifact(mavenArtifactProject2);
        MavenArtifact dependency1_Project2 = new MavenArtifact( "dependency1_groupProject2","dependency1_artifactProject2", "dependency1_versionProject2");
        //       dependency1_Project2 = mavenArtifactRepository.save(dependency1_Project2);
        project2.getDependencies().add(dependency1_Project2);
    }
}
