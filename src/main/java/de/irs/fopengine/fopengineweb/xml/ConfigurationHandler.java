package de.irs.fopengine.fopengineweb.xml;

import de.irs.fopengine.fopengineweb.exceptions.XmlException;
import de.irs.fopengine.fopengineweb.model.Project;

import java.nio.file.Path;

/**
 * An Interface for handling with xml configuration file
 */
public interface ConfigurationHandler {

    /**
     * Create project from configuration file and set the project directory to the project.
     * Configuration file has a permanency location
     * in the project directory
     *
     * @param gitUrl
     * @param projectDirectory
     * @return
     * @throws XmlException
     */
    Project createProject(String gitUrl, Path projectDirectory) throws XmlException;

    /**
     * Delete all fonts from a configuration and register all fonts and triplect from project
     *
     * @param project
     * @throws XmlException
     */
    void updateFonts(Project project) throws XmlException;

    /**
     * Update configuration xml file - version, description
     *
     * @param project
     * @throws XmlException
     */
    void updateProjectInXml(Project project) throws XmlException;
}