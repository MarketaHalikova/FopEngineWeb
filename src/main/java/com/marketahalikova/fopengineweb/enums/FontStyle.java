package com.marketahalikova.fopengineweb.enums;

public enum FontStyle {
    normal,
    bold,
    italic,
    bolditalic;

    public String getStyle() {

        String sytle;
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

