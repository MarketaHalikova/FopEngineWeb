package de.irs.fopengine.fopengineweb.mappers;

import de.irs.fopengine.fopengineweb.commands.TripletDTO;
import de.irs.fopengine.fopengineweb.model.FontTriplet;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FontTripletMapper {

    FontTripletMapper INSTANCE = Mappers.getMapper(FontTripletMapper.class);

    TripletDTO fontTripletToFontTripletDTO(FontTriplet fontTriplet);
    FontTriplet fontTripletDTOToFontTriplet(TripletDTO fontTriplet);
}
