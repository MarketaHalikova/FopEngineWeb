package com.marketahalikova.fopengineweb.xml;

import com.marketahalikova.fopengineweb.exceptions.XmlException;
import com.marketahalikova.fopengineweb.model.Project;

import java.nio.file.Path;

public interface UserconfigHandler {

    void updateFontsInXml(Project project) throws XmlException;

    Path getUserConfigPath(Project project) throws XmlException;
}