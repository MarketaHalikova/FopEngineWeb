package de.irs.fopengine.fopengineweb.commands;

import de.irs.fopengine.fopengineweb.enums.FileType;
import de.irs.fopengine.fopengineweb.enums.FontStyle;
import de.irs.fopengine.fopengineweb.model.ProjectFileMapper;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * Data Transfer object for font triplet
 */
@Data
public class TripletDTO {
    private Long id;
    public FontStyle fontStyle;
    private String inddName;
    private String inddStyle;
    private Long fontId;
    // 1 or 2 font files
    private Set<ProjectFileMapper> fontFiles;

    public TripletDTO() {
        fontFiles = new HashSet<>(2);
    }

    /**
     * Add font and set the FileType
     *
     * @param fontFile
     */
    public void addFontFile(ProjectFileMapper fontFile) {
        fontFiles.add(fontFile);
        fontFile.setFileType(FileType.font_file);
    }
}
