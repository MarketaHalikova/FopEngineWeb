package com.marketahalikova.fopengineweb.git;

import com.marketahalikova.fopengineweb.exceptions.GitException;
import com.marketahalikova.fopengineweb.model.Project;
import com.marketahalikova.fopengineweb.model.User;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;


public class GitServiceImplIT {

    public static final String PROJECT_DIRECTORY = "target/project-directory";
    public static final String PROJECT_NAME = "fopengine-test-repository";
    public static final String PROJECT_NAME_NEW = "fopengine-test-repository-new";

    public static final String GITHUB_REPOSITORY = "https://github.com/MarketaTest/fopengine-test-repository.git";
    public static final String POM_XML = "pom.xml";
    public static final String GITIGNORE = ".gitignore";
    public static final String SRC_DIRDECTORY = "src";
    public static final String GIT_DIRECTORY = ".git";
    public static final String NEW_FILE = "pom1.xml";

    public static final String GITHUB_USERNAME = "MarketaTest";
    public static final String GITHUB_PASSWORD = "MarketaTest1";

    private GitServiceImpl gitService;
    private Project project;
    private Project project1;
    private User user;

    @BeforeClass
    public static void setUpClass() throws Exception {
        if (Files.exists(Paths.get(PROJECT_DIRECTORY))) {
            FileUtils.deleteDirectory(Paths.get(PROJECT_DIRECTORY).toFile());
        }
        if (Files.exists(Paths.get(PROJECT_DIRECTORY))) {
            FileUtils.deleteDirectory(Paths.get(PROJECT_DIRECTORY).toFile());
        }
    }

    @Before
    public void setUp() throws Exception {
        gitService = new GitServiceImpl();
        gitService.setUsername(GITHUB_USERNAME);
        gitService.setPassword(GITHUB_PASSWORD);
        project = new Project();
        project.setGitPath(GITHUB_REPOSITORY);
        project.setProjectName(PROJECT_NAME);
        project.setProjectDirectory(Paths.get(PROJECT_DIRECTORY, PROJECT_NAME));
        project1 = new Project();
        project1.setGitPath(GITHUB_REPOSITORY);
        project1.setProjectName(PROJECT_NAME_NEW);
        project1.setProjectDirectory(Paths.get(PROJECT_DIRECTORY, PROJECT_NAME_NEW));

        user = new User("testUser", "");
    }

//    @Test
//    public void cloneRepository_shouldCloneRemoteRepository() throws Exception {
//        project.setProjectDirectory(Paths.get(PROJECT_DIRECTORY, PROJECT_NAME + "_1"));
//        assertThat(project.getProjectDirectory()).doesNotExist();
//        gitService.cloneRepository(project.getGitPath(), project.getProjectDirectory());
//        assertThat(project.getProjectDirectory()).exists();
//        assertThat(Paths.get(project.getProjectDirectory().toString(), POM_XML)).exists();
//        assertThat(Paths.get(project.getProjectDirectory().toString(), GITIGNORE)).exists();
//        assertThat(Paths.get(project.getProjectDirectory().toString(), SRC_DIRDECTORY)).exists();
//        assertThat(Paths.get(project.getProjectDirectory().toString(), GIT_DIRECTORY)).exists();
//    }

