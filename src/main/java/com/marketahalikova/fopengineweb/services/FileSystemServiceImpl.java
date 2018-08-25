package com.marketahalikova.fopengineweb.services;

import com.marketahalikova.fopengineweb.exceptions.FopEngineException;
import com.marketahalikova.fopengineweb.model.Font;
import com.marketahalikova.fopengineweb.model.FontTriplet;
import com.marketahalikova.fopengineweb.model.Project;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

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
// project directory has to be set
// if exists, delete it with all content - Files.exists(),  FileUtils.deleteDirectory(projectDirectory.toFile());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Path createProjectDirectory(String projectGitLink) throws FopEngineException {
// gitLink not null
// find repository name from gitlink
// https://github.com/MarketaHalikova/FopEngineWeb.git   --> fopengineweb   - in lovercase - method on String - projectRepositoryName.toLowerCase()
// find last character - projectGitLink.lastIndexOf
// substring - projectGitLink.substring
// replace in string - projectGitLink.replaceAll(".git", "");
// create directory   - Files.createDirectory
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteFontFromProject(Font font, Project project) throws FopEngineException {
// delete font from project - exception if font does not exist in project
// for every triplet - delete triplet using method deleteTripletFromProject
// return triplet back to project
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteTripletFromProject(FontTriplet fontTriplet, Project project) throws FopEngineException {
// project dir must be set
// project dir must exist
// remove triplet from font - triplet have to exist in font
// find if there is a triplet with the same metrics file - use method isTripletWithSameFontFiles
// if not - delete files - metrics, font files   - use method deleteFontFiles
// return triplet back
    }

    /**
     * Return true if there is other triplet with the same font files - it uses metrics file to check it
     *
     * @param project
     * @param fontTriplet
     * @return
     */
    boolean isTripletWithSameFontFiles(Project project, FontTriplet fontTriplet) {
// use stream project.getFonts().stream()
// 1. map to get font triplets
// 2. flatMap - all triplets from all fonts to one stream of tiplets     .flatMap(s -> s.stream())
// 3. filter if a triplet has the same triplet.getMetricsFile()
// 4. find if any exists - return true else return false

        // Optional<FontTriplet> tripletsOpt = project.getFonts().stream()
        // ???
        // .flatMap(s -> s.stream())
        //
        //
        // return tripletsOpt.isPresent();
        return true;
    }

    /**
     * Delete triplet files from file system - metrics and all font files
     * @param fontTriplet
     * @param projectDirectory
     * @throws FopEngineException
     */
    void deleteFontFiles(FontTriplet fontTriplet, Path projectDirectory) throws FopEngineException {
//        try {
//            // Files.exists()
//            // Files.delete()
//        } catch (IOException e) {
//            throw new FopEngineException(String.format("Can't delete file from directory %s", projectDirectory), e);
//        }
    }

}