package com.marketahalikova.fopengineweb.xml;

import com.marketahalikova.fopengineweb.enums.FileType;
import com.marketahalikova.fopengineweb.enums.FontStyle;
import com.marketahalikova.fopengineweb.enums.ProjectStatus;
import com.marketahalikova.fopengineweb.exceptions.XmlException;
import com.marketahalikova.fopengineweb.model.*;
import com.marketahalikova.fopengineweb.xml.generated.Configuration;
import com.marketahalikova.fopengineweb.xml.generated.File;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;


public class ConfigurationHandlerIT {
    public static final String CONFIGURATION_FILE = "target/test-classes/fopengine/fopengine-configuration.xml";

    static ConfigurationHandlerImpl configurationHandlerImpl;
    static Configuration configuration;

    @BeforeClass
    public static void setUpClass() throws Exception {
        configurationHandlerImpl = new ConfigurationHandlerImpl();
        configuration = configurationHandlerImpl.readConfiguration(Paths.get(CONFIGURATION_FILE));
    }

    @Test
    public void readConfiguration() {
        assertThat(configuration).isNotNull();
    }

    @Test
    public void createProject() throws XmlException {
        Project project = configurationHandlerImpl.createProject("gitPath", Paths.get(CONFIGURATION_FILE));
        assertThat(project.getProjectName()).isEqualTo("testProject");
        assertThat(project.getDescription()).isNullOrEmpty();
        assertThat(project.getMavenArtifact().getArtifact()).isEqualTo("test-genera");
        assertThat(project.getMavenArtifact().getGroup()).isEqualTo("de.irs.fopengine");
        assertThat(project.getMavenArtifact().getVersion()).isEqualTo("0.01");
        assertThat(project.getUserConfig().getFileName()).isEqualTo("userconfig.xml");
        assertThat(project.getUserConfig().getTargetPath()).isEqualTo("conf");
        assertThat(project.getUserConfig().getSourcePath()).isNullOrEmpty();
        assertThat(project.getUserConfig().getFileType()).isEqualByComparingTo(FileType.userconfig);
        assertThat(project.getGitPath()).isEqualTo("gitPath");
        assertThat(project.getJobs()).hasSize(4);
        assertThat(project.getFontSet()).hasSize(5);
        assertThat(project.getProjectStatus()).isEqualTo(ProjectStatus.OK);

    }

