package com.marketahalikova.fopengineweb.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
public class Font {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fontName;
    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Project project;

    public Font(String fontName) {
        this.fontName = fontName;
    }
}
