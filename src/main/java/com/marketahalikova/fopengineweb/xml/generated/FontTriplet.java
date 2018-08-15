//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2018.08.13 um 03:51:00 PM CEST 
//


package com.marketahalikova.fopengineweb.xml.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für anonymous complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Metrics-file">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attGroup ref="{}file"/>
 *                 &lt;attribute name="file-name" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{}File" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="font-style" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="normal"/>
 *             &lt;enumeration value="italic"/>
 *             &lt;enumeration value="bold"/>
 *             &lt;enumeration value="bolditalic"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="indd-name" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="indd-style" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "metricsFile",
    "file"
})
@XmlRootElement(name = "Font-triplet")
public class FontTriplet {

    @XmlElement(name = "Metrics-file", required = true)
    protected FontTriplet.MetricsFile metricsFile;
    @XmlElement(name = "File", required = true)
    protected List<File> file;
    @XmlAttribute(name = "font-style", required = true)
    protected String fontStyle;
    @XmlAttribute(name = "indd-name")
    @XmlSchemaType(name = "anySimpleType")
    protected String inddName;
    @XmlAttribute(name = "indd-style")
    @XmlSchemaType(name = "anySimpleType")
    protected String inddStyle;

    /**
     * Ruft den Wert der metricsFile-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link FontTriplet.MetricsFile }
     *     
     */
    public FontTriplet.MetricsFile getMetricsFile() {
        return metricsFile;
    }

    /**
     * Legt den Wert der metricsFile-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link FontTriplet.MetricsFile }
     *     
     */
    public void setMetricsFile(FontTriplet.MetricsFile value) {
        this.metricsFile = value;
    }

    /**
     * Gets the value of the file property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the file property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFile().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link File }
     * 
     * 
     */
    public List<File> getFile() {
        if (file == null) {
            file = new ArrayList<File>();
        }
        return this.file;
    }

    /**
     * Ruft den Wert der fontStyle-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFontStyle() {
        return fontStyle;
    }

    /**
     * Legt den Wert der fontStyle-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFontStyle(String value) {
        this.fontStyle = value;
    }

    /**
     * Ruft den Wert der inddName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInddName() {
        return inddName;
    }

    /**
     * Legt den Wert der inddName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInddName(String value) {
        this.inddName = value;
    }

    /**
     * Ruft den Wert der inddStyle-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInddStyle() {
        return inddStyle;
    }

    /**
     * Legt den Wert der inddStyle-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInddStyle(String value) {
        this.inddStyle = value;
    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attGroup ref="{}file"/>
     *       &lt;attribute name="file-name" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class MetricsFile {

        @XmlAttribute(name = "file-name", required = true)
        @XmlSchemaType(name = "anySimpleType")
        protected String fileName;
        @XmlAttribute(name = "source-path")
        protected String sourcePath;
        @XmlAttribute(name = "target-path", required = true)
        protected String targetPath;

        /**
         * Ruft den Wert der fileName-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFileName() {
            return fileName;
        }

        /**
         * Legt den Wert der fileName-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFileName(String value) {
            this.fileName = value;
        }

        /**
         * Ruft den Wert der sourcePath-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSourcePath() {
            return sourcePath;
        }

        /**
         * Legt den Wert der sourcePath-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSourcePath(String value) {
            this.sourcePath = value;
        }

        /**
         * Ruft den Wert der targetPath-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTargetPath() {
            return targetPath;
        }

        /**
         * Legt den Wert der targetPath-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTargetPath(String value) {
            this.targetPath = value;
        }

    }

}
