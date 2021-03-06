package de.irs.fopengine.fopengineweb.xml;

import de.irs.fopengine.fopengineweb.enums.FontStyle;
import de.irs.fopengine.fopengineweb.exceptions.XmlException;
import de.irs.fopengine.fopengineweb.model.*;
import de.irs.fopengine.fopengineweb.xml.generated.Configuration;
import de.irs.fopengine.fopengineweb.xml.generated.File;
import de.irs.fopengine.fopengineweb.xml.generated.Template;
import de.irs.fopengine.fopengineweb.xml.generated.Transformation;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * An implementation for handling with userconfig.xml
 */
@Component
public class ConfigurationHandlerImpl implements  ConfigurationHandler{

    public static final String CONFIGURATION_FILE = "src/main/resources/fopengine/fopengine-configuration.xml";

    /**
     * {@inheritDoc}
     */
    public Project createProject(String gitUrl, Path projectDirectory) throws XmlException {
        Configuration configuration = readConfiguration(Paths.get(projectDirectory.toString(), CONFIGURATION_FILE));

        // new project with gitUrl, projectName, description, MavenArtifact
        Project project = new Project(gitUrl, configuration.getProjectName());
        project.setDescription(configuration.getDescription());
        project.setProjectDirectory(projectDirectory);
        MavenArtifact mavenArtifact = new MavenArtifact(configuration.getGroup(), configuration.getArtifact(), configuration.getVersion());
        project.setMavenArtifact(mavenArtifact);
        project.setUserConfig(new ProjectFileMapper(configuration.getUserconfig().getFile().getFileName(), configuration.getUserconfig().getFile().getSourcePath(), configuration.getUserconfig().getFile().getTargetPath()));

        // jobs
        for (de.irs.fopengine.fopengineweb.xml.generated.Job xmlJob : configuration.getJobs().getJob()) {
            Job job = readXmlJob(xmlJob, configuration);
            project.getJobs().add(job);
        }

        // fonts
        if (Optional.ofNullable(configuration.getFonts()).isPresent()) {
            for (de.irs.fopengine.fopengineweb.xml.generated.Font xmlFont : configuration.getFonts().getFont()) {
                Font font = readXmlFont(xmlFont);
                project.addFont(font);
            }
        }

        return project;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateFonts(Project project) throws XmlException {
        Configuration configuration = readConfiguration(Paths.get(project.getProjectDirectory().toString(), CONFIGURATION_FILE));
        configuration.getFonts().getFont().clear();

        configuration.getFonts().getFont().addAll(project.getFontSet().stream()
                .map(font -> createXmlFont(font)).collect(Collectors.toSet()));

        saveConfiguration(configuration, Paths.get(project.getProjectDirectory().toString(), CONFIGURATION_FILE));
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void updateProjectInXml(Project project) throws XmlException {
        Configuration configuration = readConfiguration(Paths.get(project.getProjectDirectory().toString(), CONFIGURATION_FILE));
        configuration.setDescription(project.getDescription());
        configuration.setVersion(project.getMavenArtifact().getVersion());
        saveConfiguration(configuration, Paths.get(project.getProjectDirectory().toString(), CONFIGURATION_FILE));
    }

    public static Job readXmlJob(de.irs.fopengine.fopengineweb.xml.generated.Job xmlJob,
                                 Configuration configuration) throws XmlException {
        Job job;

        if (xmlJob.getTemplateName() != null) {
            job = getJobTemplate(configuration, xmlJob);
        } else if (xmlJob.getTransformationName() != null) {
            job = getJobTransformation(configuration, xmlJob);
        } else {
            job = new Job(xmlJob.getJobName(), xmlJob.getProcessName());
        }

        if (Optional.ofNullable(xmlJob.getMainSchema()).isPresent()) {
            job.setSchema(createProjectFileMapper(xmlJob.getMainSchema().getFile()));
            if (Optional.ofNullable(xmlJob.getMainSchema().getSchemas()).isPresent()) {
                job.setSchemas(xmlJob.getMainSchema().getSchemas().getFile().stream().map(file -> createProjectFileMapper(file)).collect(Collectors.toSet()));
            }
        }

        return job;
    }

    public static Job getJobTransformation(Configuration configuration,
                                            de.irs.fopengine.fopengineweb.xml.generated.Job xmlJob) throws XmlException {
        Job job;
        job = new JobTransformation(xmlJob.getJobName(), xmlJob.getProcessName(), xmlJob.getTransformationName());
        Transformation xmlTransformation = configuration.getTransformations().getTransformation().stream()
                .filter(transformation -> transformation.getName().equals(xmlJob.getTransformationName()))
                .findAny().orElseThrow(() -> new XmlException(String.format("transformation name %s not found", xmlJob.getTemplateName())));

        if (Optional.ofNullable(xmlTransformation.getMainXsl()).isPresent()) {
            ((JobTransformation) job).setMainXsl(new ProjectFileMapper(xmlTransformation.getMainXsl().getFile().getFileName(),
                    xmlTransformation.getMainXsl().getFile().getSourcePath(), xmlTransformation.getMainXsl().getFile().getTargetPath()));
            if (Optional.ofNullable(xmlTransformation.getMainXsl().getXsls()).isPresent()) {
                ((JobTransformation) job).setXsls(xmlTransformation.getMainXsl().getXsls().getFile().stream()
                        .map(file -> createProjectFileMapper(file))
                        .collect(Collectors.toSet()));
            }

        }
        if (Optional.ofNullable(xmlTransformation.getGraphics()).isPresent()) {
            job.setGraphics(xmlTransformation.getGraphics().getFile().stream()
                    .map(file -> createProjectFileMapper(file))
                    .collect(Collectors.toSet()));
        }
        return job;
    }

    public static Job getJobTemplate(Configuration configuration, de.irs.fopengine.fopengineweb.xml.generated.Job xmlJob) throws XmlException {
        Job job;
        job = new JobTemplate(xmlJob.getJobName(), xmlJob.getProcessName(), xmlJob.getTemplateName());
        Template xmlTemplate = configuration.getTemplates().getTemplate().stream()
                .filter(template -> template.getTemplateName().equals(xmlJob.getTemplateName()))
                .findAny().orElseThrow(() -> new XmlException(String.format("template name %s not found", xmlJob.getTemplateName())));

        if (Optional.ofNullable(xmlTemplate.getMainTemplate()).isPresent()) {
            ((JobTemplate) job).setMainTemplate(new ProjectFileMapper(xmlTemplate.getMainTemplate().getFile().getFileName(),
                    xmlTemplate.getMainTemplate().getFile().getSourcePath(), xmlTemplate.getMainTemplate().getFile().getTargetPath()));
            if (Optional.ofNullable(xmlTemplate.getMainTemplate().getTemplateFiles()).isPresent()) {
                ((JobTemplate) job).setTemplates(xmlTemplate.getMainTemplate().getTemplateFiles().getFile().stream()
                        .map(file -> createProjectFileMapper(file))
                        .collect(Collectors.toSet()));
            }
        }
        job.setGraphics(xmlTemplate.getGraphics().getFile().stream()
                .map(file -> createProjectFileMapper(file))
                .collect(Collectors.toSet()));
        return job;
    }

    public static Font readXmlFont(de.irs.fopengine.fopengineweb.xml.generated.Font xmlFont) {
        Font font = new Font(xmlFont.getFontName());

        for (de.irs.fopengine.fopengineweb.xml.generated.FontTriplet xmlFontTriplet : xmlFont.getFontTriplet()) {
            font.addTriplet(readXmlFontTriplet(xmlFontTriplet));
        }

        return font;
    }


    public static FontTriplet readXmlFontTriplet(de.irs.fopengine.fopengineweb.xml.generated.FontTriplet xmlFontTriplet) {
        FontTriplet fontTriplet = new FontTriplet();
        fontTriplet.setFontStyle(FontStyle.valueOf(xmlFontTriplet.getFontStyle()));
        fontTriplet.setInddName(xmlFontTriplet.getInddName());
        fontTriplet.setInddStyle(xmlFontTriplet.getInddStyle());
        fontTriplet.setMetricsFile(new ProjectFileMapper(xmlFontTriplet.getMetricsFile().getFileName(),
                xmlFontTriplet.getMetricsFile().getSourcePath(), xmlFontTriplet.getMetricsFile().getTargetPath()));
        fontTriplet.setFontFiles(xmlFontTriplet.getFile().stream().map(ConfigurationHandlerImpl::createProjectFileMapper)
                .collect(Collectors.toSet()));
        return fontTriplet;
    }


    public Configuration readConfiguration(Path configurationPath) throws XmlException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Configuration.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (Configuration) unmarshaller.unmarshal(configurationPath.toFile());
        } catch (JAXBException e) {
            throw new XmlException("JAXB fail", e);
        }
    }

