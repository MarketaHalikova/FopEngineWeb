package com.marketahalikova.fopengineweb.xml;


import com.marketahalikova.fopengineweb.exceptions.XmlException;
import com.marketahalikova.fopengineweb.model.MavenArtifact;
import com.marketahalikova.fopengineweb.model.Project;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.FileCopyUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


public class PomHandlerImplIT {

    static final Path PROJECT_DIRECTORY = Paths.get("target/test-classes");
    static final Path TEST_POM = Paths.get("target/test-classes/pom.xml");
    static final Path TARGET_POM = Paths.get("target/test-classes/pom_saved.xml");
    public static final String PROJECT_GROUP_1 = "de.irs.genera";
    public static final String PROJECT_ARTIFACT_1 = "generator-genera";
    public static final String PROJECT_VERSION_1 = "1.0-SNAPSHOT";
    public static final String NEW_VERSION = "new Version";
    public static final String NEW_DESCRIPTION = "new description";
    public static final String OLD_DESCRIPTION = "old description";
    private static PomHandlerImpl pomHandler;
    private final XPath xPath = XPathFactory.newInstance().newXPath();
    Document document;

    @Before
    public void setUp() throws Exception {
        FileCopyUtils.copy(TEST_POM.toFile(), TARGET_POM.toFile());
        pomHandler = new PomHandlerImpl();

        document = XmlUtils.readXmlDocument(TARGET_POM);
    }

    @Test
    public void updateProjectFromPom() throws XmlException {
        Project project = new Project();
        project.setProjectDirectory(PROJECT_DIRECTORY);
        pomHandler.updateProjectFromPom(project);

        assertThat(project.getMavenArtifact()).isNotNull();
        assertThat(project.getMavenArtifact().getGroup()).isEqualTo(PROJECT_GROUP_1);
        assertThat(project.getMavenArtifact().getArtifact()).isEqualTo(PROJECT_ARTIFACT_1);
        assertThat(project.getMavenArtifact().getVersion()).isEqualTo(PROJECT_VERSION_1);
        assertThat(project.getMavenArtifact().getVersion()).isEqualTo(PROJECT_VERSION_1);

        MavenArtifact dependency1= new MavenArtifact("de.redsix", "pdfcompare", "1.1.33");
        MavenArtifact dependency2= new MavenArtifact("de.irs.generator", "irs-generator-indesign", "2.01-SNAPSHOT");
        assertThat(project.getDependencies()).containsExactlyInAnyOrder(dependency1, dependency2);

        assertThat(project.getDescription()).isNull();
    }

    @Test
    public void updatePomFromProject() throws Exception {
        Project project = new Project();
        project.setProjectDirectory(PROJECT_DIRECTORY);
        MavenArtifact mavenArtifact = new MavenArtifact("xxx", "yyy", NEW_VERSION);
        project.setMavenArtifact(mavenArtifact);
        project.setDescription(NEW_DESCRIPTION);

        pomHandler.updatePomFromProject(project);

        document = XmlUtils.readXmlDocument(TEST_POM);
        assertThat(XmlUtils.getSingleNodeText(document, "project/version", xPath)).isEqualTo(NEW_VERSION);
        assertThat(XmlUtils.getSingleNodeText(document, "project/groupId", xPath)).isEqualTo(PROJECT_GROUP_1);
        assertThat(XmlUtils.getSingleNodeText(document, "project/artifactId", xPath)).isEqualTo(PROJECT_ARTIFACT_1);
        assertThat(XmlUtils.getNodeList(document, "project/dependencies/dependency", xPath).getLength()).isEqualTo(2);
        assertThat(XmlUtils.getNodeList(document, "project/properties/*", xPath).getLength()).isEqualTo(5);
        assertThat(XmlUtils.getNodeList(document, "project/build/plugins/plugin", xPath).getLength()).isEqualTo(7);
        assertThat(XmlUtils.getNodeList(document, "project/repositories/repository/*", xPath).getLength()).isEqualTo(3);
        assertThat(XmlUtils.getSingleNodeText(document, "project/description", xPath)).isEqualTo(NEW_DESCRIPTION);
        FileCopyUtils.copy(TARGET_POM.toFile(), TEST_POM.toFile());
    }

