package com.marketahalikova.fopengineweb.commands;

import com.marketahalikova.fopengineweb.enums.FileType;
import com.marketahalikova.fopengineweb.enums.FontStyle;
import com.marketahalikova.fopengineweb.model.ProjectFileMapper;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class TripletDTO {
    private Long id;
    public FontStyle fontStyle;
    private String inddName;
    private String inddStyle;
    private Long fontId;
    private Set<ProjectFileMapper> fontFiles;


    public void addFontFile(ProjectFileMapper fontFile) {
        fontFiles = new HashSet<>(2);
        fontFiles.add(fontFile);
        fontFile.setFileType(FileType.font_file);
    }
}
