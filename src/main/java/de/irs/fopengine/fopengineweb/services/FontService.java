package de.irs.fopengine.fopengineweb.services;

import de.irs.fopengine.fopengineweb.commands.FontDTO;
import de.irs.fopengine.fopengineweb.exceptions.FopEngineException;
import de.irs.fopengine.fopengineweb.exceptions.GitException;
import de.irs.fopengine.fopengineweb.exceptions.XmlException;
import de.irs.fopengine.fopengineweb.model.Font;

import java.util.Set;

public interface FontService {

    Set<Font> getFonts() throws FopEngineException, GitException;

    Font getFontById(Long id) throws FopEngineException, GitException;

    FontDTO getFontCommandById(Long id) throws FopEngineException, GitException;

    void deleteFontById(Long id) throws FopEngineException, GitException;

    Font updateFont(Font font) throws FopEngineException, GitException, XmlException;

    Font registerNewFont(FontDTO font, Long projectId) throws FopEngineException, XmlException;

}