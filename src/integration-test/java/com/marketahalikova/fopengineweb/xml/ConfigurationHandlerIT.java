package com.marketahalikova.fopengineweb.xml;

import com.marketahalikova.fopengineweb.enums.FileType;
import com.marketahalikova.fopengineweb.enums.FontStyle;
import com.marketahalikova.fopengineweb.enums.ProjectStatus;
import com.marketahalikova.fopengineweb.exceptions.XmlException;
import com.marketahalikova.fopengineweb.model.*;
import com.marketahalikova.fopengineweb.services.XmlService;
import com.marketahalikova.fopengineweb.xml.generated.Configuration;
import com.marketahalikova.fopengineweb.xml.generated.File;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.FileCopyUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;


public class ConfigurationHandlerIT {
    public static final String PROJECT_DIRECTORY = "target/test-classes";

    static ConfigurationHandlerImpl configurationHandler;
    static Configuration configuration;

    @BeforeClass
    public static void setUpClass() throws Exception {
        configurationHandler = new ConfigurationHandlerImpl();
        configuration = configurationHandler.readConfiguration(Paths.get(PROJECT_DIRECTORY, ConfigurationHandlerImpl.CONFIGURATION_FILE));

    }

    @Test
    public void updateProjectInXml_shouldSaveChanges() throws Exception {
        Path configurationFile = Paths.get(PROJECT_DIRECTORY, ConfigurationHandlerImpl.CONFIGURATION_FILE);
        Path backup = Paths.get(PROJECT_DIRECTORY, "configuration.backup");
        FileCopyUtils.copy(configurationFile.toFile(), backup.toFile());
        Project project = configurationHandler.createProject("gitPath", Paths.get(PROJECT_DIRECTORY));
        checkProjectValues(project, true);

        project.setDescription("new Description");
        project.getMavenArtifact().setVersion("new version");
        // when
        configurationHandler.updateProjectInXml(project);

        // then
        Configuration updatedConfiguration = configurationHandler.readConfiguration(Paths.get(PROJECT_DIRECTORY, ConfigurationHandlerImpl.CONFIGURATION_FILE));
        Project updatedProject = configurationHandler.createProject("gitPath", Paths.get(PROJECT_DIRECTORY));

        // clean up before assertions to have a correct state before other tests in case of assertion to failure
        FileCopyUtils.copy(backup.toFile(), configurationFile.toFile());

        checkProjectValues(updatedProject, false);
        assertThat(updatedConfiguration.getVersion()).isEqualTo("new version");
        assertThat(updatedConfiguration.getDescription()).isEqualTo("new Description");
        checkFonts(updatedConfiguration);
        this.checkFontArialBlack(updatedConfiguration);
        this.checkFontUnicode(updatedConfiguration);
        this.checkSimpleJob(updatedConfiguration);
        this.checkTemplateJob(updatedConfiguration);
        this.checkTransformationJobs(updatedConfiguration);
    }

