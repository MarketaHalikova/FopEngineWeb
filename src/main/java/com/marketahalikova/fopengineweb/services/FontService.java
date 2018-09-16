package com.marketahalikova.fopengineweb.services;

import com.marketahalikova.fopengineweb.commands.FontDTO;
import com.marketahalikova.fopengineweb.exceptions.FopEngineException;
import com.marketahalikova.fopengineweb.exceptions.GitException;
import com.marketahalikova.fopengineweb.exceptions.XmlException;
import com.marketahalikova.fopengineweb.model.Font;

import java.util.Set;

public interface FontService {

    Set<Font> getFonts() throws FopEngineException, GitException;
    Font getFontById(Long id) throws FopEngineException, GitException;
    FontDTO getFontCommandById(Long id) throws FopEngineException, GitException;
    void deleteFontById(Long id) throws FopEngineException, GitException;
    Font updateFont(Font font) throws FopEngineException, GitException, XmlException;
    Font registerNewFont(FontDTO font, Long projectId) throws FopEngineException, XmlException;

}