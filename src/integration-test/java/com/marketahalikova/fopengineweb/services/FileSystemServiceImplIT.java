package com.marketahalikova.fopengineweb.services;

import com.marketahalikova.fopengineweb.enums.FontStyle;
import com.marketahalikova.fopengineweb.exceptions.FopEngineException;
import com.marketahalikova.fopengineweb.model.Font;
import com.marketahalikova.fopengineweb.model.FontTriplet;
import com.marketahalikova.fopengineweb.model.Project;
import com.marketahalikova.fopengineweb.model.ProjectFileMapper;
import com.marketahalikova.fopengineweb.xml.ConfigurationHandlerImpl;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.aspectj.util.FileUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class FileSystemServiceImplIT {

    public static final Path WORKING_DIRECTORY = Paths.get("target/test-classes");

    public static final Path MASTER_PROJECT = Paths.get("target/test-classes/test-project");
    public static final Path TEST_PROJECT = Paths.get("target/test-classes/working-project");

    public static final String PROJECT_GIT_LINK = "https://github.com/MarketaHalikova/FopEngineWeb.git";
    public static final Path PROJECT_GIT_DIRECTORY = Paths.get(WORKING_DIRECTORY.toString(), "fopengineweb");

    public static final String FONT_DIRECTORY = "fonts";
    public static final String TRIPLET_1_METRICS = "JCarial.xml";
    public static final String TRIPLET_1_FILE1 = "JCarial.pfb";
    public static final String TRIPLET_1_FILE2 = "JCarial.pfm";

    FileSystemServiceImpl fileSystemService;
    ConfigurationHandlerImpl configurationHandlerImpl;
    Project project;

    FontTriplet triplet;
    ProjectFileMapper triplet1_metrics;
    ProjectFileMapper triplet1_file1;
    ProjectFileMapper triplet1_file2;

    @Before
    public void setUp() throws Exception {
        if (Files.exists(TEST_PROJECT)) {
            FileUtils.deleteDirectory(TEST_PROJECT.toFile());
        }
        fileSystemService = new FileSystemServiceImpl();
        fileSystemService.setWorkingDirectory(WORKING_DIRECTORY.toString());
        configurationHandlerImpl = new ConfigurationHandlerImpl();
        project = new Project();
        project.setProjectDirectory(TEST_PROJECT);
        // when
        fileSystemService.deleteProjectDirectory(project);
        FileUtil.copyDir(MASTER_PROJECT.toFile(), TEST_PROJECT.toFile());

        triplet1_metrics = new ProjectFileMapper(TRIPLET_1_METRICS, FONT_DIRECTORY, FONT_DIRECTORY);
        triplet1_file1 = new ProjectFileMapper(TRIPLET_1_FILE1, FONT_DIRECTORY, FONT_DIRECTORY);
        triplet1_file2 = new ProjectFileMapper(TRIPLET_1_FILE2, FONT_DIRECTORY, FONT_DIRECTORY);

        triplet = new FontTriplet();
        triplet.setMetricsFile(triplet1_metrics);

        triplet.addFontFile(triplet1_file1);
        triplet.addFontFile(triplet1_file2);
    }

    @Test
    public void deleteProjectDirectory_shouldDeleteDirectory() throws IOException, FopEngineException {
        // given
        assertThat(TEST_PROJECT).exists();
        // when
        fileSystemService.deleteProjectDirectory(project);
        // then
        assertThat(TEST_PROJECT).doesNotExist();
    }

    @Test(expected = FopEngineException.class)
    public void deleteProjectDirectory_projectDirNotSet_shouldThrowException() throws IOException, FopEngineException {
        project.setProjectDirectory(null);
        // when
        fileSystemService.deleteProjectDirectory(project);
    }

    @Test
    public void deleteProjectDirectory_projectDirNotExists_shouldDoNothing() throws IOException, FopEngineException {
        if (Files.exists(TEST_PROJECT)) {
            FileUtils.deleteDirectory(TEST_PROJECT.toFile());
        }
        assertThat(project.getProjectDirectory()).doesNotExist();
        // when
        fileSystemService.deleteProjectDirectory(project);
    }

    @Test(expected = FopEngineException.class)
    public void createProjectDirectory_gitLinkNull_shouldThrowException() throws FopEngineException {
        fileSystemService.createProjectDirectory(null);
    }

    @Test(expected = FopEngineException.class)
    public void createProjectDirectory_wrongGitUrlFormat_shouldThrowException() throws FopEngineException {
        fileSystemService.createProjectDirectory("c:\test\test.git");
    }

    @Test
    public void createProjectDirectory_createDirectory() throws FopEngineException, IOException {
        if (Files.exists(PROJECT_GIT_DIRECTORY)) {
            FileUtils.deleteDirectory(PROJECT_GIT_DIRECTORY.toFile());
        }
        assertThat(PROJECT_GIT_DIRECTORY).doesNotExist();
        Path result = fileSystemService.createProjectDirectory(PROJECT_GIT_LINK);
        assertThat(PROJECT_GIT_DIRECTORY).exists();
    }

    @Test
    public void deleteFontFromProject() throws Exception {

        String notImportant = "dummy";
        Project project = configurationHandlerImpl.createProject(notImportant, TEST_PROJECT);
        project.setProjectDirectory(TEST_PROJECT);
        assertThat(project.getFontSet()).hasSize(3);
        Optional<Font> fontOpt = project.getFontSet().stream().filter(f -> f.getFontName().equals("JCArialBold")).findAny();
        assertThat(fontOpt).isPresent();

        Font fontForDelete = fontOpt.get();
        assertThat(fontForDelete.getFontTriplets()).hasSize(4);
        fontForDelete.getFontTriplets().forEach(t -> checkFilesExist(t));

        // when
        fileSystemService.deleteFontFromProject(fontForDelete, project);
        // then

        // check font and ist triplets are not deleted
        fontOpt = project.getFontSet().stream().filter(f -> f.getFontName().equals("JCArialBold")).findAny();
        assertThat(fontOpt).isPresent();

        fontForDelete = fontOpt.get();
        assertThat(fontForDelete.getFontTriplets()).hasSize(4);

        // normal - should be deleted
        Optional<FontTriplet> tripletNormalOpt =  fontForDelete.getFontTriplets().stream()
                .filter(t-> t.getFontStyle()==FontStyle.normal).findAny();
        assertThat(tripletNormalOpt).isPresent();
        checkFilesNotExist(tripletNormalOpt.get());

        // bold - is at JCArialBlack - should not be deleted
        Optional<FontTriplet> tripletBoldOpt =  fontForDelete.getFontTriplets().stream()
                .filter(t-> t.getFontStyle()==FontStyle.bold).findAny();
        assertThat(tripletBoldOpt).isPresent();
        checkFilesExist(tripletBoldOpt.get());

        // italic - is twice but at the same font as bolditalic- should be deleted
        Optional<FontTriplet> tripletItalicOpt =  fontForDelete.getFontTriplets().stream()
                .filter(t-> t.getFontStyle()==FontStyle.italic).findAny();
        assertThat(tripletItalicOpt).isPresent();
        checkFilesNotExist(tripletItalicOpt.get());

        // italic - is twice but at the same font as italic - should be deleted
        Optional<FontTriplet> tripletBoldItalicOpt =  fontForDelete.getFontTriplets().stream()
                .filter(t-> t.getFontStyle()==FontStyle.bolditalic).findAny();
        assertThat(tripletBoldItalicOpt).isPresent();
        checkFilesNotExist(tripletBoldItalicOpt.get());
    }


    @Test
    public void deleteTripletFromProject_tripletIsNotUsedMultiple_shouldBeDeleted() throws Exception {
        String notImportant = "dummy";
        Project project = configurationHandlerImpl.createProject(notImportant, TEST_PROJECT);
        project.setProjectDirectory(TEST_PROJECT);
        assertThat(project.getFontSet()).hasSize(3);
        Optional<Font> fontOpt = project.getFontSet().stream().filter(f -> f.getFontName().equals("JCArial")).findAny();
        assertThat(fontOpt).isPresent();
        assertThat(fontOpt.get().getFontTriplets()).hasSize(1);
        FontTriplet tripletForDelete = fontOpt.get().getFontTriplets().iterator().next();
        // files should exist before delete
        checkFilesExist(tripletForDelete);
        // when
        fileSystemService.deleteTripletFromProject(tripletForDelete, project);
        // then
        checkFilesNotExist(tripletForDelete);
        // triplet is not deleted from font
        fontOpt = project.getFontSet().stream().filter(f -> f.getFontName().equals("JCArial")).findAny();
        assertThat(fontOpt).isPresent();
        assertThat(fontOpt.get().getFontTriplets()).hasSize(1);
    }

    @Test
    public void deleteTripletFromProject_tripletIsUsedMultiple_shouldNotBeDeleted() throws Exception {
        String notImportant = "dummy";
        Project project = configurationHandlerImpl.createProject(notImportant, TEST_PROJECT);
        project.setProjectDirectory(TEST_PROJECT);
        assertThat(project.getFontSet()).hasSize(3);
        Optional<Font> fontOpt = project.getFontSet().stream().filter(f -> f.getFontName().equals("JCArialBlack")).findAny();
        assertThat(fontOpt).isPresent();
        assertThat(fontOpt.get().getFontTriplets()).hasSize(2);
        Optional<FontTriplet> tripletForDeleteOpt = fontOpt.get().getFontTriplets().stream().filter(t-> t.getFontStyle()== FontStyle.normal).findAny();
        assertThat(tripletForDeleteOpt).isPresent();
        FontTriplet tripletForDelete = tripletForDeleteOpt.get();
        // files should exist before delete
        checkFilesExist(tripletForDelete);
        // when
        fileSystemService.deleteTripletFromProject(tripletForDelete, project);
        // then
        checkFilesExist(tripletForDelete);
    }

    @Test
    public void tripletWithSameFontFilesExists_tripletNotInProject_shouldReturnFalse() {
        Font font1 = new Font("font1");
        font1.addTriplet(createTriplet(1));
        font1.addTriplet(createTriplet(2));
        project.addFont(font1);
        Font font2 = new Font("font2");
        font2.addTriplet(createTriplet(1));
        font2.addTriplet(createTriplet(2));
        project.addFont(font2);
        assertThat(fileSystemService.isTripletWithSameFontFiles(project, triplet)).isFalse();
    }

    @Test
    public void tripletWithSameFontFilesExists_tripletIsInProject_shouldReturnFalse() {
        Font font1 = new Font("font1");
        font1.addTriplet(createTriplet(1));
        font1.addTriplet(createTriplet(2));
        project.addFont(font1);
        Font font2 = new Font("font2");
        font2.addTriplet(createTriplet(1));
        font2.addTriplet(triplet);
        project.addFont(font2);
        assertThat(fileSystemService.isTripletWithSameFontFiles(project, triplet)).isTrue();
    }

    @Test
    public void tripletWithSameFontFilesExists_tripletInProject_shouldReturnFalse() {
        Font font = new Font("font1");
        font.addTriplet(triplet);
        project.addFont(font);
        assertThat(fileSystemService.isTripletWithSameFontFiles(project, triplet)).isTrue();
    }

    @Test
    public void tripletWithSameFontFilesExists_noFontsInProject_shouldReturnFalse() {
        assertThat(fileSystemService.isTripletWithSameFontFiles(project, triplet)).isFalse();
    }

    @Test
    public void deleteFontFiles_shouldDeleteFiles() throws FopEngineException {
        assertThat(project.getProjectDirectory()).exists();
        assertThat(Paths.get(project.getProjectDirectory().toString(), triplet.getMetricsFile().getFullSource())).exists();
        triplet.getFontFiles().forEach(f -> {
            assertThat(Paths.get(project.getProjectDirectory().toString(), f.getFullSource())).exists();
        });
        fileSystemService.deleteFontFiles(triplet, project.getProjectDirectory());
        assertThat(Paths.get(project.getProjectDirectory().toString(), triplet.getMetricsFile().getFullSource())).doesNotExist();
        triplet.getFontFiles().forEach(f -> {
            assertThat(Paths.get(project.getProjectDirectory().toString(), f.getFullSource())).doesNotExist();
        });
    }

    @Test
    public void deleteFontFiles_filesDoesNotExist_shouldDoNothing() throws FopEngineException {
        fileSystemService.deleteFontFiles(triplet, project.getProjectDirectory());
        assertThat(Paths.get(project.getProjectDirectory().toString(), triplet.getMetricsFile().getFullSource())).doesNotExist();
        triplet.getFontFiles().forEach(f -> {
            assertThat(Paths.get(project.getProjectDirectory().toString(), f.getFullSource())).doesNotExist();
        });
        fileSystemService.deleteFontFiles(triplet, project.getProjectDirectory());
    }

    private FontTriplet createTriplet(int id) {
        FontTriplet tr = new FontTriplet();
        tr.setMetricsFile(new ProjectFileMapper("metrics_" + id, "", "font"));
        tr.addFontFile(new ProjectFileMapper("fontfile_1_" + id, "", "font"));
        tr.addFontFile(new ProjectFileMapper("fontfile_2_" + id, "", "font"));
        return tr;
    }

    private void checkFilesExist(FontTriplet triplet) {
        // then
        assertThat(Paths.get(TEST_PROJECT.toString(), triplet.getMetricsFile().getFullSource())).exists();
        triplet.getFontFiles().forEach(f -> {
            assertThat(Paths.get(project.getProjectDirectory().toString(), f.getFullSource())).exists();
        });
    }

    private void checkFilesNotExist(FontTriplet triplet) {
        // then
        assertThat(Paths.get(TEST_PROJECT.toString(), triplet.getMetricsFile().getFullSource())).doesNotExist();
        triplet.getFontFiles().forEach(f -> {
            assertThat(Paths.get(project.getProjectDirectory().toString(), f.getFullSource())).doesNotExist();
        });
    }
}