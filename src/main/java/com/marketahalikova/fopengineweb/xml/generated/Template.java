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
 *         &lt;element name="Main-template">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{}File"/>
 *                   &lt;element name="Template-files" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element ref="{}File" maxOccurs="unbounded"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{}Graphics" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="template-name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "mainTemplate",
    "graphics"
})
@XmlRootElement(name = "Template")
public class Template {

    @XmlElement(name = "Main-template", required = true)
    protected Template.MainTemplate mainTemplate;
    @XmlElement(name = "Graphics")
    protected Graphics graphics;
    @XmlAttribute(name = "template-name", required = true)
    protected String templateName;

    /**
     * Ruft den Wert der mainTemplate-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Template.MainTemplate }
     *     
     */
    public Template.MainTemplate getMainTemplate() {
        return mainTemplate;
    }

    /**
     * Legt den Wert der mainTemplate-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Template.MainTemplate }
     *     
     */
    public void setMainTemplate(Template.MainTemplate value) {
        this.mainTemplate = value;
    }

    /**
     * Ruft den Wert der graphics-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Graphics }
     *     
     */
    public Graphics getGraphics() {
        return graphics;
    }

    /**
     * Legt den Wert der graphics-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Graphics }
     *     
     */
    public void setGraphics(Graphics value) {
        this.graphics = value;
    }

    /**
     * Ruft den Wert der templateName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * Legt den Wert der templateName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTemplateName(String value) {
        this.templateName = value;
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
     *       &lt;sequence>
     *         &lt;element ref="{}File"/>
     *         &lt;element name="Template-files" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element ref="{}File" maxOccurs="unbounded"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "file",
        "templateFiles"
    })
    public static class MainTemplate {

        @XmlElement(name = "File", required = true)
        protected File file;
        @XmlElement(name = "Template-files")
        protected Template.MainTemplate.TemplateFiles templateFiles;

        /**
         * Ruft den Wert der file-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link File }
         *     
         */
        public File getFile() {
            return file;
        }

        /**
         * Legt den Wert der file-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link File }
         *     
         */
        public void setFile(File value) {
            this.file = value;
        }

        /**
         * Ruft den Wert der templateFiles-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Template.MainTemplate.TemplateFiles }
         *     
         */
        public Template.MainTemplate.TemplateFiles getTemplateFiles() {
            return templateFiles;
        }

        /**
         * Legt den Wert der templateFiles-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Template.MainTemplate.TemplateFiles }
         *     
         */
        public void setTemplateFiles(Template.MainTemplate.TemplateFiles value) {
            this.templateFiles = value;
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
         *       &lt;sequence>
         *         &lt;element ref="{}File" maxOccurs="unbounded"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "file"
        })
        public static class TemplateFiles {

            @XmlElement(name = "File", required = true)
            protected List<File> file;

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

        }

    }

}
