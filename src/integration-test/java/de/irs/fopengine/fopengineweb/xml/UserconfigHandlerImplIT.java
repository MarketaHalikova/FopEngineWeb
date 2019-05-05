package de.irs.fopengine.fopengineweb.xml;

import de.irs.fopengine.fopengineweb.enums.FontStyle;
import de.irs.fopengine.fopengineweb.exceptions.XmlException;
import de.irs.fopengine.fopengineweb.model.Font;
import de.irs.fopengine.fopengineweb.model.FontTriplet;
import de.irs.fopengine.fopengineweb.model.Project;
import de.irs.fopengine.fopengineweb.model.ProjectFileMapper;
import de.irs.fopengine.fopengineweb.services.XmlService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.FileCopyUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


public class UserconfigHandlerImplIT {

    public static final Path PROJECT_DIRECTORY = Paths.get("target/test-classes");
    public static final String XPATH_TEMPLATE = "//font-triplet[@name='%s' and @style='%s' and @weight='%s']";
    public static final Path USERCONFIG_SOURCE = Paths.get(PROJECT_DIRECTORY.toString(), XmlService.PROJECT_RESOURCES_DIRECTORY, "userconfig.xml");
    public static final Path USERCONFIG_TARGET = Paths.get(PROJECT_DIRECTORY.toString(), XmlService.PROJECT_RESOURCES_DIRECTORY,"userconfig_test.xml");
    public static final String FONT_NAME = "JCArialBold";
    public static final FontStyle FONT_STYLE = FontStyle.bold;
    public static final ProjectFileMapper METRICS_FILE = new ProjectFileMapper("metrics", "fonts", "fonts");
    public static final ProjectFileMapper FONT_FILE_TTF = new ProjectFileMapper("ttf", "fonts", "fonts");
    public static final ProjectFileMapper FONT_FILE_PFB = new ProjectFileMapper("pfb", "fonts", "fonts");
    public static final ProjectFileMapper FONT_FILE_PFM = new ProjectFileMapper("pfm", "fonts", "fonts");
    public static final String INDD_NAME = "inddName";
    public static final String INDD_STYLE = "Regular";
    public static final String FONT_NAME_1 = "testFont";
    public static final String FONT_XPATH_TEMPLATE = "/configuration/fonts/font";
    public static final String FONT_2 = "font2";

    private static XPath xPath;
    private UserconfigHandlerImpl userConfigHandler;
    private Document userConfigDocument;
    private FontTriplet testTriplet;

    @BeforeClass
    public static void setUpClass() {
        xPath = XPathFactory.newInstance().newXPath();
    }

    @Before
    public void setUp() throws Exception {
        FileCopyUtils.copy(USERCONFIG_SOURCE.toFile(), USERCONFIG_TARGET.toFile());
        userConfigHandler = new UserconfigHandlerImpl();
        userConfigDocument = XmlUtils.readXmlDocument(USERCONFIG_TARGET);

        testTriplet = new FontTriplet();
        ProjectFileMapper matricsFile = METRICS_FILE;
        testTriplet.setMetricsFile(matricsFile);
        testTriplet.setFontStyle(FONT_STYLE);
        ProjectFileMapper fontFileTtf = FONT_FILE_TTF;
        testTriplet.getFontFiles().add(fontFileTtf);
        testTriplet.setInddName(INDD_NAME);
        testTriplet.setInddStyle(INDD_STYLE);
    }

