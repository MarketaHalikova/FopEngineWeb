package com.marketahalikova.fopengineweb.repositories;

import com.marketahalikova.fopengineweb.model.Font;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FontRepository extends CrudRepository<Font, Long> {

    Optional<Font> findFontByFontName(String fontName);
}