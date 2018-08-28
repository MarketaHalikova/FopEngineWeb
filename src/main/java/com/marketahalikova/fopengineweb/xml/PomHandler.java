package com.marketahalikova.fopengineweb.xml;

import com.marketahalikova.fopengineweb.exceptions.XmlException;
import com.marketahalikova.fopengineweb.model.Project;

/**
 * Interface to handle pom.xml
 * <p>
 * read maven artifact
 * read dependencies
 * </p>
 */

public interface PomHandler {

    /**
     * Update project from pom.xml - dependencies, maven artifact
     * Project must contain project directory
     *
     * @param project
     * @throws XmlException
     */
    void updateProjectFromPom(Project project) throws XmlException;

    /**
     * Update version and dependency from project into pom.xml and save the changes
     * Project must contain a project directory
     *
     * @param project
     * @throws XmlException
     */
    void updatePomFromProject(Project project) throws XmlException;
}