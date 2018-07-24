package com.marketahalikova.fopengineweb.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class Font {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fontName;
    @ManyToOne
    private Project project;

    public Font(String fontName) {
        this();
        this.fontName = fontName;
    }
}
