package com.marketahalikova.fopengineweb.bootstrap;

import com.marketahalikova.fopengineweb.enums.FileType;
import com.marketahalikova.fopengineweb.enums.FontStyle;
import com.marketahalikova.fopengineweb.model.*;
import com.marketahalikova.fopengineweb.repositories.FontTripletRepository;
import com.marketahalikova.fopengineweb.repositories.ProjectRepository;
import com.marketahalikova.fopengineweb.repositories.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final FontTripletRepository fontTripletRepository;

    public ProjectBootstrap(UserRepository userRepository, ProjectRepository projectRepository, FontTripletRepository fontTripletRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.fontTripletRepository = fontTripletRepository;
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
        project1.setLastChange(LocalDateTime.parse("1993-05-04T09:09:09.09"));
        projects.add(project1);

        Project project2 = new Project("Project2", "git2");
        project2.setDescription("description2");

        Font font2 = new Font("Font2");
        Font font3 = new Font("Font3");
        project2.addFont(font2);;
        project2.addFont(font3);
        project2.setLastChange(LocalDateTime.parse("1993-05-04T09:09:09.09"));
        projects.add(project2);

        addFonts(projects, project1, project2);
        addMavenArtifacts(project1, project2);
        addJobs(project1, project2);
        addUserConfig(project1, project2);
        return projects;
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

    private void addFonts(List<Project> projects, Project project1, Project project2) {
        Font font1 = new Font("font1");
        project1.addFont(font1);
        Font font2 = new Font("font2");
        Font font3 = new Font("font3");
        project2.addFont(font2);
        project2.addFont(font3);
        projects.add(project2);

        FontTriplet triplet1 = new FontTriplet(FontStyle.Bold, "inddFont1", "inddStyle1");
        FontTriplet triplet2 = new FontTriplet(FontStyle.Bold, "inddFont2", "inddStyle2");
        FontTriplet triplet3 = new FontTriplet(FontStyle.Bold, "inddFont3", "inddStyle3");
        FontTriplet triplet4 = new FontTriplet(FontStyle.Bold, "inddFont4", "inddStyle4");
        ProjectFileMapper metrics1 = new ProjectFileMapper("metrics1", "source_metrics1", "target_metrics1");
        ProjectFileMapper metrics2 = new ProjectFileMapper("metrics2", "source_metrics2", "target_metrics2");
        ProjectFileMapper metrics3 = new ProjectFileMapper("metrics3", "source_metrics3", "target_metrics3");
        ProjectFileMapper metrics4 = new ProjectFileMapper("metrics4", "source_metrics4", "target_metrics4");
        metrics1.setFileType(FileType.metrics_file);
        metrics2.setFileType(FileType.metrics_file);
        metrics3.setFileType(FileType.metrics_file);
        metrics4.setFileType(FileType.metrics_file);
        triplet1.setMetricsFile(metrics1);
        triplet2.setMetricsFile(metrics2);
        triplet3.setMetricsFile(metrics3);
        triplet4.setMetricsFile(metrics4);
        fontTripletRepository.save(triplet1);
        fontTripletRepository.save(triplet2);
        fontTripletRepository.save(triplet3);
        fontTripletRepository.save(triplet4);

        font1.fontTriplets.add(triplet1);
        font1.fontTriplets.add(triplet2);
        font1.fontTriplets.add(triplet3);

        font2.fontTriplets.add(triplet4);
        font2.fontTriplets.add(triplet1);

    }

    private void addUserConfig(Project project1, Project project2) {
        ProjectFileMapper userConfig1 = new ProjectFileMapper("userconfig1.xml", "source1", "target1");
        userConfig1.setFileType(FileType.userconfig);
        ProjectFileMapper userConfig2 = new ProjectFileMapper("userconfig2.xml", "source2", "target2");
        userConfig2.setFileType(FileType.userconfig);
        project1.setUserConfig(userConfig1);
        project2.setUserConfig(userConfig2);
    }

    private void addJobs(Project project1, Project project2) {
        JobTemplate job1 = new JobTemplate("job1", "process1", "template1");
        JobTransformation job2 = new JobTransformation("job2", "process2", "transformation1");
        JobTemplate job3 = new JobTemplate("job3", "process3", "template1");
        JobTransformation job4 = new JobTransformation("job4", "process4", "transformation1");
        project1.getJobs().add(job1);
        project1.getJobs().add(job2);
        project2.getJobs().add(job3);
        project2.getJobs().add(job4);
        addSchemas(job1, job2, job3, job4);
        addXsl(job2, job4);
        addTemplate(job1, job3);

        ProjectFileMapper graphics1 = new ProjectFileMapper("graphics1", "source_graphics1", "target_graphics1");
        ProjectFileMapper graphics2 = new ProjectFileMapper("graphics2", "source_graphics2", "target_graphics2");
        ProjectFileMapper graphics3 = new ProjectFileMapper("graphics3", "source_graphics3", "target_graphics3");
        ProjectFileMapper graphics4 = new ProjectFileMapper("graphics4", "source_graphics4", "target_graphics4");
        graphics1.setFileType(FileType.graphics);
        graphics2.setFileType(FileType.graphics);
        graphics3.setFileType(FileType.graphics);
        graphics4.setFileType(FileType.graphics);
        job1.getGraphics().add(graphics1);
        job1.getGraphics().add(graphics2);
        job2.getGraphics().add(graphics3);
        job3.getGraphics().add(graphics4);
    }

    private void addSchemas(Job job1, Job job2, Job job3, Job job4) {
        ProjectFileMapper mainSchema1 = new ProjectFileMapper("mainSchema1.xsd", "source_mainSchema1", "target_mainSchema1");
        ProjectFileMapper mainSchema2 = new ProjectFileMapper("mainSchema2.xsd", "source_mainSchema2", "target_mainSchema2");
        ProjectFileMapper mainSchema3 = new ProjectFileMapper("mainSchema3.xsd", "source_mainSchema3", "target_mainSchema3");
        ProjectFileMapper mainSchema4 = new ProjectFileMapper("mainSchema4.xsd", "source_mainSchema4", "target_mainSchema4");
        mainSchema1.setFileType(FileType.schema);
        mainSchema2.setFileType(FileType.schema);
        mainSchema3.setFileType(FileType.schema);
        mainSchema4.setFileType(FileType.schema);
        job1.setSchema(mainSchema1);
        job2.setSchema(mainSchema2);
        job3.setSchema(mainSchema3);
        job4.setSchema(mainSchema4);
        ProjectFileMapper schema11 = new ProjectFileMapper("schema11.xsd", "source_schema11", "target_schema11");
        schema11.setFileType(FileType.imported_schemas);
        ProjectFileMapper schema12 = new ProjectFileMapper("schema12.xsd", "source_schema12", "target_schema12");
        schema12.setFileType(FileType.imported_schemas);
        ProjectFileMapper schema21 = new ProjectFileMapper("schema21.xsd", "source_schema21", "target_schema21");
        schema21.setFileType(FileType.imported_schemas);
        ProjectFileMapper schema22 = new ProjectFileMapper("schema22.xsd", "source_schema22", "target_schema22");
        schema22.setFileType(FileType.imported_schemas);
        ProjectFileMapper schema31 = new ProjectFileMapper("schema31.xsd", "source_schema31", "target_schema31");
        schema31.setFileType(FileType.imported_schemas);
        job1.getSchemas().add(schema11);
        job1.getSchemas().add(schema12);
        job2.getSchemas().add(schema21);
        job2.getSchemas().add(schema22);
        job3.getSchemas().add(schema31);
    }

    private void addXsl(JobTransformation job1, JobTransformation job3) {
        ProjectFileMapper main_xsl1 = new ProjectFileMapper("main_xsl1.xsd", "source_main_xsl1", "target_main_xsl1");
        ProjectFileMapper main_xsl3 = new ProjectFileMapper("main_xsl3.xsd", "source_main_xsl3", "target_main_xsl3");
        main_xsl1.setFileType(FileType.xsl);
        main_xsl3.setFileType(FileType.xsl);
        job1.setMainXsl(main_xsl1);
        job3.setMainXsl(main_xsl3);

        ProjectFileMapper xsl11 = new ProjectFileMapper("xsl11.xsd", "source_xsl11", "target_xsl11");
        xsl11.setFileType(FileType.imported_xsls);
        ProjectFileMapper xsl12 = new ProjectFileMapper("xsl12.xsd", "source_xsl12", "target_xsl12");
        xsl12.setFileType(FileType.imported_xsls);
        job1.getXsls().add(xsl11);
        job1.getXsls().add(xsl12);
    }

    private void addTemplate(JobTemplate job1, JobTemplate job2) {
        ProjectFileMapper main_template1 = new ProjectFileMapper("main_template1.xsd", "source_main_template1", "target_main_template1");
        ProjectFileMapper main_template2 = new ProjectFileMapper("main_template2.xsd", "source_main_template2", "target_main_template2");
        main_template1.setFileType(FileType.template);
        main_template2.setFileType(FileType.template);
        job1.setMainTemplate(main_template1);
        job2.setMainTemplate(main_template2);

        ProjectFileMapper xsl11 = new ProjectFileMapper("xsl11.xsd", "source_xsl11", "target_xsl11");
        xsl11.setFileType(FileType.imported_templates);
        ProjectFileMapper xsl12 = new ProjectFileMapper("xsl12.xsd", "source_xsl12", "target_xsl12");
        xsl12.setFileType(FileType.imported_templates);

        job1.getTemplates().add(xsl11);
        job1.getTemplates().add(xsl12);
    }
}
