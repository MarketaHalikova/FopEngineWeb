package com.marketahalikova.fopengineweb.utils;

import javax.persistence.AttributeConverter;
import java.nio.file.Path;
import java.nio.file.Paths;

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