    @Test
    public void readXmlJob() throws XmlException {
        Job job1 = configurationHandlerImpl.readXmlJob(configuration.getJobs().getJob().get(0), configuration);
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

    @Test
    public void getJobTransformation() throws XmlException {
        Job job2 = configurationHandlerImpl.readXmlJob(configuration.getJobs().getJob().get(1), configuration);
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

        Job job3 = configurationHandlerImpl.readXmlJob(configuration.getJobs().getJob().get(2), configuration);
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

    @Test
    public void getJobTemplate() throws XmlException {
        Job job4 = configurationHandlerImpl.readXmlJob(configuration.getJobs().getJob().get(3), configuration);
        JobTemplate jobTemplate = (JobTemplate) job4;
        assertThat(jobTemplate.getJobName()).isEqualTo("template");
        assertThat(jobTemplate.getProcessName()).isEqualTo("TEMPLATE_PDF");
        assertThat(jobTemplate.getSchema().getFileName()).isEqualTo("Template.xsd");
        assertThat(jobTemplate.getSchema().getTargetPath()).isEqualTo("schema");
        assertThat(jobTemplate.getSchema().getSourcePath()).isEqualTo("schema");
        assertThat(jobTemplate.getTemplateName()).isEqualTo("template1");
        assertThat(jobTemplate.getSchema().getFileType()).isEqualByComparingTo(FileType.schema);
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

    @Test
    public void readXmlFont() {
        Font font1 = configurationHandlerImpl.readXmlFont(configuration.getFonts().getFont().get(0), configuration);
        assertThat(font1.getFontName()).isEqualTo("arialuni");
        assertThat(font1.getFontTriplets()).hasSize(1);
        Font font2 = configurationHandlerImpl.readXmlFont(configuration.getFonts().getFont().get(1), configuration);
        assertThat(font2.getFontName()).isEqualTo("Arial");
        assertThat(font2.getFontTriplets()).hasSize(3);
        Font font3 = configurationHandlerImpl.readXmlFont(configuration.getFonts().getFont().get(2), configuration);
        assertThat(font3.getFontName()).isEqualTo("ArialBold");
        assertThat(font3.getFontTriplets()).hasSize(3);
        Font font4 = configurationHandlerImpl.readXmlFont(configuration.getFonts().getFont().get(3), configuration);
        assertThat(font4.getFontName()).isEqualTo("JCArialBlack");
        assertThat(font4.getFontTriplets()).hasSize(4);
        Font font5 = configurationHandlerImpl.readXmlFont(configuration.getFonts().getFont().get(4), configuration);
        assertThat(font5.getFontName()).isEqualTo("Helvetica");
        assertThat(font5.getFontTriplets()).hasSize(4);

    }

    @Test
    public void readXmlFontTriplet() {
        Font font1 = configurationHandlerImpl.readXmlFont(
                configuration.getFonts().getFont().stream()
                        .filter(f-> f.getFontName().equals("arialuni"))
                        .findAny().get(),configuration);
       // Font font1 = configurationHandlerImpl.readXmlFont(configuration.getFonts().getFont().get(0), configuration);
        FontTriplet triplet1 = font1.getFontTriplets().iterator().next();
        assertThat(triplet1.getFontStyle()).isEqualByComparingTo(FontStyle.normal);
        assertThat(triplet1.getInddName()).isEqualTo("Arial Unicode MS");
        assertThat(triplet1.getInddStyle()).isEqualTo("Regular");
        assertThat(triplet1.getFontFiles()).hasSize(1);
        ProjectFileMapper mapper = triplet1.getFontFiles().iterator().next();
        assertThat(mapper.getFileName()).isEqualTo("arialuni.ttf");
        assertThat(mapper.getTargetPath()).isEqualTo("fonts");
        assertThat(triplet1.getFontFiles().iterator().next().getSourcePath()).isNullOrEmpty();
        assertThat(triplet1.getFontFiles().iterator().next().getFileType()).isEqualByComparingTo(FileType.font_file);
        assertThat(triplet1.getEmbedFile()).isEqualTo("fonts/arialuni.ttf");
        assertThat(triplet1.getMetricsFile().getFileName()).isEqualTo("arialuni.xml");
        assertThat(triplet1.getMetricsFile().getTargetPath()).isEqualTo("fonts");
        assertThat(triplet1.getMetricsFile().getSourcePath()).isNullOrEmpty();
        assertThat(triplet1.getMetricsFile().getFileType()).isEqualByComparingTo(FileType.metrics_file);

        Font font4 = configurationHandlerImpl.readXmlFont(configuration.getFonts().getFont().get(3), configuration);
        FontTriplet triplet3 = font4.getFontTriplets().stream().filter(triplet -> triplet.getFontStyle() == FontStyle.italic).findAny().get();
       // FontTriplet triplet3 = font4.getFontTriplets().stream().collect(Collectors.toList()).get(2);
        assertThat(triplet3.getFontStyle()).isEqualByComparingTo(FontStyle.italic);
        assertThat(triplet3.getInddName()).isEqualTo("Arial");
        assertThat(triplet3.getInddStyle()).isEqualTo("Black Italic");
        assertThat(triplet3.getFontFiles()).hasSize(1);
        assertThat(triplet3.getFontFiles().iterator().next().getFileName()).isEqualTo("ariblki.ttf");
        assertThat(triplet3.getFontFiles().iterator().next().getTargetPath()).isEqualTo("fonts");
        assertThat(triplet3.getFontFiles().iterator().next().getSourcePath()).isNullOrEmpty();
        assertThat(triplet3.getFontFiles().iterator().next().getFileType()).isEqualByComparingTo(FileType.font_file);
        assertThat(triplet3.getEmbedFile()).isEqualTo("fonts/ariblki.ttf");
        assertThat(triplet3.getMetricsFile().getFileName()).isEqualTo("ariblki.xml");
        assertThat(triplet3.getMetricsFile().getTargetPath()).isEqualTo("fonts");
        assertThat(triplet3.getMetricsFile().getSourcePath()).isNullOrEmpty();
        assertThat(triplet3.getMetricsFile().getFileType()).isEqualByComparingTo(FileType.metrics_file);

    }


    @Test
    public void createProjectFileMapper() {
        File file = new File();
        file.setFileName("fileName");
        file.setSourcePath("sourcePath");
        file.setTargetPath("targetPath");
        ProjectFileMapper projectFileMapper = configurationHandlerImpl.createProjectFileMapper(file);
        assertThat(projectFileMapper.getFileName()).isEqualTo(file.getFileName());
        assertThat(projectFileMapper.getSourcePath()).isEqualTo(file.getSourcePath());
        assertThat(projectFileMapper.getTargetPath()).isEqualTo(file.getTargetPath());
    }
}