    @Test
    public void updateFontsInXml() throws Exception {
        // precondition
        NodeList resultNodeList = XmlUtils.getNodeList(userConfigDocument, FONT_XPATH_TEMPLATE, xPath);
        assertThat(resultNodeList.getLength()).isEqualTo(13);

        // given
        Project project = new Project();
        Font font1 = new Font(FONT_NAME_1);
        font1.getFontTriplets().add(testTriplet);
        project.getFontSet().add(font1);

        Font font2 = new Font(FONT_2);
        FontTriplet triplet11 = new FontTriplet(FontStyle.normal, "triplet11_inddName", "triplet11_inddStyle");
        FontTriplet triplet12 = new FontTriplet(FontStyle.italic, "triplet12_inddName", "triplet12_inddStyle");
        FontTriplet triplet13 = new FontTriplet(FontStyle.bold, "triplet13_inddName", "triplet13_inddStyle");
        FontTriplet triplet14 = new FontTriplet(FontStyle.bolditalic, "triplet14_inddName", "triplet14_inddStyle");
        ProjectFileMapper metrics = new ProjectFileMapper("metrics.xml", "", "");
        triplet11.setMetricsFile(metrics);
        triplet12.setMetricsFile(metrics);
        triplet13.setMetricsFile(metrics);
        triplet14.setMetricsFile(metrics);
        ProjectFileMapper embed = new ProjectFileMapper("embed.xml", "", "");
        triplet11.getFontFiles().add(embed);
        triplet12.getFontFiles().add(embed);
        triplet13.getFontFiles().add(embed);
        triplet14.getFontFiles().add(embed);
        project.getFontSet().add(font2);
        font2.getFontTriplets().add(triplet11);
        font2.getFontTriplets().add(triplet12);
        font2.getFontTriplets().add(triplet13);
        font2.getFontTriplets().add(triplet14);

        ProjectFileMapper userconfigMapper
                = new ProjectFileMapper(USERCONFIG_TARGET.getFileName().toString(), "", ".");
        project.setUserConfig(userconfigMapper);
        project.setProjectDirectory(PROJECT_DIRECTORY);
        userConfigHandler.updateFontsInXml(project);

        // then
        Document doc = XmlUtils.readXmlDocument(USERCONFIG_TARGET);
        resultNodeList = XmlUtils.getNodeList(doc, FONT_XPATH_TEMPLATE, xPath);
        assertThat(resultNodeList.getLength()).isEqualTo(5);

        checkTextTriplet(doc);
        Optional<Node> resultNode = XmlUtils.getSingleNode(doc,
                String.format(UserconfigHandlerImpl.FONT_TRIPLET_XPATH_TEMPLATE, FONT_2, triplet11.getFontStyle().getStyle(),
                        triplet11.fontStyle.getWeight()), xPath);
        assertThat(resultNode).isPresent();
        resultNode = XmlUtils.getSingleNode(doc,
                String.format(UserconfigHandlerImpl.FONT_TRIPLET_XPATH_TEMPLATE, FONT_2, triplet12.getFontStyle().getStyle(),
                        triplet11.fontStyle.getWeight()), xPath);
        assertThat(resultNode).isPresent();
        resultNode = XmlUtils.getSingleNode(doc,
                String.format(UserconfigHandlerImpl.FONT_TRIPLET_XPATH_TEMPLATE, FONT_2, triplet13.getFontStyle().getStyle(),
                        triplet11.fontStyle.getWeight()), xPath);
        assertThat(resultNode).isPresent();
        resultNode = XmlUtils.getSingleNode(doc,
                String.format(UserconfigHandlerImpl.FONT_TRIPLET_XPATH_TEMPLATE, FONT_2, triplet14.getFontStyle().getStyle(),
                        triplet11.fontStyle.getWeight()), xPath);
        assertThat(resultNode).isPresent();

    }

    @Test
    public void updateFontsInXml_saveEmptyFont() throws Exception {
        // precondition
        NodeList resultNodeList = XmlUtils.getNodeList(userConfigDocument, FONT_XPATH_TEMPLATE, xPath);
        assertThat(resultNodeList.getLength()).isEqualTo(13);

        // given
        Project project = new Project();
        Font font1 = new Font("font1");
        project.getFontSet().add(font1);
        ProjectFileMapper userconfigMapper
                = new ProjectFileMapper(USERCONFIG_TARGET.getFileName().toString(), "", ".");
        project.setUserConfig(userconfigMapper);
        project.setProjectDirectory(PROJECT_DIRECTORY);
        userConfigHandler.updateFontsInXml(project);

        // then
        Document doc = XmlUtils.readXmlDocument(USERCONFIG_TARGET);
        resultNodeList = XmlUtils.getNodeList(doc, FONT_XPATH_TEMPLATE, xPath);
        assertThat(resultNodeList.getLength()).isEqualTo(0);
    }

