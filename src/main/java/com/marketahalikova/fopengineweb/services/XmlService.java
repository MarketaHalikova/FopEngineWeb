package com.marketahalikova.fopengineweb.services;

import com.marketahalikova.fopengineweb.exceptions.XmlException;
import com.marketahalikova.fopengineweb.model.Project;

import java.nio.file.Path;

public interface XmlService {

    String PROJECT_RESOURCES_DIRECTORY = "src/main/resources";
    /**
     * Read all xml files for a project and create a new Project object
     * @param gitPath
     * @param configurationPath
     * @return
     */
    Project readProject(String gitPath, Path configurationPath) throws XmlException;

    /**
     * delete old fonts and set Fonts to all xml files
     * @param project
     */
    void updateFontsInXml(Project project) throws XmlException;

    /**
     * Update version and description in pom and configuration
     * @param project
     * @throws XmlException
     */
    void updateProjectInXml(Project project) throws XmlException;
}
