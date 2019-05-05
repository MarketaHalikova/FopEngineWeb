package de.irs.fopengine.fopengineweb.model;

import de.irs.fopengine.fopengineweb.enums.FileType;
import de.irs.fopengine.fopengineweb.enums.FontStyle;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Font Triplet in project
 */
@Data
@Entity
public class FontTriplet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    public FontStyle fontStyle;
    /**
     * Name of font in indesign
     */
    private String inddName;
    /**
     * Style of font in indesign
     */
    private String inddStyle;

    /**
     * Parent Font
     */
    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Font font;

    @OneToOne(cascade = CascadeType.ALL)
    @Where(clause="FILE_TYPE = 'metrics_file'")
    private ProjectFileMapper metricsFile;

    /**
     * One for true type, two for Type1 Fonts
     */
    @OneToMany(cascade = CascadeType.ALL)
    @Where(clause = "FILE_TYPE = 'font_file'")
    private Set<ProjectFileMapper> fontFiles;


    public FontTriplet(){
        fontFiles = new HashSet<>(2);
    }

    public FontTriplet(FontStyle fontStyle) {
        this();
        this.fontStyle = fontStyle;
    }

    public FontTriplet(FontStyle fontStyle, String inddName, String inddStyle) {
        this();
        this.fontStyle = fontStyle;
        this.inddName = inddName;
        this.inddStyle = inddStyle;
    }

    /**
     * Return embed file. It should be ttf or pfb extension
     * @return embed file (ttf or pfb)
     */
    public String getEmbedFile() {
        return fontFiles.stream()
                .filter(f -> f.getFileName().endsWith("ttf") || f.getFileName().endsWith("pfb"))
                .findAny()
                .map(fontMapper -> fontMapper.getFileName())
                .orElse("");
    }

    /**
     * Return path to embed file in resources. It should be ttf or pfb extension
     * @return path to embed file in resources
     */
    public String getEmbedFileSource() {
        return fontFiles.stream()
                .filter(f -> f.getFileName().endsWith("ttf") || f.getFileName().endsWith("pfb"))
                .findAny()
                .map(fontMapper -> fontMapper.getFullSource())
                .orElse("");
    }

    /**
     * Return path to embed file in local file system. It should be ttf or pfb extension
     * @return
     */
    public String getEmbedFileTarget() {
        return fontFiles.stream()
                .filter(f -> f.getFileName().endsWith("ttf") || f.getFileName().endsWith("pfb"))
                .findAny()
                .map(fontMapper -> fontMapper.getFullTarget())
                .orElse("");
    }

    /**
     * Setter for metrics file. This method sets the type of ProjectFileMapper to metrics_file
     * @param metricsFile mapper for metrics file
     */
    public void setMetricsFile(ProjectFileMapper metricsFile) {
        metricsFile.setFileType(FileType.metrics_file);
        this.metricsFile = metricsFile;
    }

    /**
     * Setter for font files. This method sets the type of ProjectFileMappers to font_file.
     * The old set is replaced
     * @param fontFiles mapper for font files
     */
    public void setFontFiles(Set<ProjectFileMapper> fontFiles) {
        fontFiles.forEach(projectFileMapper -> projectFileMapper.setFileType(FileType.font_file));
        this.fontFiles = fontFiles;
    }

    /**
     * Add font file to set. This method sets the type of ProjectFileMapper to font_file.
     * @param fontFile mapper for a font file
     */
    public void addFontFile(ProjectFileMapper fontFile) {
        fontFiles.add(fontFile);
        fontFile.setFileType(FileType.font_file);
    }

}