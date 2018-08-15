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
 *         &lt;element ref="{}Auxiliary-services" minOccurs="0"/>
 *         &lt;element ref="{}Entries" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="service-key" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="builder-class" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "auxiliaryServices",
    "entries"
})
@XmlRootElement(name = "Service")
public class Service {

    @XmlElement(name = "Auxiliary-services")
    protected AuxiliaryServices auxiliaryServices;
    @XmlElement(name = "Entries")
    protected Entries entries;
    @XmlAttribute(name = "service-key", required = true)
    protected String serviceKey;
    @XmlAttribute(name = "builder-class")
    protected String builderClass;

    /**
     * Ruft den Wert der auxiliaryServices-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link AuxiliaryServices }
     *     
     */
    public AuxiliaryServices getAuxiliaryServices() {
        return auxiliaryServices;
    }

    /**
     * Legt den Wert der auxiliaryServices-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link AuxiliaryServices }
     *     
     */
    public void setAuxiliaryServices(AuxiliaryServices value) {
        this.auxiliaryServices = value;
    }

    /**
     * Ruft den Wert der entries-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Entries }
     *     
     */
    public Entries getEntries() {
        return entries;
    }

    /**
     * Legt den Wert der entries-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Entries }
     *     
     */
    public void setEntries(Entries value) {
        this.entries = value;
    }

    /**
     * Ruft den Wert der serviceKey-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceKey() {
        return serviceKey;
    }

    /**
     * Legt den Wert der serviceKey-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceKey(String value) {
        this.serviceKey = value;
    }

    /**
     * Ruft den Wert der builderClass-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBuilderClass() {
        return builderClass;
    }

    /**
     * Legt den Wert der builderClass-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBuilderClass(String value) {
        this.builderClass = value;
    }

}
