package de.irs.fopengine.fopengineweb.services;

import de.irs.fopengine.fopengineweb.commands.TripletDTO;
import de.irs.fopengine.fopengineweb.exceptions.FopEngineException;
import de.irs.fopengine.fopengineweb.exceptions.GitException;
import de.irs.fopengine.fopengineweb.model.FontTriplet;

public interface TripletService {

    /**
     * Delete font by Id
     * @param id font od
     * @throws FopEngineException
     */
    void deleteFontTripletById(Long id) throws FopEngineException;

    /**
     * Update triplet
     * @param fontTriplet font triplet to update
     * @return updatet font triplet
     * @throws FopEngineException
     */
    FontTriplet updateTriplet(FontTriplet fontTriplet) throws FopEngineException;

    /**
     * Create a new Triplet
     * @param triplet new triplet DTO
     * @param fontId id of parent font
     * @return new triplet
     * @throws FopEngineException
     */
    FontTriplet registerNewTriplet(TripletDTO triplet, Long fontId) throws FopEngineException;

    /**
     * Get triplet by id
     * @param id triplet id
     * @return tripet by id
     * @throws FopEngineException exception in app
     */
    FontTriplet getTripletById(Long id) throws FopEngineException;

    /**
     * Return tripletDTO by id
     * @param id triplet id
     * @return tripletDTO by id
     * @throws FopEngineException exception in app
     * @throws GitException exception in git
     */
    TripletDTO getTripletDTOById(Long id) throws FopEngineException, GitException;
}
