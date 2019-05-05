package de.irs.fopengine.fopengineweb.utils;

import javax.persistence.AttributeConverter;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Converter from path in Project to string in a database
 */
public class PathConverter implements AttributeConverter<Path, String> {

    @Override
    public String convertToDatabaseColumn(Path path) {
        return path == null ? null : path.toString();
    }

    @Override
    public Path convertToEntityAttribute(String path) {
        return path==null?null:Paths.get(path);
    }
}