    @Test
    public void setVersion_shouldSaveNewVersionAndNotChangeRestOfXml() throws XmlException {
        pomHandler.setVersion(NEW_VERSION, document);
        XmlUtils.saveDocument(document, TARGET_POM);
        document = XmlUtils.readXmlDocument(TARGET_POM);
        assertThat(XmlUtils.getSingleNodeText(document, "project/version", xPath)).isEqualTo(NEW_VERSION);
        assertThat(XmlUtils.getNodeList(document, "project/dependencies/dependency", xPath).getLength()).isEqualTo(2);
        assertThat(XmlUtils.getNodeList(document, "project/properties/*", xPath).getLength()).isEqualTo(5);
        assertThat(XmlUtils.getNodeList(document, "project/build/plugins/plugin", xPath).getLength()).isEqualTo(7);
        assertThat(XmlUtils.getNodeList(document, "project/repositories/repository/*", xPath).getLength()).isEqualTo(3);
    }

    @Test
    public void getMavenArtifact() throws XmlException {
        MavenArtifact mavenArtifact = pomHandler.getMavenArtifact(document);
        assertThat(mavenArtifact.getGroup()).isEqualTo(PROJECT_GROUP_1);
        assertThat(mavenArtifact.getArtifact()).isEqualTo(PROJECT_ARTIFACT_1);
        assertThat(mavenArtifact.getVersion()).isEqualTo(PROJECT_VERSION_1);
    }

    @Test
    public void getDependencies() throws XmlException {
        Set<MavenArtifact> dependencies =  pomHandler.getDependencies(document);
        assertThat(dependencies).hasSize(2);

        MavenArtifact dependency1 = new MavenArtifact("de.redsix", "pdfcompare", "1.1.33");
        MavenArtifact dependency2 = new MavenArtifact("de.irs.generator", "irs-generator-indesign", "2.01-SNAPSHOT");
        assertThat(dependencies).containsExactlyInAnyOrder(dependency1,dependency2);
    }

    @Test
    public void setDescription_createNewElement()  throws XmlException {

        assertThat(XmlUtils.getSingleNode(document, "project/description", xPath)).isNotPresent();

        pomHandler.setDescription(NEW_DESCRIPTION, document);
        XmlUtils.saveDocument(document, TARGET_POM);

        document = XmlUtils.readXmlDocument(TARGET_POM);
        assertThat(XmlUtils.getSingleNodeText(document, "project/description", xPath)).isEqualTo(NEW_DESCRIPTION);
        assertThat(XmlUtils.getNodeList(document, "project/dependencies/dependency", xPath).getLength()).isEqualTo(2);
        assertThat(XmlUtils.getNodeList(document, "project/properties/*", xPath).getLength()).isEqualTo(5);
        assertThat(XmlUtils.getNodeList(document, "project/build/plugins/plugin", xPath).getLength()).isEqualTo(7);
        assertThat(XmlUtils.getNodeList(document, "project/repositories/repository/*", xPath).getLength()).isEqualTo(3);

    }

    @Test
    public void setDescription_updateElement()  throws Exception {
        if(Files.exists(TARGET_POM)) {
            Files.delete(TARGET_POM);
        }
        Node project = XmlUtils.getSingleNode(document, "project", xPath).get();
        Node description  = document.createElement("description");

        Node newNode = project.appendChild(description);
        newNode.setTextContent(OLD_DESCRIPTION);
        XmlUtils.saveDocument(document, TARGET_POM);

        document = XmlUtils.readXmlDocument(TARGET_POM);
        assertThat(XmlUtils.getSingleNode(document, "project/description", xPath)).isPresent();
        assertThat(XmlUtils.getSingleNodeText(document, "project/description", xPath)).isEqualTo(OLD_DESCRIPTION);

        pomHandler.setDescription(NEW_DESCRIPTION, document);
        XmlUtils.saveDocument(document, TARGET_POM);
        document = XmlUtils.readXmlDocument(TARGET_POM);
        assertThat(XmlUtils.getSingleNodeText(document, "project/description", xPath)).isEqualTo(NEW_DESCRIPTION);
        assertThat(XmlUtils.getNodeList(document, "project/dependencies/dependency", xPath).getLength()).isEqualTo(2);
        assertThat(XmlUtils.getNodeList(document, "project/properties/*", xPath).getLength()).isEqualTo(5);
        assertThat(XmlUtils.getNodeList(document, "project/build/plugins/plugin", xPath).getLength()).isEqualTo(7);
        assertThat(XmlUtils.getNodeList(document, "project/repositories/repository/*", xPath).getLength()).isEqualTo(3);

    }
}