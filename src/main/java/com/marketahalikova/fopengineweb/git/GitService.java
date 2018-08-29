package com.marketahalikova.fopengineweb.git;

import com.marketahalikova.fopengineweb.exceptions.GitException;
import com.marketahalikova.fopengineweb.model.Project;
import com.marketahalikova.fopengineweb.model.User;

import java.nio.file.Path;
import java.util.Set;


public interface GitService {

    /**
     * Clone remote repository to a local directory
     *
     */
    Path cloneRepository(String gitPath, Path localDirectory) throws GitException;

    /**
     * Check changes in repository and update local repository from remote if it is changed
     */
    boolean matchRemote(Path localDirectory) throws GitException;

    /**
     * Create commit and push project to a repository
     */
    void commitProject(Path localDirectory, String message, User user) throws GitException ;

    void matchAllProjects(Set<Project> projects) throws GitException ;
}