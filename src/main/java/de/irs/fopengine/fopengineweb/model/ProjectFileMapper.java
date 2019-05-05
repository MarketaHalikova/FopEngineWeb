package de.irs.fopengine.fopengineweb.model;

import de.irs.fopengine.fopengineweb.enums.FileType;
import de.irs.fopengine.fopengineweb.services.XmlService;
import lombok.Data;

import javax.persistence.*;
import java.nio.file.Paths;

/**
 * File mapper from resource in jar to local file system
 */
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
        if(sourcePath==null){
            return Paths.get(XmlService.PROJECT_RESOURCES_DIRECTORY, fileName).toString();
        } else {
            return Paths.get(XmlService.PROJECT_RESOURCES_DIRECTORY, sourcePath, fileName).toString();
        }

    }
}