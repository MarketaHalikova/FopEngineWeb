package com.marketahalikova.fopengineweb.model;

import lombok.Data;

@Data
public class MavenArtifact {

    private String group;
    private String artifact;
    private  String version;

    public MavenArtifact(String group, String artifact, String version) {
        this.group = group;
        this.artifact = artifact;
        this.version = version;
    }
}
