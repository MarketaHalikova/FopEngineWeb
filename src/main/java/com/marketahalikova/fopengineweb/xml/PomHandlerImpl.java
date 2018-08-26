package com.marketahalikova.fopengineweb.xml;

import com.marketahalikova.fopengineweb.exceptions.XmlException;
import com.marketahalikova.fopengineweb.model.MavenArtifact;
import lombok.Getter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Class to handle pom.xml
 * <p>
 * read maven artifact
 * read dependencies
 */
@Getter
public class PomHandlerImpl {

    private static final String GROUP_EXPRESSION = "/project/groupId";
    private static final String ARTIFACT_EXPRESSION = "/project/artifactId";
    private static final String VERSION_EXPRESSION = "/project/version";
    private static final String DEPENDENCIES_EXPRESSION = "/project/dependencies/dependency";

    private final Path pomPath;
    private Document xmlDocument;
    private static final XPath xPath = XPathFactory.newInstance().newXPath();

    public PomHandlerImpl(Path pomPath) throws XmlException {
        this.pomPath = pomPath;
        xmlDocument = XmlUtils.readXmlDocument(pomPath);
    }

    /**
     * Change project version i pom and save it to as a file to targetPath
     *  1. find project version node
     *  2. change version text
     *  3. save document
     * @param value
     * @param targetPath
     * @throws XmlException
     */
    public void setVersion(String value, Path targetPath) throws XmlException {
        Optional<Node> nodeOptional = XmlUtils.getSingleNode(xmlDocument, VERSION_EXPRESSION, xPath);

        if (nodeOptional.isPresent()) {
            nodeOptional.get().setTextContent(value);
            XmlUtils.saveDocument(xmlDocument,targetPath);
        }
    }

    /**
     * Return project maven artifact from pom
     *
     * create new Artifact from project group, artifact, version
     * @return project maven artifact from pom
     * @throws XmlException
     */
    public MavenArtifact getMavenArtifact() throws XmlException {
        String group = XmlUtils.getSingleNodeText(xmlDocument, GROUP_EXPRESSION, xPath);
        String artifact = XmlUtils.getSingleNodeText(xmlDocument, ARTIFACT_EXPRESSION, xPath);
        String version = XmlUtils.getSingleNodeText(xmlDocument, VERSION_EXPRESSION, xPath);
        MavenArtifact mavenArtifact = new MavenArtifact(group, artifact, version);
        return mavenArtifact;
    }

    /**
     * Set of project dependencies as set of MavenArtifact.
     * 1. fint all dependencies
     * 2. create MavenArtifact from every dependency
     * 3. create set of dependencies
     * @return Set of dependencies
     * @throws XmlException
     */
    public Set<MavenArtifact> getDependencies() throws XmlException {
        Set<MavenArtifact> dependencies = new LinkedHashSet<>();

        NodeList nodeList = XmlUtils.getNodeList(xmlDocument, DEPENDENCIES_EXPRESSION, xPath);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node dependencyNode = nodeList.item(i);
            String group = XmlUtils.getDirectChildNodeTextByName(dependencyNode,"groupId");
            String artifact = XmlUtils.getDirectChildNodeTextByName(dependencyNode,"artifactId");
            String version = XmlUtils.getDirectChildNodeTextByName(dependencyNode,"version");
            MavenArtifact mavenArtifact = new MavenArtifact(group, artifact, version);
            dependencies.add(mavenArtifact);
        }
        return dependencies;

    }

}
