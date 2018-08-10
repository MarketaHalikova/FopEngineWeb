package com.marketahalikova.fopengineweb.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class MavenArtifact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String artifact;
    @Column(name = "maven_group")
    private String group;
    private String version;

    public MavenArtifact(String group, String artifact, String version) {
        this.artifact = artifact;
        this.group = group;
        this.version = version;
    }

}