    @Test
    public void updateFonts() throws Exception {
        // create backup configuration file
        Path configurationFile = Paths.get(PROJECT_DIRECTORY, ConfigurationHandlerImpl.CONFIGURATION_FILE);
        Path backup = Paths.get(PROJECT_DIRECTORY, "configuration.backup");
        FileCopyUtils.copy(configurationFile.toFile(), backup.toFile());

        // prepare font to add
        Font fontToAdd = new Font("added Font");
        FontTriplet newTriplet = new FontTriplet();
        fontToAdd.addTriplet(newTriplet);
        newTriplet.setFontStyle(FontStyle.bold);
        newTriplet.setInddName("new Indd name");
        newTriplet.setInddStyle("new Indd style");
        ProjectFileMapper metricsFile = new ProjectFileMapper("metrics1", "source1", "target1");
        newTriplet.setMetricsFile(metricsFile);
        ProjectFileMapper fontFile1 = new ProjectFileMapper("fontFile1", "sourceFont1", "targetFont1");
        newTriplet.addFontFile(fontFile1);

        // read project from old configuration
        Configuration configuration = configurationHandler.readConfiguration(Paths.get(PROJECT_DIRECTORY, ConfigurationHandlerImpl.CONFIGURATION_FILE));
        Project project = configurationHandler.createProject("gitPath", Paths.get(PROJECT_DIRECTORY));
        // check the state
        checkProjectValues(project, false);
        checkFonts(configuration);
        this.checkFontArialBlack(configuration);
        this.checkFontUnicode(configuration);
        this.checkSimpleJob(configuration);
        this.checkTemplateJob(configuration);
        this.checkTransformationJobs(configuration);

        // prepare fonts for deleting
        Font fontToDelete1 = project.getFontSet().stream().filter(f-> f.getFontName().equals("Arial")).findFirst().get();
        Font fontToDelete2 = project.getFontSet().stream().filter(f-> f.getFontName().equals("Helvetica")).findFirst().get();
        // should be in project
        assertThat(project.getFontSet()).contains(fontToDelete1,fontToDelete2);

        // make changes in project - delete, add fonts
        project.getFontSet().remove(fontToDelete1);
        project.getFontSet().remove(fontToDelete2);
        project.addFont(fontToAdd);

        // when - update fonts
        configurationHandler.updateFonts(project);

        // then
        // reload file
        Configuration updatedConfiguration = configurationHandler.readConfiguration(Paths.get(PROJECT_DIRECTORY, ConfigurationHandlerImpl.CONFIGURATION_FILE));
        Project updatedProject = configurationHandler.createProject("gitPath", Paths.get(PROJECT_DIRECTORY));

        // clean up before assertions to have a correct state before other tests in case of assertion to failure
        FileCopyUtils.copy(backup.toFile(), configurationFile.toFile());

        // nothing except fonts should be changed
        checkProjectValues(updatedProject, true);
        this.checkSimpleJob(updatedConfiguration);
        this.checkTemplateJob(updatedConfiguration);
        this.checkTransformationJobs(updatedConfiguration);

        this.checkFontUnicode(updatedConfiguration);
        this.checkFontArialBlack(updatedConfiguration);

        assertThat(updatedConfiguration.getFonts().getFont()).hasSize(4);

        assertThat(updatedConfiguration.getFonts().getFont().stream()
                .filter(f-> f.getFontName().equals("Arial"))
                .findAny()).isNotPresent();
        assertThat(updatedConfiguration.getFonts().getFont().stream()
                .filter(f-> f.getFontName().equals("Helvetica"))
                .findAny()).isNotPresent();

        Font newFont = configurationHandler.readXmlFont(updatedConfiguration.getFonts().getFont().stream()
                .filter(f-> f.getFontName().equals("added Font"))
                .findAny().get());

        assertThat(newFont.getFontName()).isEqualTo(fontToAdd.getFontName());
        assertThat(newFont.getFontTriplets()).hasSize(1);
        FontTriplet savedTriplet = newFont.getFontTriplets().iterator().next();
        assertThat(savedTriplet.getFontStyle()).isEqualTo(newTriplet.getFontStyle());
        assertThat(savedTriplet.getInddName()).isEqualTo(newTriplet.getInddName());
        assertThat(savedTriplet.getInddStyle()).isEqualTo(newTriplet.getInddStyle());
        assertThat(savedTriplet.getEmbedFileSource()).isEqualTo(newTriplet.getEmbedFileSource());
        assertThat(savedTriplet.fontStyle).isEqualTo(newTriplet.fontStyle);
        assertThat(savedTriplet.fontStyle).isEqualTo(newTriplet.fontStyle);
        assertThat(savedTriplet.getMetricsFile()).isEqualTo(newTriplet.getMetricsFile());
        assertThat(savedTriplet.getFontFiles().iterator().next()).isEqualTo(newTriplet.getFontFiles().iterator().next());
    }


    @Test
    public void readConfiguration() {
        assertThat(configuration).isNotNull();
    }

    @Test
    public void createProject() throws XmlException {
        Project project = configurationHandler.createProject("gitPath", Paths.get(PROJECT_DIRECTORY));
        checkProjectValues(project, true);

    }


    @Test
    public void readXmlJob() throws XmlException {
        checkSimpleJob(configuration);
    }

    @Test
    public void getJobTransformation() throws XmlException {
        checkTransformationJobs(configuration);

    }

    @Test
    public void getJobTemplate() throws XmlException {
        checkTemplateJob(configuration);
    }



    @Test
    public void readXmlFont() {
        Configuration conf = configuration;
        checkFonts(conf);
    }



    @Test
    public void readXmlFontTriplet() {
        checkFontUnicode(configuration);
        checkFontArialBlack(configuration);
    }

