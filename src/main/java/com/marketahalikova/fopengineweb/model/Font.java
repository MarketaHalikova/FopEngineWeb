package com.marketahalikova.fopengineweb.model;


import javax.persistence.*;
import java.util.Objects;

@Entity
public class Font {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fontName;
    @ManyToOne
    private Project project;


    public Font() {
    }

    public Font(String fontName) {
        this();
        this.fontName = fontName;
    }


    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Font font = (Font) o;
        return Objects.equals(id, font.id) &&
                Objects.equals(fontName, font.fontName);
    }

    @Override
    public String toString() {
        return "Font{" +
                "id=" + id +
                ", fontName='" + fontName + '\'' +
                '}';
    }
}
