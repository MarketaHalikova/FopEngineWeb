package de.irs.fopengine.fopengineweb.xml;

import de.irs.fopengine.fopengineweb.exceptions.XmlException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


public class XmlUtilsTest {

    public static final String XML_STRING = "<project xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
            "         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
            "         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n" +
            "    <modelVersion>4.0.0</modelVersion>\n" +
            "    <groupId>de.irs.genera</groupId>\n" +
            "    <artifactId>generator-genera</artifactId>\n" +
            "    <version>1.0-SNAPSHOT</version>\n" +
            "    <dependencies>\n" +
            "        <!-- https://mvnrepository.com/artifact/de.redsix/pdfcompare -->\n" +
            "        <dependency>\n" +
            "            <groupId>de.redsix</groupId>\n" +
            "            <artifactId>pdfcompare</artifactId>\n" +
            "            <version>1.1.33</version>\n" +
            "        </dependency>  " +
            "        <dependency>\n" +
            "            <groupId>de.irs.generator</groupId>\n" +
            "            <artifactId>irs-generator-indesign</artifactId>\n" +
            "            <version>2.01-SNAPSHOT</version>\n" +
            "        </dependency> " +
            "</dependencies>" +
            "</project>"
            ;
    public static final Path TEST_XML_PATH = Paths.get("target/test-classes/test.xml");
    public static final String PROJECT_GROUP_EXPRESSION = "/project/groupId";
    public static final String DEPENDENCIES_EXPRESSION = "/project/dependencies/dependency";
    public static final String SECOND_DEPENDENCY_EXPRESSION = "/project/dependencies/dependency[2]";
    public static final String ARTIFACT_ID_NAME = "artifactId";
    public static final String GROUP_ID_NAME = "groupId";
    public static final String ARTIFACT_ID_VALUE = "irs-generator-indesign";
    public static final String PROJECT_GROUP_VALUE = "de.irs.genera";
    public static final Path TARGET_FILE = Paths.get("saved.xml");

    static Document document;
    final XPath xPath = XPathFactory.newInstance().newXPath();

    @BeforeClass
    public static void setUpClass() throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(XML_STRING.getBytes());
        document = db.parse(inputStream);
        inputStream.close();
    }

    @Test
    public void getNodeList() throws XmlException {
        NodeList nodeList = XmlUtils.getNodeList(document, PROJECT_GROUP_EXPRESSION , xPath);
        assertThat(nodeList.getLength()).isEqualTo(1);
        Node node = nodeList.item(0);
        assertThat(node).isNotNull();
        assertThat(node.getNodeName()).isEqualTo(GROUP_ID_NAME);
        assertThat(node.getTextContent()).isEqualTo(PROJECT_GROUP_VALUE);

        nodeList = XmlUtils.getNodeList(document, DEPENDENCIES_EXPRESSION, xPath);
        assertThat(nodeList.getLength()).isEqualTo(2);

    }

    @Test
    public void getNodeList_nodeNotExists_shouldReturnEmptyNodeList() throws XmlException {
        NodeList nodeList = XmlUtils.getNodeList(document, "blabla" , xPath);
        assertThat(nodeList.getLength()).isEqualTo(0);
    }

    @Test
    public void getSingleNodeValue() throws XmlException {
        String result = XmlUtils.getSingleNodeText(document, PROJECT_GROUP_EXPRESSION , xPath);
        assertThat(result).isEqualTo(PROJECT_GROUP_VALUE);
    }

    @Test
    public void getSingleNodeValue_nodeNotExists() throws XmlException {
        String result = XmlUtils.getSingleNodeText(document, "blabla" , xPath);
        assertThat(result).isEmpty();
    }

    @Test
    public void readXmlDocumentFromInputStream() throws XmlException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(XML_STRING.getBytes());
        Document doc = XmlUtils.readXmlDocument(inputStream);
        assertThat(doc).isNotNull();
    }

    @Test(expected = XmlException.class)
    public void readXmlDocumentFromInputStream_errorInXml() throws XmlException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("blabla".getBytes());
        Document doc = XmlUtils.readXmlDocument(inputStream);
    }

    @Test
    public void readXmlDocumentFromPath() throws XmlException {
        Document doc = XmlUtils.readXmlDocument(TEST_XML_PATH);
        assertThat(doc).isNotNull();
    }

    @Test(expected = XmlException.class)
    public void readXmlDocumentFromPath_fileNotFound() throws XmlException {
        Document doc = XmlUtils.readXmlDocument(Paths.get("blabla"));
        assertThat(doc).isNotNull();
    }


    @Test
    public void saveDocument() throws XmlException, IOException {

        if (Files.exists(TARGET_FILE)) {
            Files.delete(TARGET_FILE);
        }
        XmlUtils.saveDocument(document, TARGET_FILE);

        Document doc = XmlUtils.readXmlDocument(TARGET_FILE);
        assertThat(doc).isNotNull();
        String result = XmlUtils.getSingleNodeText(document, PROJECT_GROUP_EXPRESSION , xPath);
        assertThat(result).isEqualTo(PROJECT_GROUP_VALUE);
        if (Files.exists(TARGET_FILE)) {
            Files.delete(TARGET_FILE);
        }
    }

    @Test
    public void getDirectChildNodeValueByName() throws XmlException {
        Node node = XmlUtils.getSingleNode(document, SECOND_DEPENDENCY_EXPRESSION, xPath).get();
        String result = XmlUtils.getDirectChildNodeTextByName(node, ARTIFACT_ID_NAME);
        assertThat(result).isEqualTo(ARTIFACT_ID_VALUE);
    }

    @Test
    public void getDirectChildNodeValueByName_childNotExists() throws XmlException {
        Node node = XmlUtils.getSingleNode(document, SECOND_DEPENDENCY_EXPRESSION, xPath).get();
        String result = XmlUtils.getDirectChildNodeTextByName(node, "blaBla");
        assertThat(result).isEqualTo("");
    }

    @Test
    public void getDirectChildNodeByName() throws XmlException {
        Node node = XmlUtils.getSingleNode(document, SECOND_DEPENDENCY_EXPRESSION, xPath).get();
        Optional<Node> result = XmlUtils.getDirectChildNodeByName(node, ARTIFACT_ID_NAME);
        assertThat(result).isPresent();
        assertThat(result.get().getNodeName()).isEqualTo(ARTIFACT_ID_NAME);
    }

    @Test
    public void getDirectChildNodeByName_childNotExists() throws XmlException {
        Node node = XmlUtils.getSingleNode(document, SECOND_DEPENDENCY_EXPRESSION, xPath).get();
        Optional<Node> result = XmlUtils.getDirectChildNodeByName(node, "blabla");
        assertThat(result).isEmpty();
    }


    @Test
    public void getSingleNode() throws XmlException {
        Optional<Node> nodeOptional = XmlUtils.getSingleNode(document, PROJECT_GROUP_EXPRESSION, xPath);
        assertThat(nodeOptional).isPresent();
        assertThat(nodeOptional.get().getNodeName()).isEqualTo(GROUP_ID_NAME);
    }

    @Test
    public void getSingleNode_nodeNotExists() throws XmlException {
        Optional<Node> nodeOptional = XmlUtils.getSingleNode(document, "xxx", xPath);
        assertThat(nodeOptional).isEmpty();
    }
}