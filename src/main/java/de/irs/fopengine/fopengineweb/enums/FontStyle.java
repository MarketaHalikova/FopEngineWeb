package de.irs.fopengine.fopengineweb.enums;

/**
 * Font styles
 */
public enum FontStyle {
    normal,
    bold,
    italic,
    bolditalic;

    /**
     * Get Fop style from Enum for user config
     * @return fop style
     */
    public String getStyle() {
        switch (this) {
            case normal:
            case bold:
                return "normal";
            case italic:
            case bolditalic:
                return "italic";
        }
        throw new RuntimeException("Case not implemented");
    }

    /**
     * Return Fop Weight for Fop UserConfig
     * @return
     */
    public String getWeight() {
        switch(this) {
            case normal:
            case italic:
                return "normal";
            case bold:
            case bolditalic:
                return "bold";
        }
        throw new RuntimeException("Case not implemented");
    }
}

