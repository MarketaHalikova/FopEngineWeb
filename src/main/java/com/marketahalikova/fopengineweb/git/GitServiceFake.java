package com.marketahalikova.fopengineweb.git;

import com.marketahalikova.fopengineweb.exceptions.FopEngineException;
import com.marketahalikova.fopengineweb.exceptions.GitException;
import com.marketahalikova.fopengineweb.model.Project;
import com.marketahalikova.fopengineweb.model.User;
import com.marketahalikova.fopengineweb.services.FileSystemService;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

@Service
@Profile("dev")
public class GitServiceFake implements GitService {

    private static Path TEST_PROJECT_1= Paths.get("src/integration-test/resources/fopengine-test-repository_1");
    private static Path TEST_PROJECT_2 = Paths.get("src/integration-test/resources/fopengine-test-repository_2");

    private final FileSystemService fileSystemService;

    @Autowired
    public GitServiceFake(FileSystemService fileSystemService) {
        this.fileSystemService = fileSystemService;
    }

    @Override
    public Path cloneRepository(String gitPath, Path workingDirectory) throws GitException {
        try {
            Path targetPath = fileSystemService.createProjectDirectory(gitPath);

            if(gitPath.contains("fopengine-test-repository_1")) {
                FileUtil.copyDir(TEST_PROJECT_1.toFile(), targetPath.toFile());
            } else if(gitPath.contains("fopengine-test-repository_2")) {
                FileUtil.copyDir(TEST_PROJECT_2.toFile(), targetPath.toFile());
            } else {
                FileUtil.copyDir(TEST_PROJECT_2.toFile(), targetPath.toFile());
            }
            return targetPath;
        } catch (FopEngineException | IOException e) {
            throw new GitException(e);
        }
    }

    @Override
    public boolean matchRemote(Path localDirectory){
        return true;
    }

    @Override
    public void commitProject(Path localDirectory, String message, User user) {

    }

    @Override
    public void matchAllProjects(Set<Project> projects) {

    }
}