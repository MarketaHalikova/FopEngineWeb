package com.marketahalikova.fopengineweb.services;

import com.marketahalikova.fopengineweb.exceptions.XmlException;
import com.marketahalikova.fopengineweb.model.Project;
import com.marketahalikova.fopengineweb.xml.ConfigurationHandler;
import com.marketahalikova.fopengineweb.xml.PomHandler;
import com.marketahalikova.fopengineweb.xml.UserconfigHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

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


    @Override
    public Project readProject(String gitPath, Path projectDirectory) throws XmlException {
        Project project = configurationHandler.createProject(gitPath, projectDirectory);
        pomHandler.updateProjectFromPom(project);
        return project;
    }

    @Override
    public void updateFontsInXml(Project project) throws XmlException {
        userConfigHandler.updateFontsInXml(project);
        configurationHandler.updateFonts(project);
    }


    @Override
    public void updateProjectInXml(Project project) throws XmlException {
        configurationHandler.updateProjectInXml(project);
        pomHandler.updatePomFromProject(project);
    }
}
