package com.marketahalikova.fopengineweb.xml;


import com.marketahalikova.fopengineweb.exceptions.XmlException;
import com.marketahalikova.fopengineweb.model.MavenArtifact;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


public class PomHandlerImplIT {

    static final Path TEST_POM = Paths.get("target/test-classes/pom.xml");
    static final Path TARGET_POM = Paths.get("target/test-classes/pom_saved.xml");
    public static final String PROJECT_GROUP_1 = "de.irs.genera";
    public static final String PROJECT_ARTIFACT_1 = "generator-genera";
    public static final String PROJECT_VERSION_1 = "1.0-SNAPSHOT";
    public static final String NEW_VERSION = "new Version";
    static PomHandlerImpl pomHandler;
    final XPath xPath = XPathFactory.newInstance().newXPath();

    @BeforeClass
    public static void setUpClass() throws Exception {
        pomHandler = new PomHandlerImpl(TEST_POM);
    }

    @Test
    public void setVersion_shouldSaveNewVersionAndNotChangeRestOfXml() throws XmlException {
        pomHandler.setVersion(NEW_VERSION, TARGET_POM);

        Document doc = XmlUtils.readXmlDocument(TARGET_POM);
        assertThat(XmlUtils.getSingleNodeText(doc, "project/version", xPath)).isEqualTo(NEW_VERSION);
        assertThat(XmlUtils.getNodeList(doc, "project/dependencies/dependency", xPath).getLength()).isEqualTo(2);
        assertThat(XmlUtils.getNodeList(doc, "project/properties/*", xPath).getLength()).isEqualTo(5);
        assertThat(XmlUtils.getNodeList(doc, "project/build/plugins/plugin", xPath).getLength()).isEqualTo(7);
        assertThat(XmlUtils.getNodeList(doc, "project/repositories/repository/*", xPath).getLength()).isEqualTo(3);

    }

    @Test
    public void getMavenArtifact() throws XmlException {
        MavenArtifact mavenArtifact = pomHandler.getMavenArtifact();
        assertThat(mavenArtifact.getGroup()).isEqualTo(PROJECT_GROUP_1);
        assertThat(mavenArtifact.getArtifact()).isEqualTo(PROJECT_ARTIFACT_1);
        assertThat(mavenArtifact.getVersion()).isEqualTo(PROJECT_VERSION_1);
    }

    @Test
    public void getDependencies() throws XmlException {
        Set<MavenArtifact> dependencies =  pomHandler.getDependencies();
        assertThat(dependencies).hasSize(2);

        MavenArtifact dependency1 = new MavenArtifact("de.redsix", "pdfcompare", "1.1.33");
        MavenArtifact dependency2 = new MavenArtifact("de.irs.generator", "irs-generator-indesign", "2.01-SNAPSHOT");
        assertThat(dependencies).containsExactlyInAnyOrder(dependency1,dependency2);
    }
}