package com.marketahalikova.fopengineweb.xml;

import com.marketahalikova.fopengineweb.exceptions.XmlException;
import com.marketahalikova.fopengineweb.model.Font;
import com.marketahalikova.fopengineweb.model.FontTriplet;
import com.marketahalikova.fopengineweb.model.Project;
import com.marketahalikova.fopengineweb.model.ProjectFileMapper;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Component
public class UserconfigHandlerImpl implements UserconfigHandler{

    public static final String FONT_TRIPLET_XPATH_TEMPLATE = "//font-triplet[@name='%s' and @style='%s' and @weight='%s']";

    //string.format - nahradi procenta stringama


    public static final String FONTS_XPATH_TEMPLATE = "/configuration/fonts";

    private final XPath xPath = XPathFactory.newInstance().newXPath();

    public void saveOrUpdateFontTriplet(String fontName, FontTriplet fontTriplet, Document userConfigDocument) throws XmlException {
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
    }

    @Override
    public void updateFontsInXml(Project project) throws XmlException {
        Path userConfig =getUserConfigPath(project);
        Document userConfigDocument = XmlUtils.readXmlDocument(userConfig);
        deleteAllFonts(userConfigDocument);
        for (Font font : project.getFontSet()) {
            for (FontTriplet triplet : font.getFontTriplets()) {
                saveOrUpdateFontTriplet(font.getFontName(), triplet, userConfigDocument);
            }
        }
        XmlUtils.saveDocument(userConfigDocument, userConfig);
    }

    @Override
    public Path getUserConfigPath(Project project) throws XmlException {
        ProjectFileMapper userConfig = Optional.ofNullable(project.getUserConfig())
                .orElseThrow(() -> new XmlException("User config is not set"));
        Path projectDirectory = Optional.ofNullable(project.getProjectDirectory())
                .orElseThrow(() -> new XmlException("Project directory is not set"));
        return Paths.get(projectDirectory.toString(),  userConfig.getFullSource());
    }

    public void deleteAllFonts(Document userConfigDocument) throws XmlException {
        Optional<Node> fontsOptional = XmlUtils.getSingleNode(userConfigDocument, FONTS_XPATH_TEMPLATE, xPath);
        Node fonts = fontsOptional.orElseThrow(() -> new XmlException("Fonts node not found in userconfig"));
        XmlUtils.removeChildren(fonts);
    }
}