    public static ProjectFileMapper createProjectFileMapper(File file) {
        return new ProjectFileMapper(file.getFileName(), file.getSourcePath(), file.getTargetPath());
    }

    public de.irs.fopengine.fopengineweb.xml.generated.Font createXmlFont(Font font) {
        de.irs.fopengine.fopengineweb.xml.generated.Font xmlFont = new de.irs.fopengine.fopengineweb.xml.generated.Font();
        xmlFont.setFontName(font.getFontName());
        List<Object> triplets = font.fontTriplets.stream()
                .map(triplet -> createTriplet(triplet)).collect(Collectors.toList());
        xmlFont.getFontTriplet().addAll(triplets.stream().map(obj -> ((de.irs.fopengine.fopengineweb.xml.generated.FontTriplet) obj)).collect(Collectors.toList()));
        return xmlFont;
    }

    public de.irs.fopengine.fopengineweb.xml.generated.FontTriplet createTriplet(FontTriplet triplet) {
        de.irs.fopengine.fopengineweb.xml.generated.FontTriplet xmlTriplet = new de.irs.fopengine.fopengineweb.xml.generated.FontTriplet();
        xmlTriplet.setFontStyle(triplet.getFontStyle().name());
        xmlTriplet.setInddName(triplet.getInddName());
        xmlTriplet.setInddStyle(triplet.getInddStyle());
        de.irs.fopengine.fopengineweb.xml.generated.FontTriplet.MetricsFile metricsFile
                =  new de.irs.fopengine.fopengineweb.xml.generated.FontTriplet.MetricsFile();
        metricsFile.setFileName(triplet.getMetricsFile().getFileName());
        metricsFile.setSourcePath(triplet.getMetricsFile().getSourcePath());
        metricsFile.setTargetPath(triplet.getMetricsFile().getTargetPath());
        xmlTriplet.setMetricsFile(metricsFile);
        triplet.getFontFiles().forEach(f -> {
            File file = new File();
            file.setFileName(f.getFileName());
            file.setSourcePath(f.getSourcePath());
            file.setTargetPath(f.getTargetPath());
            xmlTriplet.getFile().add(file);
        });
        return xmlTriplet;
    }

    /**
     * Marshal xml configuration
     *
     * @param configurationPath
     * @throws javax.xml.bind.JAXBException
     * @throws java.io.FileNotFoundException
     */
    public void saveConfiguration(Configuration configuration, Path configurationPath) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Configuration.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.marshal(configuration, configurationPath.toFile());
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}