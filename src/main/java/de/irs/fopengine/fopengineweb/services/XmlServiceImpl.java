package de.irs.fopengine.fopengineweb.services;

import de.irs.fopengine.fopengineweb.exceptions.XmlException;
import de.irs.fopengine.fopengineweb.model.Project;
import de.irs.fopengine.fopengineweb.xml.ConfigurationHandler;
import de.irs.fopengine.fopengineweb.xml.PomHandler;
import de.irs.fopengine.fopengineweb.xml.UserconfigHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

/**
 * Service for xml handling - configuration, pom.xml, userconfig
 */
@Service
public class XmlServiceImpl implements XmlService {

    private final ConfigurationHandler configurationHandler;

    private final PomHandler pomHandler;

    private final UserconfigHandler userConfigHandler;

    @Autowired
    public XmlServiceImpl(ConfigurationHandler configurationHandler, PomHandler pomHandler,
                          UserconfigHandler userConfigHandler) {
        this.configurationHandler = configurationHandler;
        this.pomHandler = pomHandler;
        this.userConfigHandler = userConfigHandler;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Project readProject(String gitUrl, Path projectDirectory) throws XmlException {
        Project project = configurationHandler.createProject(gitUrl, projectDirectory);
        pomHandler.updateProjectFromPom(project);
        return project;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateFontsInXml(Project project) throws XmlException {
        userConfigHandler.updateFontsInXml(project);
        configurationHandler.updateFonts(project);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateProjectInXml(Project project) throws XmlException {
        configurationHandler.updateProjectInXml(project);
        pomHandler.updatePomFromProject(project);
    }
}
