package com.marketahalikova.fopengineweb.xml;
import com.marketahalikova.fopengineweb.exceptions.XmlException;
import com.marketahalikova.fopengineweb.model.Project;

import java.nio.file.Path;

public interface ConfigurationHandler {


    /**
     * Create project from configuration file and set the project directory to the project.
     * Configuration file has a permanency location
     * in the project directory
     * @param gitPath
     * @param projectDirectory
     * @return
     * @throws XmlException
     */
    Project createProject(String gitPath, Path projectDirectory) throws XmlException;

    /**
     * Delete all fonts from a configuration and register all fonts and triplect from project
     * @param project
     * @throws XmlException
     */
    void updateFonts(Project project) throws XmlException;

    /**
     * Update configuration xml file - version, description
     * @param project
     * @throws XmlException
     */
    void updateProjectInXml(Project project) throws XmlException;
}