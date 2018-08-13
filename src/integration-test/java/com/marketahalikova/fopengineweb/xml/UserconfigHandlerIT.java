package com.marketahalikova.fopengineweb.xml;

import com.marketahalikova.fopengineweb.enums.FontStyle;
import com.marketahalikova.fopengineweb.exceptions.XmlException;
import com.marketahalikova.fopengineweb.model.FontTriplet;
import com.marketahalikova.fopengineweb.model.ProjectFileMapper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.FileCopyUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


public class UserconfigHandlerIT {

    public static final String XPATH_TEMPLATE = "//font-triplet[@name='%s' and @style='%s' and @weight='%s']";
    public static final Path USERCONFIG_SOURCE = Paths.get("target/test-classes/userconfig.xml");
    public static final Path USERCONFIG_TARGET = Paths.get("target/test-classes/userconfig_test.xml");
    public static final String FONT_NAME = "JCArialBlack";
    public static final FontStyle FONT_STYLE = FontStyle.BoldItalic;
    public static final ProjectFileMapper METRICS_FILE = new ProjectFileMapper("metrics", "sourcePath", "targetPath");
    public static final ProjectFileMapper FONT_FILE_TTF = new ProjectFileMapper("ttf", "sourcePath", "targetPath");
    public static final ProjectFileMapper FONT_FILE_PFM = new ProjectFileMapper("pfm", "sourcePath", "targetPath");
    public static final ProjectFileMapper FONT_FILE_PFB = new ProjectFileMapper("pfb", "sourcePath", "targetPath");
    public static final String INDD_NAME = "inddName";
    public static final String INDD_STYLE = "inddStyle";
    public static final String FONT_NAME_1 = "testFont";
    public static final String STYLE = FONT_STYLE.getStyle();
    public static final String WEIGHT = FONT_STYLE.getWeight();

    private static XPath xPath;
    UserconfigHandler userConfigHandler;


    @BeforeClass
    public static void setUpClass() {
        xPath = XPathFactory.newInstance().newXPath();
    }

    @Before
    public void setUp() throws Exception {
        FileCopyUtils.copy(USERCONFIG_SOURCE.toFile(), USERCONFIG_TARGET.toFile());
        userConfigHandler = new UserconfigHandler(USERCONFIG_TARGET);
    }

    @Test
    public void deleteTriplet_tripletFoundAndDeleted() throws XmlException {
        // check if node exists
        Document doc = XmlUtils.readXmlDocument(USERCONFIG_TARGET);
        Optional<Node> resultNode = XmlUtils.getSingleNode(doc,
                String.format(XPATH_TEMPLATE, FONT_NAME, STYLE, WEIGHT), xPath);
        assertThat(resultNode).isPresent();

        // delete node
        boolean result = userConfigHandler.deleteTriplet(FONT_NAME, FONT_STYLE);
        assertThat(result).isTrue();

        // check that node does not exist
        doc = XmlUtils.readXmlDocument(USERCONFIG_TARGET);
        resultNode = XmlUtils.getSingleNode(doc,
                String.format(XPATH_TEMPLATE, FONT_NAME, STYLE, WEIGHT), xPath);
        assertThat(resultNode).isNotPresent();

    }

    @Test
    public void deleteTriplet_tripletNotFound() throws XmlException {
        // delete node
        boolean result = userConfigHandler.deleteTriplet("blabla", FONT_STYLE);
        assertThat(result).isFalse();
    }

    @Test
    public void saveNewFontTriplet_fontIsTtf() throws XmlException {
        //given
        FontTriplet testTriplet = new FontTriplet();
        ProjectFileMapper matricsFile = METRICS_FILE;
        testTriplet.setMetricsFile(matricsFile);
        testTriplet.setFontStyle(FONT_STYLE);
        ProjectFileMapper fontFileTtf = FONT_FILE_TTF;
        testTriplet.getFontFiles().add(fontFileTtf);
        testTriplet.setInddName(INDD_NAME);
        testTriplet.setInddStyle(INDD_STYLE);

        //when
        userConfigHandler.saveOrUpdateFontTriplet(FONT_NAME_1, testTriplet);

        // then
        Document doc = XmlUtils.readXmlDocument(USERCONFIG_TARGET);
        Optional<Node> resultNode = XmlUtils.getSingleNode(doc,
                String.format(XPATH_TEMPLATE, FONT_NAME_1, STYLE, WEIGHT), xPath);
        assertThat(resultNode).isPresent();
        assertThat(resultNode.get().getParentNode().getAttributes().getNamedItem("kerning").getNodeValue()).isEqualTo("no");
        assertThat(resultNode.get().getParentNode().getAttributes().getNamedItem("embed-file").getNodeValue()).isEqualTo(FONT_FILE_TTF.getTargetPath()+"/" + FONT_FILE_TTF.getFileName());
        assertThat(resultNode.get().getParentNode().getAttributes().getNamedItem("metrics-file").getNodeValue()).isEqualTo(METRICS_FILE.getTargetPath() + "/" + METRICS_FILE.getFileName());

        assertThat(resultNode.get().getAttributes().getNamedItem("name").getNodeValue()).isEqualTo(FONT_NAME_1);
        assertThat(resultNode.get().getAttributes().getNamedItem("style").getNodeValue()).isEqualTo(STYLE);
        assertThat(resultNode.get().getAttributes().getNamedItem("weight").getNodeValue()).isEqualTo(WEIGHT);
        assertThat(resultNode.get().getAttributes().getNamedItem("inddName").getNodeValue()).isEqualTo(INDD_NAME);
        assertThat(resultNode.get().getAttributes().getNamedItem("inddStyle").getNodeValue()).isEqualTo(INDD_STYLE);

    }

