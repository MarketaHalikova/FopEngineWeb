package de.irs.fopengine.fopengineweb.git;

import de.irs.fopengine.fopengineweb.exceptions.GitException;
import de.irs.fopengine.fopengineweb.model.Project;
import de.irs.fopengine.fopengineweb.model.User;

import java.nio.file.Path;
import java.util.Set;

/**
 * Service for git handling
 */
public interface GitService {

    /**
     * Clone remote repository to a local directory
     *
     */
    Path cloneRepository(String gitPath, Path localDirectory) throws GitException;

    /**
     * Check changes in repository and update local repository from remote if it is changed
     * @param localDirectory local directory of a project
     * @return true if project match
     */
    boolean matchRemote(Path localDirectory) throws GitException;

    /**
     * Create commit and push project to a repository
     * @param localDirectory local directory of a project
     * @param message commit message
     * @param user user which started the commit
     */
    void commitProject(Path localDirectory, String message, User user) throws GitException ;

    /**
     * Check all projects for changes in remote git repository. If there are changes
     * the local repository should by updated
     * @param projects list of projects to match
     * @throws GitException
     */
    void matchAllProjects(Set<Project> projects) throws GitException ;
}