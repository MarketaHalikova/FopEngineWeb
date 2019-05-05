package de.irs.fopengine.fopengineweb.repositories;

import de.irs.fopengine.fopengineweb.model.FontTriplet;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FontTripletRepository extends CrudRepository<FontTriplet, Long> {

    Optional<FontTriplet> findFontTripletByInddNameAndInddStyle(String inddName, String inddStyle);
}