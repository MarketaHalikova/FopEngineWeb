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
 *         &lt;element ref="{}Main-schema" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="job-name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="process-name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="transformation-name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="template-name" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "mainSchema"
})
@XmlRootElement(name = "Job")
public class Job {

    @XmlElement(name = "Main-schema")
    protected MainSchema mainSchema;
    @XmlAttribute(name = "job-name", required = true)
    protected String jobName;
    @XmlAttribute(name = "process-name", required = true)
    protected String processName;
    @XmlAttribute(name = "transformation-name")
    protected String transformationName;
    @XmlAttribute(name = "template-name")
    @XmlSchemaType(name = "anySimpleType")
    protected String templateName;

    /**
     * Ruft den Wert der mainSchema-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link MainSchema }
     *     
     */
    public MainSchema getMainSchema() {
        return mainSchema;
    }

    /**
     * Legt den Wert der mainSchema-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link MainSchema }
     *     
     */
    public void setMainSchema(MainSchema value) {
        this.mainSchema = value;
    }

    /**
     * Ruft den Wert der jobName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * Legt den Wert der jobName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJobName(String value) {
        this.jobName = value;
    }

    /**
     * Ruft den Wert der processName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcessName() {
        return processName;
    }

    /**
     * Legt den Wert der processName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcessName(String value) {
        this.processName = value;
    }

    /**
     * Ruft den Wert der transformationName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransformationName() {
        return transformationName;
    }

    /**
     * Legt den Wert der transformationName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransformationName(String value) {
        this.transformationName = value;
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

}