    @Test
    public void createProjectFileMapper() {
        File file = new File();
        file.setFileName("fileName");
        file.setSourcePath("sourcePath");
        file.setTargetPath("targetPath");
        ProjectFileMapper projectFileMapper = configurationHandler.createProjectFileMapper(file);
        assertThat(projectFileMapper.getFileName()).isEqualTo(file.getFileName());
        assertThat(projectFileMapper.getSourcePath()).isEqualTo(file.getSourcePath());
        assertThat(projectFileMapper.getTargetPath()).isEqualTo(file.getTargetPath());
    }

    private void checkFonts(Configuration conf) {
        assertThat(conf.getFonts().getFont()).hasSize(5);
        Font font1 = configurationHandler.readXmlFont(conf.getFonts().getFont().stream()
                .filter(f-> f.getFontName().equals("arialuni"))
                .findAny().get());
        assertThat(font1.getFontTriplets()).hasSize(1);

        Font font2 = configurationHandler.readXmlFont(conf.getFonts().getFont().stream()
                .filter(f-> f.getFontName().equals("Arial"))
                .findAny().get());
        assertThat(font2.getFontTriplets()).hasSize(3);

        Font font3 = configurationHandler.readXmlFont(conf.getFonts().getFont().stream()
                .filter(f-> f.getFontName().equals("ArialBold"))
                .findAny().get());
        assertThat(font3.getFontTriplets()).hasSize(3);

        Font font4 = configurationHandler.readXmlFont(conf.getFonts().getFont().stream()
                .filter(f-> f.getFontName().equals("JCArialBlack"))
                .findAny().get());
        assertThat(font4.getFontTriplets()).hasSize(4);

        Font font5 = configurationHandler.readXmlFont(conf.getFonts().getFont().stream()
                .filter(f-> f.getFontName().equals("Helvetica"))
                .findAny().get());
        assertThat(font5.getFontTriplets()).hasSize(4);
    }

    private void checkFontArialBlack(Configuration conf) {
        ProjectFileMapper firstFile;
        Font font4 = configurationHandler.readXmlFont(conf.getFonts().getFont().stream()
                .filter(f-> f.getFontName().equals("JCArialBlack"))
                .findAny()
                .get());
        assertThat(font4.getFontName()).isEqualTo("JCArialBlack");
        FontTriplet triplet3 = font4.getFontTriplets().stream().filter(t -> t.getFontStyle() == FontStyle.italic).findFirst().get();
        assertThat(triplet3.getFontStyle()).isEqualByComparingTo(FontStyle.italic);
        assertThat(triplet3.getInddName()).isEqualTo("Arial");
        assertThat(triplet3.getInddStyle()).isEqualTo("Black Italic");
        assertThat(triplet3.getFontFiles()).hasSize(1);
        firstFile = triplet3.getFontFiles().iterator().next();
        assertThat(firstFile.getFileName()).isEqualTo("ariblki.ttf");
        assertThat(firstFile.getTargetPath()).isEqualTo("fonts");
        assertThat(firstFile.getSourcePath()).isEqualTo("fonts");
        assertThat(firstFile.getFileType()).isEqualByComparingTo(FileType.font_file);
        assertThat(triplet3.getEmbedFileSource()).isEqualTo(Paths.get(XmlService.PROJECT_RESOURCES_DIRECTORY,"fonts/ariblki.ttf").toString());
        assertThat(triplet3.getEmbedFileTarget()).isEqualTo(Paths.get("fonts/ariblki.ttf").toString());
        assertThat(triplet3.getMetricsFile().getFileName()).isEqualTo("ariblki.xml");
        assertThat(triplet3.getMetricsFile().getTargetPath()).isEqualTo("fonts");
        assertThat(triplet3.getMetricsFile().getSourcePath()).isEqualTo("fonts");
        assertThat(triplet3.getMetricsFile().getFileType()).isEqualByComparingTo(FileType.metrics_file);
    }

