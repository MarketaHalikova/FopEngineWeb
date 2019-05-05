package de.irs.fopengine.fopengineweb.services;

import de.irs.fopengine.fopengineweb.exceptions.XmlException;
import de.irs.fopengine.fopengineweb.model.Project;

import java.nio.file.Path;

public interface XmlService {

    String PROJECT_RESOURCES_DIRECTORY = "src/main/resources";
    /**
     * Read all xml files for a project and create a new Project object
     * @param gitUrl git web Url
     * @param configurationPath
     * @return
     */
    Project readProject(String gitUrl, Path configurationPath) throws XmlException;

    /**
     * delete old fonts and set Fonts to all xml files
     * @param project project with font
     */
    void updateFontsInXml(Project project) throws XmlException;

    /**
     * Update version and description in pom and configuration
     * @param project project to update
     * @throws XmlException
     */
    void updateProjectInXml(Project project) throws XmlException;
}