    @Test(expected = XmlException.class)
    public void getUserConfigPath_userConfigMissing_shouldThrowException() throws XmlException {
        Project project = new Project();
        project.setProjectDirectory(PROJECT_DIRECTORY);
        userConfigHandler.getUserConfigPath(project);
    }

    @Test(expected = XmlException.class)
    public void getUserConfigPath_projectDirectoryMissing_shouldThrowException() throws XmlException {
        Project project = new Project();
        ProjectFileMapper notImportant = new ProjectFileMapper("x", "y", "z");
        project.setUserConfig(notImportant);
        userConfigHandler.getUserConfigPath(project);
    }

    @Test
    public void getUserConfigPath() throws XmlException {
        Project project = new Project();
        ProjectFileMapper userConfigMapper = new ProjectFileMapper("fileName", "sourcePath", "targetPath");
        project.setUserConfig(userConfigMapper);
        project.setProjectDirectory(PROJECT_DIRECTORY);
        Path result = userConfigHandler.getUserConfigPath(project);

        assertThat(result).isEqualTo(Paths.get(PROJECT_DIRECTORY.toString(), XmlService.PROJECT_RESOURCES_DIRECTORY, "sourcePath/fileName"));

    }

    @Test
    public void deleteAllFonts() throws XmlException {
        userConfigHandler.deleteAllFonts(userConfigDocument);
        //then
        XmlUtils.saveDocument(userConfigDocument, USERCONFIG_TARGET);

        // then
        Document doc = XmlUtils.readXmlDocument(USERCONFIG_TARGET);
        Optional<Node> resultNode = XmlUtils.getSingleNode(doc, UserconfigHandlerImpl.FONTS_XPATH_TEMPLATE, xPath);
        assertThat(resultNode).isPresent();
        assertThat(resultNode.get().getChildNodes().getLength()).isEqualTo(0);
    }