    private void checkFontUnicode(Configuration conf) {
        Font unicode = configurationHandler.readXmlFont(conf.getFonts().getFont().stream()
                .filter(f-> f.getFontName().equals("arialuni"))
                .findAny()
                .get());
        FontTriplet triplet1 = unicode.getFontTriplets().iterator().next();
        assertThat(triplet1.getFontStyle()).isEqualByComparingTo(FontStyle.normal);
        assertThat(triplet1.getInddName()).isEqualTo("Arial Unicode MS");
        assertThat(triplet1.getInddStyle()).isEqualTo("Regular");
        assertThat(triplet1.getFontFiles()).hasSize(1);
        ProjectFileMapper firstFile = triplet1.getFontFiles().iterator().next();
        assertThat(firstFile.getFileName()).isEqualTo("arialuni.ttf");
        assertThat(firstFile.getTargetPath()).isEqualTo("fonts");
        assertThat(firstFile.getSourcePath()).isEqualTo("fonts");
        assertThat(firstFile.getFileType()).isEqualByComparingTo(FileType.font_file);
        assertThat(triplet1.getEmbedFileSource())
                .isEqualTo(Paths.get(XmlService.PROJECT_RESOURCES_DIRECTORY,"fonts/arialuni.ttf").toString());
        assertThat(triplet1.getEmbedFileTarget()).isEqualTo(Paths.get("fonts/arialuni.ttf").toString());
        assertThat(triplet1.getMetricsFile().getFileName()).isEqualTo("arialuni.xml");
        assertThat(triplet1.getMetricsFile().getTargetPath()).isEqualTo("fonts");
        assertThat(triplet1.getMetricsFile().getSourcePath()).isEqualTo("fonts");
        assertThat(triplet1.getMetricsFile().getFileType()).isEqualByComparingTo(FileType.metrics_file);
    }

    private void checkSimpleJob(Configuration conf) throws XmlException {
        Job job1 = configurationHandler.readXmlJob(conf.getJobs().getJob().get(0), conf);
        assertThat(job1.getJobName()).isEqualTo("genera_idml");
        assertThat(job1.getProcessName()).isEqualTo("GENERATE_IDML");
        assertThat(job1.getSchema().getFileName()).isEqualTo("Genera5DataDynamic.xsd");
        assertThat(job1.getSchema().getTargetPath()).isEqualTo("schema");
        assertThat(job1.getSchema().getSourcePath()).isEqualTo("schema");
        assertThat(job1.getSchema().getFileType()).isEqualByComparingTo(FileType.schema);
        assertThat(job1.getSchemas()).hasSize(1);
        assertThat(job1.getSchemas().iterator().next().getFileName()).isEqualTo("Genera5HtmlText.xsd");
        assertThat(job1.getSchemas().iterator().next().getFileType()).isEqualByComparingTo(FileType.imported_schemas);
        assertThat(job1.getSchemas().iterator().next().getFileName()).isEqualTo("Genera5HtmlText.xsd");
        assertThat(job1.getGraphics()).isNullOrEmpty();
    }

    private void checkTransformationJobs(Configuration conf) throws XmlException {
        Job job2 = configurationHandler.readXmlJob(conf.getJobs().getJob().get(1), conf);
        JobTransformation jobTransformation = (JobTransformation) job2;
        assertThat(jobTransformation.getJobName()).isEqualTo("xsl_fo");
        assertThat(jobTransformation.getProcessName()).isEqualTo("GENERATE_XSL_FO");
        assertThat(jobTransformation.getSchema().getFileName()).isEqualTo("Genera5DataDynamic.xsd");
        assertThat(jobTransformation.getSchema().getTargetPath()).isEqualTo("schema");
        assertThat(jobTransformation.getSchema().getSourcePath()).isEqualTo("schema");
        assertThat(jobTransformation.getSchema().getFileType()).isEqualByComparingTo(FileType.schema);
        assertThat(jobTransformation.getTransformationName()).isEqualTo("genera");
        assertThat(jobTransformation.getSchemas()).hasSize(2);
        assertThat(jobTransformation.getSchemas().iterator().next().getFileType()).isEqualByComparingTo(FileType.imported_schemas);
        assertThat(jobTransformation.getGraphics()).isNullOrEmpty();
        assertThat(jobTransformation.getMainXsl().getFileName()).isEqualTo("genera5.xsl");
        assertThat(jobTransformation.getMainXsl().getTargetPath()).isEqualTo("conf");
        assertThat(jobTransformation.getMainXsl().getSourcePath()).isEqualTo("conf");
        assertThat(jobTransformation.getMainXsl().getFileType()).isEqualByComparingTo(FileType.xsl);
        assertThat(jobTransformation.getXsls()).hasSize(2);
        assertThat(jobTransformation.getXsls().iterator().next().getFileType()).isEqualByComparingTo(FileType.imported_xsls);

        Job job3 = configurationHandler.readXmlJob(conf.getJobs().getJob().get(2), conf);
        JobTransformation jobTransformation2 = (JobTransformation) job3;
        assertThat(jobTransformation2.getJobName()).isEqualTo("xsl_fo");
        assertThat(jobTransformation2.getProcessName()).isEqualTo("GENERATE_XSL_FO");
        assertThat(jobTransformation2.getSchema()).isNull();
        assertThat(jobTransformation2.getTransformationName()).isEqualTo("genera2");
        assertThat(jobTransformation2.getSchemas()).hasSize(0);
        assertThat(jobTransformation2.getGraphics()).hasSize(2);
        assertThat(jobTransformation2.getGraphics().iterator().next().getFileName()).isEqualTo("img1.jpg");
        assertThat(jobTransformation2.getMainXsl().getFileName()).isEqualTo("genera5.xsl");
        assertThat(jobTransformation2.getMainXsl().getTargetPath()).isEqualTo("conf");
        assertThat(jobTransformation2.getMainXsl().getSourcePath()).isEqualTo("conf");
        assertThat(jobTransformation2.getXsls()).hasSize(0);
    }

