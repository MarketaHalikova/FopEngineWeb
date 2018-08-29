package com.marketahalikova.fopengineweb.git;

import com.marketahalikova.fopengineweb.exceptions.GitException;
import com.marketahalikova.fopengineweb.model.Project;
import com.marketahalikova.fopengineweb.model.User;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@Service
@Profile("!dev")
public class GitServiceImpl implements  GitService{

    private String username = "";
    private String password = "";


    private Optional<UsernamePasswordCredentialsProvider> credentialsOptional = Optional.empty();

    @Override
    public Path cloneRepository(String gitPath, Path localDirectory) throws GitException {
        try {
            Git git = GitUtils.cloneRepository(gitPath, localDirectory.toString(),
                    credentialsOptional.orElse(createCredentials()));
            return Paths.get(git.getRepository().getDirectory().getParent());
        } catch (GitAPIException e) {
            throw new GitException(e);
        }
    }


    @Override
    public boolean matchRemote(Path localDirectory) throws GitException {
        try {
            Git git = GitUtils.createGit(localDirectory.toString());
            return GitUtils.updateFromRemoteIfChanged(git, credentialsOptional.orElse(createCredentials()));
        } catch (IOException | GitAPIException e) {
            throw new GitException(e);
        }
    }

    @Override
    public void commitProject(Path localDirectory, String message, User user) throws GitException {
        try {
            Git git = GitUtils.createGit(localDirectory.toString());
            if (GitUtils.localChanges(git)) {
                GitUtils.addCommitPush(user.getUserName(), user.getUserName(), message, git,
                        credentialsOptional.orElse(createCredentials()), true);
            }
        } catch (IOException | GitAPIException e) {
            throw new GitException(e);
        }
    }

    @Override
    public void matchAllProjects(Set<Project> projects) {
        projects.parallelStream().map(Project::getProjectDirectory).forEach(path -> {
            try {
                matchRemote(path);
            } catch (GitException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private UsernamePasswordCredentialsProvider createCredentials() {
        if (!credentialsOptional.isPresent()) {
            credentialsOptional = Optional.of(GitUtils.createCredentials(Optional.ofNullable(getUsername()).orElse(""),
                    Optional.ofNullable(getPassword()).orElse("")));
        }
        return credentialsOptional.get();
    }
}