    @Test
    public void matchProject_remoteProjectNotChanged() throws GitException {
        project.setProjectDirectory(Paths.get(PROJECT_DIRECTORY, PROJECT_NAME + "_2"));
        assertThat(project.getProjectDirectory()).doesNotExist();
        gitService.cloneRepository(project.getGitPath(), project.getProjectDirectory());
        assertThat(project.getProjectDirectory()).exists();

        boolean result = gitService.matchRemote(project.getProjectDirectory());

        assertThat(result).isFalse();
    }

//    @Test
//    public void matchProject_remoteProjectChanged() throws GitException, IOException, GitAPIException {
//        project.setProjectDirectory(Paths.get(PROJECT_DIRECTORY, PROJECT_NAME + "_3"));
//        project1.setProjectDirectory(Paths.get(PROJECT_DIRECTORY, PROJECT_NAME_NEW + "_3"));
//
//
//        assertThat(project.getProjectDirectory()).doesNotExist();
//        gitService.cloneRepository(project.getGitPath(), project.getProjectDirectory());
//        assertThat(project.getProjectDirectory()).exists();
//
//
//        // create a change in remote repository
//        gitService.cloneRepository(project1.getGitPath(), project1.getProjectDirectory());
//        assertThat(GitUtils.localChanges(GitUtils.createGit(project1.getProjectDirectory().toString()))).isFalse();
//        Path newFile = Paths.get(project1.getProjectDirectory().toString(), NEW_FILE);
//        if (Files.exists(newFile)) {
//            Files.delete(newFile);
//        }
//        Files.copy(Paths.get(project1.getProjectDirectory().toString(), POM_XML), newFile);
//        assertThat(GitUtils.localChanges(GitUtils.createGit(project1.getProjectDirectory().toString()))).isTrue();
//        gitService.commitProject(project1.getProjectDirectory(), "to check changes in remote", user);
//
//        // check changes in remote
//        boolean result = gitService.matchRemote(project.getProjectDirectory());
//        assertThat(result).isTrue();
//        assertThat(newFile).exists();
//
//        // clean up
//        Files.delete(newFile);
//        gitService.commitProject(project1.getProjectDirectory(), "clean up changes in remote", user);
//    }
//
//
//    @Test
//    public void commitProject_addNewFile() throws IOException, GitException {
//        project.setProjectDirectory(Paths.get(PROJECT_DIRECTORY, PROJECT_NAME + "_4"));
//        project1.setProjectDirectory(Paths.get(PROJECT_DIRECTORY, PROJECT_NAME_NEW + "_4"));
//        // given
//        Path newFile = Paths.get(project.getProjectDirectory().toString(), NEW_FILE);
//
//        assertThat(project.getProjectDirectory()).doesNotExist();
//        gitService.cloneRepository(project.getGitPath(), project.getProjectDirectory());
//        assertThat(project.getProjectDirectory()).exists();
//        if (Files.exists(newFile)) {
//            Files.delete(newFile);
//        }
//        Files.copy(Paths.get(project.getProjectDirectory().toString(), POM_XML), newFile);
//
//        File newSubDirectory = new File(project.getProjectDirectory().toString(), "sub");
//        if (!newSubDirectory.exists()) {
//            org.eclipse.jgit.util.FileUtils.mkdir(newSubDirectory);
//        }
//        File newFileInSubdir = new File(project.getProjectDirectory().toString(), "sub/a.txt");
//        if (!newFileInSubdir.exists()) {
//            org.eclipse.jgit.util.FileUtils.createNewFile(newFileInSubdir);
//        }
//
//        // when
//        gitService.commitProject(project.getProjectDirectory(), "test commit from GitApi", user);
//
//        // than
//        // clone from git to a new directory
//        assertThat(project1.getProjectDirectory()).doesNotExist();
//        gitService.cloneRepository(project1.getGitPath(), project1.getProjectDirectory());
//        assertThat(project1.getProjectDirectory()).exists();
//
//        newFile = Paths.get(project1.getProjectDirectory().toString(), NEW_FILE);
//        assertThat(newFile).exists();
//
//        newFileInSubdir = new File(project1.getProjectDirectory().toString(), "sub/a.txt");
//        assertThat(newFileInSubdir).exists();
//
//        // clean up
//        Files.delete(newFile);
//        FileUtils.deleteDirectory(newFileInSubdir.getParentFile());
//        gitService.commitProject(project1.getProjectDirectory(), "clean commit from GitApi", new User("testUser", ""));
//    }
//
//    @Test
//    public void commitProject_fileChange() throws IOException, GitException {
//        project.setProjectDirectory(Paths.get(PROJECT_DIRECTORY, PROJECT_NAME + "_5"));
//        project1.setProjectDirectory(Paths.get(PROJECT_DIRECTORY, PROJECT_NAME_NEW + "_5"));
//        // clone repository
//        assertThat(project.getProjectDirectory()).doesNotExist();
//        gitService.cloneRepository(project.getGitPath(), project.getProjectDirectory());
//        assertThat(project.getProjectDirectory()).exists();
//
//        // create backup to clean up changes
//        Path pomXml = Paths.get(project.getProjectDirectory().toString(), POM_XML);
//        Path backupFile = Paths.get(project.getProjectDirectory().getParent().toString(), POM_XML);
//        Files.copy(pomXml, backupFile);
//
//        Files.write(pomXml, "the text".getBytes(), StandardOpenOption.APPEND);
//
//
//        // when
//        gitService.commitProject(project.getProjectDirectory(), "test commit from GitApi", new User("testUser", ""));
//
//        // than
//        // clone from git to a new directory
//
//        assertThat(project1.getProjectDirectory()).doesNotExist();
//        gitService.cloneRepository(project1.getGitPath(), project1.getProjectDirectory());
//        assertThat(project1.getProjectDirectory()).exists();
//
//        pomXml = Paths.get(project1.getProjectDirectory().toString(), POM_XML);
//        assertThat(pomXml).exists();
//
//        assertThat(pomXml.toFile().length()).isNotEqualTo(backupFile.toFile().length());
//
//        // clean up
//        Files.delete(pomXml);
//        Files.copy(backupFile, pomXml);
//        gitService.commitProject(project1.getProjectDirectory(), "clean commit from GitApi", new User("testUser", ""));
//    }
}