package com.marketahalikova.fopengineweb.model;

import com.marketahalikova.fopengineweb.enums.FontStyle;
import lombok.Data;
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

    @OneToOne(cascade = CascadeType.ALL)
    @Where(clause="FILE_TYPE = 'metrics_file'")
    private ProjectFileMapper metricsFile;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "file_to_source",
            joinColumns = @JoinColumn(name = "source_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id")
    )
    @Where(clause = "FILE_TYPE = 'font_file'")
    private Set<ProjectFileMapper> fontFiles;


    public FontTriplet(){
        fontFiles = new HashSet<>(2);
    }

    public FontTriplet(FontStyle fontStyle, String inddName, String inddStyle) {
        this.fontStyle = fontStyle;
        this.inddName = inddName;
        this.inddStyle = inddStyle;
    }
    public String getEmbedFile() {
        return fontFiles.stream()
                .filter(f -> f.getFileName().endsWith("ttf") || f.getFileName().endsWith("pfb"))
                .findAny()
                .map(fontMapper -> fontMapper.getFullTarget())
                .orElse("");
    }
}