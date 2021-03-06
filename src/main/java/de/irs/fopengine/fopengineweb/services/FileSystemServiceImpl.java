package de.irs.fopengine.fopengineweb.services;

import de.irs.fopengine.fopengineweb.exceptions.FopEngineException;
import de.irs.fopengine.fopengineweb.model.Font;
import de.irs.fopengine.fopengineweb.model.FontTriplet;
import de.irs.fopengine.fopengineweb.model.Project;
import de.irs.fopengine.fopengineweb.model.ProjectFileMapper;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Service
public class FileSystemServiceImpl implements FileSystemService {

    public static final String PROJECT_WORKING_DIRECTORY = "work";
    @Value("${fopengine.git.working-directory}")
    private String workingDirectory = "";


    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteProjectDirectory(Project project) throws FopEngineException {
        Path projectDirectory = Optional.ofNullable(project.getProjectDirectory()).orElseThrow(() -> new FopEngineException(String.format("Project directory for project %s is not set!", project.getProjectName())));
        if (Files.exists(projectDirectory)) {
            try {
                FileUtils.deleteDirectory(projectDirectory.toFile());
            } catch (IOException e) {
                throw new FopEngineException(String.format("Can't delete project directory of project %s!", project.getProjectName()));
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Path createProjectDirectory(String projectGitLink) throws FopEngineException {
        if (projectGitLink == null) {
            throw new FopEngineException("GitPath is null!");
        }
        int lastSlash = projectGitLink.lastIndexOf("/");
        if (lastSlash < 5) {
            throw new FopEngineException("Project git path is not correct:" + projectGitLink);
        }
        String repositoryName = projectGitLink.substring(lastSlash + 1).replaceAll(".git", "");
        Path projectDirectory = Paths.get(workingDirectory, repositoryName.toLowerCase());
        try {
            File workingDirFile = new File(workingDirectory);
            if (!workingDirFile.exists()) {
                workingDirFile.mkdirs();
            }
            if (Files.exists(projectDirectory)) {
                FileUtils.deleteDirectory(projectDirectory.toFile());
            }
            return Files.createDirectory(projectDirectory);
        } catch (IOException e) {
            throw new FopEngineException(String.format("Can't create project directory %s in a the working directory %s. Git link is %s",
                    repositoryName, workingDirectory, projectGitLink));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteFontFromProject(Font font, Project project) throws FopEngineException {
        Set<FontTriplet> triplets = font.getFontTriplets().stream().collect(Collectors.toSet());
        if (!project.getFontSet().remove(font)) {
            throw new FopEngineException("Project doesn't contain deleted font: " + font.getFontName());
        }
        for (FontTriplet triplet : triplets) {
            deleteTripletFromProject(triplet, project);
        }
        project.addFont(font);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteTripletFromProject(FontTriplet fontTriplet, Project project) throws FopEngineException {

        if (project.getProjectDirectory() == null) {
            new FopEngineException("Project directory for project %s is not set!");
        }

        if (!Files.exists(project.getProjectDirectory())) {
            new FopEngineException("Project directory for project %s does not exist!");
        }

        if (!fontTriplet.getFont().getFontTriplets().remove(fontTriplet)) {
            throw new FopEngineException("Project doesn't contain deleted triplet: " + fontTriplet);
        }


        if (!isTripletWithSameFontFiles(project, fontTriplet)) {
            deleteFontFiles(fontTriplet, project.getProjectDirectory());
        }

        fontTriplet.getFont().addTriplet(fontTriplet);
    }

    /**
     * {@inheritDoc}
     */
    boolean isTripletWithSameFontFiles(Project project, FontTriplet fontTriplet) {
        Optional<FontTriplet> tripletsOpt = project.getFontSet().stream()
                .map(font -> font.getFontTriplets())
                .flatMap(s -> s.stream())
                .filter(triplet -> triplet.getMetricsFile().equals(fontTriplet.getMetricsFile()))
                .findFirst();
        return tripletsOpt.isPresent();
    }

    /**
     * {@inheritDoc}
     */
    void deleteFontFiles(FontTriplet fontTriplet, Path projectDirectory) throws FopEngineException {
        try {
            Path metricsPath = Paths.get(projectDirectory.toString(), fontTriplet.getMetricsFile().getFullSource());
            if (Files.exists(metricsPath)) {
                Files.delete(metricsPath);
            }
            for (ProjectFileMapper fontFile : fontTriplet.getFontFiles()) {
                Path filePath = Paths.get(projectDirectory.toString(), fontFile.getFullSource());
                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                }
            }
        } catch (IOException e) {
            throw new FopEngineException(String.format("Can't delete file from directory %s", projectDirectory), e);
        }
    }

}