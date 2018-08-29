package com.marketahalikova.fopengineweb.model;

import com.marketahalikova.fopengineweb.enums.FileType;
import com.marketahalikova.fopengineweb.enums.FontStyle;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity
public class FontTriplet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    public FontStyle fontStyle;
    private String inddName;
    private String inddStyle;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Font font;

    @OneToOne(cascade = CascadeType.ALL)
    @Where(clause="FILE_TYPE = 'metrics_file'")
    private ProjectFileMapper metricsFile;

    @OneToMany(cascade = CascadeType.ALL)
    @Where(clause = "FILE_TYPE = 'font_file'")
    private Set<ProjectFileMapper> fontFiles;


    public FontTriplet(){
        fontFiles = new HashSet<>(2);
    }

    public FontTriplet(FontStyle fontStyle, String inddName, String inddStyle) {
        this();
        this.fontStyle = fontStyle;
        this.inddName = inddName;
        this.inddStyle = inddStyle;
    }
    public FontTriplet(FontStyle fontStyle) {
        this();
        this.fontStyle = fontStyle;
    }

    public String getEmbedFile() {
        return fontFiles.stream()
                .filter(f -> f.getFileName().endsWith("ttf") || f.getFileName().endsWith("pfb"))
                .findAny()
                .map(fontMapper -> fontMapper.getFullTarget())
                .orElse("");
    }

    public String getEmbedFileSource() {
        return fontFiles.stream()
                .filter(f -> f.getFileName().endsWith("ttf") || f.getFileName().endsWith("pfb"))
                .findAny()
                .map(fontMapper -> fontMapper.getFullSource())
                .orElse("");
    }

    public String getEmbedFileTarget() {
        return fontFiles.stream()
                .filter(f -> f.getFileName().endsWith("ttf") || f.getFileName().endsWith("pfb"))
                .findAny()
                .map(fontMapper -> fontMapper.getFullTarget())
                .orElse("");
    }

    public void setMetricsFile(ProjectFileMapper metricsFile) {
        metricsFile.setFileType(FileType.metrics_file);
        this.metricsFile = metricsFile;
    }

    public void setFontFiles(Set<ProjectFileMapper> fontFiles) {
        fontFiles.forEach(projectFileMapper -> projectFileMapper.setFileType(FileType.font_file));
        this.fontFiles = fontFiles;
    }

    public void addFontFile(ProjectFileMapper fontFile) {
        fontFiles.add(fontFile);
        fontFile.setFileType(FileType.font_file);
    }

}