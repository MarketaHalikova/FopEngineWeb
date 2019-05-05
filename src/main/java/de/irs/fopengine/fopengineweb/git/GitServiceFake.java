package de.irs.fopengine.fopengineweb.git;

import de.irs.fopengine.fopengineweb.exceptions.FopEngineException;
import de.irs.fopengine.fopengineweb.exceptions.GitException;
import de.irs.fopengine.fopengineweb.model.Project;
import de.irs.fopengine.fopengineweb.model.User;
import de.irs.fopengine.fopengineweb.services.FileSystemService;
import org.apache.commons.io.FileUtils;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

/**
 * Fake Git service.
 * It is not connected to git. It used data from local resources to spead up the start
 * The changes are not done in a repository
 */
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

    /**
     * Copy data from resource to simulate git clone
     * @param gitPath git web URL
     * @param workingDirectory directory to save new project
     * @return created project directory
     * @throws GitException something went wrong by cloning of git repository
     */
    @Override
    public Path cloneRepository(String gitPath, Path workingDirectory) throws GitException {
        System.out.println("GitServiceFake is used");
        try {
            Path targetPath = fileSystemService.createProjectDirectory(gitPath);
            if(Files.exists(targetPath)) {
                FileUtils.deleteDirectory(targetPath.toFile());
            }
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

    /**
     * Method does nothing and return always true
     * @param localDirectory local directory of a project
     * @return always true
     */
    @Override
    public boolean matchRemote(Path localDirectory){
        return true;
    }

    /**
     * Method does nothing
     * @param localDirectory local directory of a project
     * @param message commit message
     * @param user user which started the commit
     */
    @Override
    public void commitProject(Path localDirectory, String message, User user) {

    }

    /**
     * Method does nothing
     * @param projects list of projects to match
     */
    @Override
    public void matchAllProjects(Set<Project> projects) {

    }
}