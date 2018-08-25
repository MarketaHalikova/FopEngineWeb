package com.marketahalikova.fopengineweb.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
        fontTriplets = fontTriplets;
        fontTriplets.forEach(t -> t.setFont(this));
    }}