package de.irs.fopengine.fopengineweb.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Font in project. Font can contain 0 to 4 triplets
 */
@Data
@Entity
public class Font implements Comparable<Font>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fontName;

    /**
     * Parent project
     */
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

    /**
     * add a triplet and create the link from triplet to font
     * @param triplet
     */
    public void addTriplet(FontTriplet triplet) {
        fontTriplets.add(triplet);
        triplet.setFont(this);
    }

    /**
     * Set a new set of font triplets. The set is replaced
     * @param fontTriplets
     */
    public void setFontTriplets(Set<FontTriplet> fontTriplets) {
        this.fontTriplets = fontTriplets;
        fontTriplets.forEach(t -> t.setFont(this));
    }

    /**
     * REturn missing triplet styles
     * @return  missing triplet styles
     */
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

    /**
     * Compare two triplets to sort it.
     * @param otherFont font for comparing
     * @return  of comparing
     */
    @Override
    public int compareTo(Font otherFont) {
        return getFontName().toLowerCase().compareTo(otherFont.getFontName().toLowerCase());
    }
}