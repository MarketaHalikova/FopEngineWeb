package com.marketahalikova.fopengineweb.mappers;

import com.marketahalikova.fopengineweb.commands.TripletDTO;
import com.marketahalikova.fopengineweb.model.FontTriplet;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FontTripletMapper {

    FontTripletMapper INSTANCE = Mappers.getMapper(FontTripletMapper.class);

    TripletDTO fontTripletToFontTripletDTO(FontTriplet fontTriplet);
    FontTriplet fontTripletDTOToFontTriplet(TripletDTO fontTriplet);
}
