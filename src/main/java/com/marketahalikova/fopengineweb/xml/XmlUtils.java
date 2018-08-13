package com.marketahalikova.fopengineweb.xml;

import com.marketahalikova.fopengineweb.exceptions.XmlException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class XmlUtils {

    private XmlUtils() {
    }

    /**
     * Return node list from xml document for xPath expression
     *
     * @param xmlDocument
     * @param xPathExpression
     * @param xPath
     * @return
     * @throws XmlException
     */
    public static NodeList getNodeList(Document xmlDocument, String xPathExpression, XPath xPath) throws XmlException {
        try {
            NodeList nodeList = (NodeList) xPath.evaluate(xPathExpression, xmlDocument, XPathConstants.NODESET);
            return nodeList;
        } catch (XPathExpressionException e) {
            throw new XmlException("error while extracting node list: " + xPathExpression,e);
        }
    }

    /**
     * Return text from node for xPath expression from xml document
     *
     * @param xmlDocument
     * @param xPathExpression
     * @param xPath
     * @return
     * @throws XmlException
     */
    public static String getSingleNodeText(Document xmlDocument, String xPathExpression, XPath xPath) throws XmlException {
        try {
            Node node = (Node) xPath.evaluate(xPathExpression, xmlDocument, XPathConstants.NODE);
            if (Optional.ofNullable(node).isPresent()){
                return node.getTextContent();
            } else {
                return "";
            }

        } catch (XPathExpressionException e) {
            throw new XmlException("error while extracting single node: " + xPathExpression, e);
        }
    }

    /**
     * Return single node for xPath expression
     *
     * @param xmlDocument
     * @param xPathExpression
     * @param xPath
     * @return
     * @throws XmlException
     */
    public static Optional<Node> getSingleNode(Document xmlDocument, String xPathExpression, XPath xPath) throws XmlException {
        Node node = null;
        try {
            node = (Node) xPath.evaluate(xPathExpression, xmlDocument, XPathConstants.NODE);
            return Optional.ofNullable(node);
        } catch (XPathExpressionException e) {
            throw new XmlException("error while extracting single node: " + xPathExpression, e);
        }

    }

    /**
     * Return document created from xml input stream
     *
     * @param inputStream
     * @return
     * @throws XmlException
     */
    public static Document readXmlDocument(InputStream inputStream) throws XmlException {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        try {
            db = dbf.newDocumentBuilder();
            Document document = db.parse(inputStream);
            return document;
        } catch (ParserConfigurationException | SAXException |IOException e) {
            throw new XmlException("error while creating document", e);
        }

    }

    /**
     * Return document created from a file
     *
     * @param aSourceFile
     * @return
     * @throws XmlException
     */
    public static Document readXmlDocument(Path aSourceFile) throws XmlException {
        try {
            InputStream inputStream = Files.newInputStream(aSourceFile);
            return readXmlDocument(inputStream);
        } catch (IOException e) {
            throw new XmlException("file from path was not found: " + aSourceFile.toString(), e);
        }
    }

    /**
     * Save document to a file
     *
     * @param xmlDocument
     * @param aSourceFile
     * @throws XmlException
     */
    public static void saveDocument(Document xmlDocument, Path aSourceFile) throws XmlException {
        try {
            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            xformer.transform(new DOMSource(xmlDocument), new StreamResult(aSourceFile.toFile()));
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }


    }

    /**
     * Find direct child of parent node by name and return text of this child
     *
     * @param parentNode
     * @param childNodeName
     * @return
     */
    public static String getDirectChildNodeTextByName(Node parentNode, String childNodeName) {
        Optional<Node> childNode = getDirectChildNodeByName(parentNode, childNodeName);
        if (childNode.isPresent()) {
            return childNode.get().getTextContent();
        } else {
            return "";
        }
    }

    /**
     * Find direct child of parent node by name
     *
     * @param parentNode
     * @param childNodeName
     * @return
     */
    public static Optional<Node> getDirectChildNodeByName(Node parentNode, String childNodeName) {
        for (int i = 0; i < parentNode.getChildNodes().getLength(); i++) {
            Node child = parentNode.getChildNodes().item(i);
            if (child.getNodeName().equals(childNodeName)) {
                return Optional.of(child);
            }
        }
        return Optional.empty();
    }


    public static void updateOrCreateAttribute(Node node, String attributeName, String newValue){
        Node attribute = node.getAttributes().getNamedItem(attributeName);
        if(attribute != null) {
            if(!attribute.getNodeValue().equals(newValue)) {
                attribute.setNodeValue(newValue);
            }
        } else {
            ((Element)node).setAttribute(attributeName, newValue);
        }
    }
}