    /**
     * Check project values. If checkChangeable=false, the version and description are not checked.
     * It is used when changes in project are made
     * @param project
     * @param checkChangeable
     */
    private void checkProjectValues(Project project, boolean checkChangeable) {
        assertThat(project.getProjectName()).isEqualTo("testProject");
        assertThat(project.getMavenArtifact().getArtifact()).isEqualTo("test-genera");
        assertThat(project.getMavenArtifact().getGroup()).isEqualTo("de.irs.fopengine");
        if (checkChangeable) {
            assertThat(project.getMavenArtifact().getVersion()).isEqualTo("0.01");
            assertThat(project.getDescription()).isNullOrEmpty();
        }
        assertThat(project.getUserConfig().getFileName()).isEqualTo("userconfig.xml");
        assertThat(project.getUserConfig().getTargetPath()).isEqualTo("conf");
        assertThat(project.getUserConfig().getSourcePath()).isNullOrEmpty();
        assertThat(project.getUserConfig().getFileType()).isEqualByComparingTo(FileType.userconfig);
        assertThat(project.getGitPath()).isEqualTo("gitPath");
        assertThat(project.getJobs()).hasSize(4);
        assertThat(project.getProjectStatus()).isEqualTo(ProjectStatus.OK);
    }

    private void checkTemplateJob(Configuration conf) throws XmlException {
        Job job4 = configurationHandler.readXmlJob(conf.getJobs().getJob().get(3), conf);
        JobTemplate jobTemplate = (JobTemplate) job4;
        assertThat(jobTemplate.getJobName()).isEqualTo("template");
        assertThat(jobTemplate.getProcessName()).isEqualTo("TEMPLATE_PDF");
        assertThat(jobTemplate.getSchema().getFileName()).isEqualTo("Template.xsd");
        assertThat(jobTemplate.getSchema().getTargetPath()).isEqualTo("schema");
        assertThat(jobTemplate.getSchema().getSourcePath()).isEqualTo("schema");
        assertThat(jobTemplate.getTemplateName()).isEqualTo("template1");
        assertThat(jobTemplate.getSchema().getFileType()).isSameAs(FileType.schema);
        assertThat(jobTemplate.getSchemas()).hasSize(0);
        assertThat(jobTemplate.getGraphics()).hasSize(2);
        assertThat(jobTemplate.getGraphics().iterator().next().getFileType()).isEqualByComparingTo(FileType.graphics);
        assertThat(jobTemplate.getMainTemplate().getFileName()).isEqualTo("template1.xml");
        assertThat(jobTemplate.getMainTemplate().getTargetPath()).isEqualTo("templates");
        assertThat(jobTemplate.getMainTemplate().getSourcePath()).isEqualTo("templates");
        assertThat(jobTemplate.getMainTemplate().getFileType()).isEqualByComparingTo(FileType.template);
        assertThat(jobTemplate.getTemplates()).hasSize(2);
        assertThat(jobTemplate.getTemplates().iterator().next().getFileType()).isEqualByComparingTo(FileType.imported_templates);
    }
}