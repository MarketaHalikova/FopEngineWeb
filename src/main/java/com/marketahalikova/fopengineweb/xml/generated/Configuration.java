//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2018.08.13 um 03:51:00 PM CEST 
//


package com.marketahalikova.fopengineweb.xml.generated;

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
 *         &lt;element name="Userconfig" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{}File"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{}Jobs"/>
 *         &lt;element ref="{}Fonts" minOccurs="0"/>
 *         &lt;element ref="{}Transformations" minOccurs="0"/>
 *         &lt;element ref="{}Services" minOccurs="0"/>
 *         &lt;element ref="{}WorkFlow"/>
 *         &lt;element ref="{}Templates" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{}mavenArtifact"/>
 *       &lt;attribute name="project-name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="description" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="self-contained" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "userconfig",
    "jobs",
    "fonts",
    "transformations",
    "services",
    "workFlow",
    "templates"
})
@XmlRootElement(name = "Configuration")
public class Configuration {

    @XmlElement(name = "Userconfig")
    protected Configuration.Userconfig userconfig;
    @XmlElement(name = "Jobs", required = true)
    protected Jobs jobs;
    @XmlElement(name = "Fonts")
    protected Fonts fonts;
    @XmlElement(name = "Transformations")
    protected Transformations transformations;
    @XmlElement(name = "Services")
    protected Services services;
    @XmlElement(name = "WorkFlow", required = true)
    protected WorkFlow workFlow;
    @XmlElement(name = "Templates")
    protected Templates templates;
    @XmlAttribute(name = "project-name", required = true)
    protected String projectName;
    @XmlAttribute(name = "description")
    protected String description;
    @XmlAttribute(name = "self-contained")
    protected Boolean selfContained;
    @XmlAttribute(name = "group", required = true)
    protected String group;
    @XmlAttribute(name = "artifact", required = true)
    protected String artifact;
    @XmlAttribute(name = "version", required = true)
    protected String version;

    /**
     * Ruft den Wert der userconfig-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Configuration.Userconfig }
     *     
     */
    public Configuration.Userconfig getUserconfig() {
        return userconfig;
    }

    /**
     * Legt den Wert der userconfig-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Configuration.Userconfig }
     *     
     */
    public void setUserconfig(Configuration.Userconfig value) {
        this.userconfig = value;
    }

    /**
     * Ruft den Wert der jobs-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Jobs }
     *     
     */
    public Jobs getJobs() {
        return jobs;
    }

    /**
     * Legt den Wert der jobs-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Jobs }
     *     
     */
    public void setJobs(Jobs value) {
        this.jobs = value;
    }

    /**
     * Ruft den Wert der fonts-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Fonts }
     *     
     */
    public Fonts getFonts() {
        return fonts;
    }

    /**
     * Legt den Wert der fonts-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Fonts }
     *     
     */
    public void setFonts(Fonts value) {
        this.fonts = value;
    }

    /**
     * Ruft den Wert der transformations-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Transformations }
     *     
     */
    public Transformations getTransformations() {
        return transformations;
    }

    /**
     * Legt den Wert der transformations-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Transformations }
     *     
     */
    public void setTransformations(Transformations value) {
        this.transformations = value;
    }

    /**
     * Ruft den Wert der services-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Services }
     *     
     */
    public Services getServices() {
        return services;
    }

    /**
     * Legt den Wert der services-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Services }
     *     
     */
    public void setServices(Services value) {
        this.services = value;
    }

    /**
     * Ruft den Wert der workFlow-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link WorkFlow }
     *     
     */
    public WorkFlow getWorkFlow() {
        return workFlow;
    }

    /**
     * Legt den Wert der workFlow-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link WorkFlow }
     *     
     */
    public void setWorkFlow(WorkFlow value) {
        this.workFlow = value;
    }

    /**
     * Ruft den Wert der templates-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Templates }
     *     
     */
    public Templates getTemplates() {
        return templates;
    }

    /**
     * Legt den Wert der templates-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Templates }
     *     
     */
    public void setTemplates(Templates value) {
        this.templates = value;
    }

    /**
     * Ruft den Wert der projectName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Legt den Wert der projectName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProjectName(String value) {
        this.projectName = value;
    }

    /**
     * Ruft den Wert der description-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Legt den Wert der description-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Ruft den Wert der selfContained-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isSelfContained() {
        if (selfContained == null) {
            return false;
        } else {
            return selfContained;
        }
    }

    /**
     * Legt den Wert der selfContained-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSelfContained(Boolean value) {
        this.selfContained = value;
    }

    /**
     * Ruft den Wert der group-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroup() {
        return group;
    }

    /**
     * Legt den Wert der group-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroup(String value) {
        this.group = value;
    }

    /**
     * Ruft den Wert der artifact-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArtifact() {
        return artifact;
    }

    /**
     * Legt den Wert der artifact-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArtifact(String value) {
        this.artifact = value;
    }

    /**
     * Ruft den Wert der version-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Legt den Wert der version-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
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
    public static class Userconfig {

        @XmlElement(name = "File", required = true)
        protected File file;

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

    }

}
