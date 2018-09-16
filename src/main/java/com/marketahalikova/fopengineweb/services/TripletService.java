package com.marketahalikova.fopengineweb.services;

import com.marketahalikova.fopengineweb.commands.TripletDTO;
import com.marketahalikova.fopengineweb.exceptions.FopEngineException;
import com.marketahalikova.fopengineweb.exceptions.GitException;
import com.marketahalikova.fopengineweb.model.FontTriplet;

public interface TripletService {

    void deleteFontTripletById(Long id) throws FopEngineException;

    FontTriplet updateTriplet(FontTriplet fontTriplet) throws FopEngineException;

    FontTriplet registerNewTriplet(TripletDTO triplet, Long fontId) throws FopEngineException;

    FontTriplet getTripletById(Long id) throws FopEngineException;
    TripletDTO getTripletDTOById(Long id) throws FopEngineException, GitException;
}
