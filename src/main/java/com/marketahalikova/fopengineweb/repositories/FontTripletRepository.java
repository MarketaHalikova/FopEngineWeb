package com.marketahalikova.fopengineweb.repositories;

import com.marketahalikova.fopengineweb.model.FontTriplet;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FontTripletRepository extends CrudRepository<FontTriplet, Long> {

    Optional<FontTriplet> findFontTripletByInddNameAndInddStyle(String inddName, String inddStyle);
}