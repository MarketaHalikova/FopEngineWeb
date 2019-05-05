package de.irs.fopengine.fopengineweb.repositories;

import de.irs.fopengine.fopengineweb.model.Font;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FontRepository extends CrudRepository<Font, Long> {

    List<Font> findAllByOrderByFontNameAsc();
}