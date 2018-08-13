package com.marketahalikova.fopengineweb.enums;

public enum FontStyle {
    Normal,
    Bold,
    Italic,
    BoldItalic;

    public String getStyle() {

        String sytle;
        switch (this) {
            case Normal:
            case Bold:
                return "normal";
            case Italic:
            case BoldItalic:
                return "italic";
        }
        throw new RuntimeException("Case not implemented");
    }

    public String getWeight() {
        switch(this) {
            case Normal:
            case Italic:
                return "normal";
            case Bold:
            case BoldItalic:
                return "bold";
        }
        throw new RuntimeException("Case not implemented");
    }
}

