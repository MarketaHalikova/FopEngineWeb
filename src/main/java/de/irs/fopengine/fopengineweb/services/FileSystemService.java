package de.irs.fopengine.fopengineweb.services;

import de.irs.fopengine.fopengineweb.exceptions.FopEngineException;
import de.irs.fopengine.fopengineweb.model.Font;
import de.irs.fopengine.fopengineweb.model.FontTriplet;
import de.irs.fopengine.fopengineweb.model.Project;

import java.nio.file.Path;

public interface FileSystemService {

    /**
     * Delete project repository including all files
     * @param project
     * @throws FopEngineException
     */
    void deleteProjectDirectory(Project project) throws FopEngineException;

    /**
     * Create an empty project directory
     * @param projectGitLink
     * @return
     * @throws FopEngineException
     */
    Path createProjectDirectory(String projectGitLink) throws FopEngineException;

    /**
     * Delete all triplets and theirs files of font from parameter from file system.
     * Font is not deleted from project object
     * If other font uses the same font files, font files will not be deleted
     * If the font files are used inside one font and nowhere else, the files should be deleted
     * @param font
     * @param project
     * @throws FopEngineException project does not contain deleted font
     */
    void deleteFontFromProject(Font font, Project project) throws FopEngineException;

    /**
     * Delete triplet from project file system and from project.
     * Triplet is not deleted from project object.
     * If there is an other triplet that uses the same font files, the font files are not be deleted.
     * in oposit if no other triplet uses this font files, this method will delete them from the file system
     * @param fontTriplet
     * @param project
     * @throws FopEngineException
     */
    void deleteTripletFromProject(FontTriplet fontTriplet, Project project) throws FopEngineException;


}