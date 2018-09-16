package com.marketahalikova.fopengineweb.mappers;


import com.marketahalikova.fopengineweb.commands.FontDTO;
import com.marketahalikova.fopengineweb.model.Font;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FontMapper {

    FontMapper INSTANCE = Mappers.getMapper(FontMapper.class);

    FontDTO fontToFontDTO(Font font);
    Font fontDTOToFont(FontDTO font);
}
