package com.marketahalikova.fopengineweb.xml;

import com.marketahalikova.fopengineweb.exceptions.XmlException;
import com.marketahalikova.fopengineweb.model.MavenArtifact;
import com.marketahalikova.fopengineweb.model.Project;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.nio.file.Path;
import java.nio.file.Paths;
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
@Component
public class PomHandlerImpl implements PomHandler{

    private static final String POM_FILE = "pom.xml";
    private static final String GROUP_EXPRESSION = "/project/groupId";
    private static final String ARTIFACT_EXPRESSION = "/project/artifactId";
    private static final String VERSION_EXPRESSION = "/project/version";
    private static final String DEPENDENCIES_EXPRESSION = "/project/dependencies/dependency";
    private static final String DESCRIPTION_EXPRESSION = "/project/description";

    private static final XPath xPath = XPathFactory.newInstance().newXPath();
    private Document xmlDocument;



    /**
     * Update project from pom.xml - dependencies, maven artifact
     * Project must contain a project directory
     *
     * @param project
     * @throws XmlException
     */
    public void updateProjectFromPom(Project project) throws XmlException {
        Document xmlDocument = XmlUtils.readXmlDocument(Paths.get(project.getProjectDirectory().toString(), POM_FILE));
        project.setMavenArtifact(this.getMavenArtifact(xmlDocument));
        project.setDependencies(this.getDependencies(xmlDocument));
    }

    /**
     * Update version and dependency from project into pom.xml and save the changes
     * Project must contain a project directory
     *
     * @param project
     * @throws XmlException
     */
    public void updatePomFromProject(Project project) throws XmlException {
        Path pomPath = Paths.get(project.getProjectDirectory().toString(), POM_FILE);
        Document xmlDocument = XmlUtils.readXmlDocument(pomPath);
        if (project.getMavenArtifact() != null && project.getMavenArtifact().getVersion() != null) {
            setVersion(project.getMavenArtifact().getVersion(), xmlDocument);
        }
        if (project.getDescription() != null) {
            setDescription(project.getDescription(), xmlDocument);
        }
        XmlUtils.saveDocument(xmlDocument, pomPath);
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
     * Save description to pom document
     *
     * @param value
     * @param xmlDocument
     * @throws XmlException
     */
    public void setDescription(String value, Document xmlDocument) throws XmlException {
        Optional<Node> node = XmlUtils.getSingleNode(xmlDocument, DESCRIPTION_EXPRESSION, xPath);
        node.orElse(createDescriptionNode(xmlDocument))
                .setTextContent(value);
    }

    /**
     * Create and register a new description node
     * @param xmlDocument
     * @return new description node
     * @throws XmlException
     */
    public Node createDescriptionNode(Document xmlDocument) throws XmlException {
        Node description = xmlDocument.createElement("description");
        XmlUtils.getSingleNode(xmlDocument, "project", xPath).get().appendChild(description);
        return description;
    }

    /**
     * Save version to pom document
     *
     * @param value
     * @param xmlDocument
     * @throws XmlException
     */
    public void setVersion(String value, Document xmlDocument) throws XmlException {
        Optional<Node> node = XmlUtils.getSingleNode(xmlDocument, VERSION_EXPRESSION, xPath);
        node.orElseThrow(() -> new XmlException("Version not found in pom.xml"))
                .setTextContent(value);
    }

    /**
     * Return project maven artifact from pom
     *
     * @return project maven artifact from pom
     * @throws XmlException
     */
    public MavenArtifact getMavenArtifact(Document xmlDocument) throws XmlException {
        String group = XmlUtils.getSingleNodeText(xmlDocument, GROUP_EXPRESSION, xPath);
        String artifact = XmlUtils.getSingleNodeText(xmlDocument, ARTIFACT_EXPRESSION, xPath);
        String version = XmlUtils.getSingleNodeText(xmlDocument, VERSION_EXPRESSION, xPath);
        return new MavenArtifact(group, artifact, version);
    }

    /**
     * Set of project dependencies as set of MavenArtifact.
     *
     * @return Set of dependencies
     * @throws XmlException
     */
    public Set<MavenArtifact> getDependencies(Document xmlDocument) throws XmlException {
        Set<MavenArtifact> dependencies = new LinkedHashSet<>();
        NodeList nodeList = XmlUtils.getNodeList(xmlDocument, DEPENDENCIES_EXPRESSION, xPath);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node dependencyNode = nodeList.item(i);
            String group = XmlUtils.getDirectChildNodeTextByName(dependencyNode, "groupId");
            String artifact = XmlUtils.getDirectChildNodeTextByName(dependencyNode, "artifactId");
            String version = XmlUtils.getDirectChildNodeTextByName(dependencyNode, "version");
            dependencies.add(new MavenArtifact(group, artifact, version));
        }
        return dependencies;
    }}