    @Test
    public void saveNewFontTriplet_fontIsTtf() throws XmlException {
        //given
        Document doc = XmlUtils.readXmlDocument(USERCONFIG_TARGET);

        //when
        userConfigHandler.saveOrUpdateFontTriplet(FONT_NAME_1, testTriplet, doc);
        XmlUtils.saveDocument(doc, USERCONFIG_TARGET);
        // then
        doc = XmlUtils.readXmlDocument(USERCONFIG_TARGET);
        checkTextTriplet(doc);

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
        Document doc = XmlUtils.readXmlDocument(USERCONFIG_TARGET);
        //when
        userConfigHandler.saveOrUpdateFontTriplet(FONT_NAME_1, testTriplet, doc);
        XmlUtils.saveDocument(doc, USERCONFIG_TARGET);
        // then
        doc = XmlUtils.readXmlDocument(USERCONFIG_TARGET);
        Optional<Node> resultNode = XmlUtils.getSingleNode(doc,
                String.format(XPATH_TEMPLATE, FONT_NAME_1, FONT_STYLE.getStyle(), FONT_STYLE.getWeight()), xPath);
        assertThat(resultNode).isPresent();
        assertThat(resultNode.get().getParentNode().getAttributes().getNamedItem("kerning").getNodeValue()).isEqualTo("no");
        // todo assertThat(resultNode.get().getParentNode().getAttributes().getNamedItem("embed-file").getNodeValue())
                //.isEqualTo(FONT_FILE_PFB.getFullTarget());
        // todo assertThat(resultNode.get().getParentNode().getAttributes().getNamedItem("metrics-file").getNodeValue())
                //.isEqualTo(METRICS_FILE.getFullTarget());

        assertThat(resultNode.get().getAttributes().getNamedItem("name").getNodeValue()).isEqualTo(FONT_NAME_1);
        assertThat(resultNode.get().getAttributes().getNamedItem("style").getNodeValue()).isEqualTo(FONT_STYLE.getStyle());
        assertThat(resultNode.get().getAttributes().getNamedItem("weight").getNodeValue()).isEqualTo(FONT_STYLE.getWeight());
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
        Document doc = XmlUtils.readXmlDocument(USERCONFIG_TARGET);
        //when
        userConfigHandler.saveOrUpdateFontTriplet(FONT_NAME, testTriplet, doc);
        XmlUtils.saveDocument(doc, USERCONFIG_TARGET);
        // then
        doc = XmlUtils.readXmlDocument(USERCONFIG_TARGET);
        Optional<Node> resultNode = XmlUtils.getSingleNode(doc,
                String.format(XPATH_TEMPLATE, FONT_NAME, FONT_STYLE.getStyle(), FONT_STYLE.getWeight()), xPath);
        assertThat(resultNode).isPresent();
        assertThat(resultNode.get().getParentNode().getAttributes().getNamedItem("kerning").getNodeValue()).isEqualTo("no");
        //todo assertThat(resultNode.get().getParentNode().getAttributes().getNamedItem("embed-file")
         //       .getNodeValue()).isEqualTo(FONT_FILE_TTF.getFullTarget());
        // todo assertThat(resultNode.get().getParentNode().getAttributes()
               // .getNamedItem("metrics-file").getNodeValue()).isEqualTo(METRICS_FILE.getFullTarget());

        assertThat(resultNode.get().getAttributes().getNamedItem("style").getNodeValue()).isEqualTo(FONT_STYLE.getStyle());
        assertThat(resultNode.get().getAttributes().getNamedItem("weight").getNodeValue()).isEqualTo(FONT_STYLE.getWeight());
        assertThat(resultNode.get().getAttributes().getNamedItem("inddName").getNodeValue()).isEqualTo(INDD_NAME);
        assertThat(resultNode.get().getAttributes().getNamedItem("inddStyle").getNodeValue()).isEqualTo(INDD_STYLE);
        assertThat(resultNode.get().getAttributes().getNamedItem("name").getNodeValue()).isEqualTo(FONT_NAME);

    }

    private void checkTextTriplet(Document document) throws XmlException {
        Optional<Node> resultNode = XmlUtils.getSingleNode(document,
                String.format(UserconfigHandlerImpl.FONT_TRIPLET_XPATH_TEMPLATE, FONT_NAME_1, testTriplet.getFontStyle().getStyle(),
                        testTriplet.fontStyle.getWeight()), xPath);
        assertThat(resultNode).isPresent();
        assertThat(resultNode.get().getParentNode().getAttributes().getNamedItem("kerning").getNodeValue()).isEqualTo("no");
       // todo assertThat(resultNode.get().getParentNode().getAttributes().getNamedItem("embed-file").getNodeValue()).isEqualTo(FONT_FILE_TTF.getFullTarget());
        // todo assertThat(resultNode.get().getParentNode().getAttributes().getNamedItem("metrics-file").getNodeValue()).isEqualTo(METRICS_FILE.getFullTarget());

        assertThat(resultNode.get().getAttributes().getNamedItem("name").getNodeValue()).isEqualTo(FONT_NAME_1);
        assertThat(resultNode.get().getAttributes().getNamedItem("style").getNodeValue()).isEqualTo(FONT_STYLE.getStyle());
        assertThat(resultNode.get().getAttributes().getNamedItem("weight").getNodeValue()).isEqualTo(FONT_STYLE.getWeight());
        assertThat(resultNode.get().getAttributes().getNamedItem("inddName").getNodeValue()).isEqualTo(INDD_NAME);
        assertThat(resultNode.get().getAttributes().getNamedItem("inddStyle").getNodeValue()).isEqualTo(INDD_STYLE);
    }
}