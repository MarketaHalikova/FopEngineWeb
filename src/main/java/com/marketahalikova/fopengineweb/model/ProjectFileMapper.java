package com.marketahalikova.fopengineweb.model;

import com.marketahalikova.fopengineweb.enums.FileType;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ProjectFileMapper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String sourcePath;
    private String targetPath;

    @Enumerated(value = EnumType.STRING)
    private FileType fileType;

    public ProjectFileMapper(String fileName, String sourcePath, String targetPath) {
        this.fileName = fileName;
        this.sourcePath = sourcePath;
        this.targetPath = targetPath;
    }

}