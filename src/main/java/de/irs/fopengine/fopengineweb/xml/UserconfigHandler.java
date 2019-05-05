package de.irs.fopengine.fopengineweb.xml;

import de.irs.fopengine.fopengineweb.exceptions.XmlException;
import de.irs.fopengine.fopengineweb.model.Project;

import java.nio.file.Path;

/**
 * Interface for userconfig handling
 */
public interface UserconfigHandler {

    /**
     * Update fonts in userconfig
     * @param project project
     * @throws XmlException error in xml handling
     */
    void updateFontsInXml(Project project) throws XmlException;

    /**
     * Get path to userconfig in local file system
     * @param project
     * @return path to userconfig in local file system
     * @throws XmlException error in xml handling
     */
    Path getUserConfigPath(Project project) throws XmlException;
}