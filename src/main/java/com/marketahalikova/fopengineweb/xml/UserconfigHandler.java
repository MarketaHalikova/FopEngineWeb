package com.marketahalikova.fopengineweb.xml;

import com.marketahalikova.fopengineweb.enums.FontStyle;
import com.marketahalikova.fopengineweb.exceptions.XmlException;
import com.marketahalikova.fopengineweb.model.FontTriplet;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.nio.file.Path;


public class UserconfigHandler {

    public static final String FONT_TRIPLET_XPATH_TEMPLATE = "//font-triplet[@name='%s' and @style='%s' and @weight='%s']";

    //string.format - nahradi procenta stringama


    public static final String FONTS_XPATH_TEMPLATE = "/configuration/fonts";

    private final Path userconfig;
    private Document userConfigDocument;
    private final XPath xPath = XPathFactory.newInstance().newXPath();

    public UserconfigHandler(Path userconfig) throws XmlException {
        this.userconfig = userconfig;
        userConfigDocument = XmlUtils.readXmlDocument(userconfig);
    }

    /**
     * Delete font triplet from user config and save the document to file
     *
     * @param fontName
     * @param fontStyle
     * @return
     * @throws XmlException
     */
    public boolean deleteTriplet(String fontName, FontStyle fontStyle) throws XmlException {
        String style = fontStyle.getStyle();
        String weight = fontStyle.getWeight();
        // find font-triplet nods by name, style, weight
        NodeList nodeList = XmlUtils.getNodeList(userConfigDocument,String.format(FONT_TRIPLET_XPATH_TEMPLATE,fontName,style,weight),xPath);
        if(nodeList.getLength()==1){
            // in order to remove font node (which is a parent of the font-triplet)
            // need to call method removeChild on parent node of font node - which is fonts node
            nodeList.item(0).getParentNode().getParentNode().removeChild(nodeList.item(0).getParentNode());
            XmlUtils.saveDocument(userConfigDocument, userconfig);
            return true;
        } else {
            return false;
        }

    }

    public void saveOrUpdateFontTriplet(String fontName, FontTriplet fontTriplet) throws XmlException {
        String style = fontTriplet.getFontStyle().getStyle();
        String weight = fontTriplet.getFontStyle().getWeight();
        Node tripletNode;
        Node parentNode;
        NodeList nodeList = XmlUtils.getNodeList(userConfigDocument,String.format(FONT_TRIPLET_XPATH_TEMPLATE,fontName,style,weight),xPath);
        if(nodeList.getLength()==1){
            tripletNode = nodeList.item(0);
            parentNode = tripletNode.getParentNode();
        } else {
            parentNode = userConfigDocument.createElement("font");
            tripletNode = userConfigDocument.createElement("font-triplet");
            parentNode.appendChild(tripletNode);
            //appending node to document
            XmlUtils.getSingleNode(userConfigDocument,"/configuration/fonts", xPath).get().appendChild(parentNode);
        }

        XmlUtils.updateOrCreateAttribute(parentNode, "metrics-file", fontTriplet.getMetricsFile().getFullTarget());
        XmlUtils.updateOrCreateAttribute(parentNode, "kerning", "no");
        XmlUtils.updateOrCreateAttribute(parentNode, "embed-file", fontTriplet.getEmbedFile());
        XmlUtils.updateOrCreateAttribute(tripletNode, "name", fontName);
        XmlUtils.updateOrCreateAttribute(tripletNode, "style", style);
        XmlUtils.updateOrCreateAttribute(tripletNode, "weight", weight);
        XmlUtils.updateOrCreateAttribute(tripletNode, "inddName", fontTriplet.getInddName());
        XmlUtils.updateOrCreateAttribute(tripletNode, "inddStyle", fontTriplet.getInddStyle());
        XmlUtils.saveDocument(userConfigDocument, userconfig);

        // find font-triplet nody by name, style, weight
        // if exists - update attributes of font-triplet and font nodes
        // if not exists, create a new font-triplet and font nodes and save content of fontTriplet from parameter
        // save document
    }}