package com.marketahalikova.fopengineweb.repositories;

import com.marketahalikova.fopengineweb.model.Font;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FontRepository extends CrudRepository<Font, Long> {

//    Optional<Font> findFontByFontNameAndProject_Id(String fontName);
    List<Font> findAllByOrderByFontNameAsc();
}