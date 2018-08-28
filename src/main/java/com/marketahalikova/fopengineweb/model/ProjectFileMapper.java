package com.marketahalikova.fopengineweb.model;

import com.marketahalikova.fopengineweb.enums.FileType;
import com.marketahalikova.fopengineweb.services.XmlService;
import lombok.Data;

import javax.persistence.*;
import java.nio.file.Paths;

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

    public String getFullTarget() {
        return Paths.get(targetPath, fileName).toString();
    }

    public String getFullSource() {
        return Paths.get(XmlService.PROJECT_RESOURCES_DIRECTORY, sourcePath, fileName).toString();
    }
}