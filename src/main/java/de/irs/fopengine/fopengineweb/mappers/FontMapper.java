package de.irs.fopengine.fopengineweb.mappers;


import de.irs.fopengine.fopengineweb.commands.FontDTO;
import de.irs.fopengine.fopengineweb.model.Font;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FontMapper {

    FontMapper INSTANCE = Mappers.getMapper(FontMapper.class);

    FontDTO fontToFontDTO(Font font);
    Font fontDTOToFont(FontDTO font);
}
