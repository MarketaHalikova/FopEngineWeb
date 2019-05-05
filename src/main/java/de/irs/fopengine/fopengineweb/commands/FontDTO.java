package de.irs.fopengine.fopengineweb.commands;

import lombok.Data;

/**
 * Data Transfer object for font
 */
@Data
public class FontDTO {
    private Long id;
    private String fontName;
    private Long projectId;
}