    @Test
    public void saveNewFontTriplet_fontIsPfb() throws XmlException {
        //given
        FontTriplet testTriplet = new FontTriplet();
        ProjectFileMapper matricsFile = METRICS_FILE;
        testTriplet.setMetricsFile(matricsFile);
        testTriplet.setFontStyle(FONT_STYLE);
        ProjectFileMapper fontFilePfb = FONT_FILE_PFB;
        ProjectFileMapper fontFilePfm = FONT_FILE_PFM;
        testTriplet.getFontFiles().add(fontFilePfm);
        testTriplet.getFontFiles().add(fontFilePfb);
        testTriplet.setInddName(INDD_NAME);
        testTriplet.setInddStyle(INDD_STYLE);

        //when
        userConfigHandler.saveOrUpdateFontTriplet(FONT_NAME_1, testTriplet);

        // then
        Document doc = XmlUtils.readXmlDocument(USERCONFIG_TARGET);
        Optional<Node> resultNode = XmlUtils.getSingleNode(doc,
                String.format(XPATH_TEMPLATE, FONT_NAME_1, STYLE, WEIGHT), xPath);
        assertThat(resultNode).isPresent();
        assertThat(resultNode.get().getParentNode().getAttributes().getNamedItem("kerning").getNodeValue()).isEqualTo("no");
        assertThat(resultNode.get().getParentNode().getAttributes().getNamedItem("embed-file").getNodeValue())
                .isEqualTo(FONT_FILE_PFB.getTargetPath()+"/" + FONT_FILE_PFB.getFileName());
        assertThat(resultNode.get().getParentNode().getAttributes().getNamedItem("metrics-file").getNodeValue())
                .isEqualTo(METRICS_FILE.getTargetPath() + "/" + METRICS_FILE.getFileName());

        assertThat(resultNode.get().getAttributes().getNamedItem("name").getNodeValue()).isEqualTo(FONT_NAME_1);
        assertThat(resultNode.get().getAttributes().getNamedItem("style").getNodeValue()).isEqualTo(STYLE);
        assertThat(resultNode.get().getAttributes().getNamedItem("weight").getNodeValue()).isEqualTo(WEIGHT);
        assertThat(resultNode.get().getAttributes().getNamedItem("inddName").getNodeValue()).isEqualTo(INDD_NAME);
        assertThat(resultNode.get().getAttributes().getNamedItem("inddStyle").getNodeValue()).isEqualTo(INDD_STYLE);

    }

    @Test
    public void updateFontTriplet() throws XmlException {
        //given
        FontTriplet testTriplet = new FontTriplet();
        ProjectFileMapper matricsFile = METRICS_FILE;
        testTriplet.setMetricsFile(matricsFile);
        testTriplet.setFontStyle(FONT_STYLE);
        ProjectFileMapper fontFileTtf = FONT_FILE_TTF;
        testTriplet.getFontFiles().add(fontFileTtf);
        testTriplet.setInddName(INDD_NAME);
        testTriplet.setInddStyle(INDD_STYLE);

        //when
        userConfigHandler.saveOrUpdateFontTriplet(FONT_NAME, testTriplet);

        // then
        Document doc = XmlUtils.readXmlDocument(USERCONFIG_TARGET);
        Optional<Node> resultNode = XmlUtils.getSingleNode(doc,
                String.format(XPATH_TEMPLATE, FONT_NAME, STYLE, WEIGHT), xPath);
        assertThat(resultNode).isPresent();
        assertThat(resultNode.get().getParentNode().getAttributes().getNamedItem("kerning").getNodeValue()).isEqualTo("no");
        assertThat(resultNode.get().getParentNode().getAttributes().getNamedItem("embed-file").getNodeValue()).isEqualTo(FONT_FILE_TTF.getTargetPath()+"/" + FONT_FILE_TTF.getFileName());
        assertThat(resultNode.get().getParentNode().getAttributes().getNamedItem("metrics-file").getNodeValue()).isEqualTo(METRICS_FILE.getTargetPath() + "/" + METRICS_FILE.getFileName());

        assertThat(resultNode.get().getAttributes().getNamedItem("style").getNodeValue()).isEqualTo(STYLE);
        assertThat(resultNode.get().getAttributes().getNamedItem("weight").getNodeValue()).isEqualTo(WEIGHT);
        assertThat(resultNode.get().getAttributes().getNamedItem("inddName").getNodeValue()).isEqualTo(INDD_NAME);
        assertThat(resultNode.get().getAttributes().getNamedItem("inddStyle").getNodeValue()).isEqualTo(INDD_STYLE);
        assertThat(resultNode.get().getAttributes().getNamedItem("name").getNodeValue()).isEqualTo(FONT_NAME);

    }
}