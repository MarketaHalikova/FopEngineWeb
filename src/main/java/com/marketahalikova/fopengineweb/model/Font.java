package com.marketahalikova.fopengineweb.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Font implements Comparable<Font>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fontName;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Project project;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "font")
    public Set<FontTriplet> fontTriplets;

    public Font(){
        fontTriplets = new HashSet<>(4);

    }

    public Font(String fontName) {
        this();
        this.fontName = fontName;
    }

    public void addTriplet(FontTriplet triplet) {
        fontTriplets.add(triplet);
        triplet.setFont(this);
    }

    public void setFontTriplets(Set<FontTriplet> fontTriplets) {
        this.fontTriplets = fontTriplets;
        fontTriplets.forEach(t -> t.setFont(this));
    }

    public Set<String> getAbsentTripletStyles(){
        Set<String> absentStyles = new HashSet<>(4);
        absentStyles.add("normal");
        absentStyles.add("bold");
        absentStyles.add("italic");
        absentStyles.add("bolditalic");
        Set<String> presentStyles = new HashSet<>(4);
        for (FontTriplet f :fontTriplets
             ) {
            presentStyles.add(f.getFontStyle().toString());
        }
        absentStyles.removeAll(presentStyles);
        return absentStyles;
    }

    @Override
    public int compareTo(Font o) {
        return getFontName().toLowerCase().compareTo(o.getFontName().toLowerCase());
    }
}