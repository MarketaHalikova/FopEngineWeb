//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2018.08.13 um 03:51:00 PM CEST 
//


package de.irs.fopengine.fopengineweb.xml.generated;

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
 *         &lt;element name="Fop" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *                   &lt;element ref="{}Module"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="pdflib-enabled" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                 &lt;attribute name="pdflib-version" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{}Modules" minOccurs="0"/>
 *         &lt;element ref="{}Entries" minOccurs="0"/>
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
    "fop",
    "modules",
    "entries"
})
@XmlRootElement(name = "Settings")
public class Settings {

    @XmlElement(name = "Fop")
    protected Settings.Fop fop;
    @XmlElement(name = "Modules")
    protected Modules modules;
    @XmlElement(name = "Entries")
    protected Entries entries;

    /**
     * Ruft den Wert der fop-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Settings.Fop }
     *     
     */
    public Settings.Fop getFop() {
        return fop;
    }

    /**
     * Legt den Wert der fop-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Settings.Fop }
     *     
     */
    public void setFop(Settings.Fop value) {
        this.fop = value;
    }

    /**
     * Ruft den Wert der modules-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Modules }
     *     
     */
    public Modules getModules() {
        return modules;
    }

    /**
     * Legt den Wert der modules-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Modules }
     *     
     */
    public void setModules(Modules value) {
        this.modules = value;
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
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence maxOccurs="unbounded" minOccurs="0">
     *         &lt;element ref="{}Module"/>
     *       &lt;/sequence>
     *       &lt;attribute name="pdflib-enabled" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *       &lt;attribute name="pdflib-version" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "module"
    })
    public static class Fop {

        @XmlElement(name = "Module")
        protected List<Module> module;
        @XmlAttribute(name = "pdflib-enabled", required = true)
        protected boolean pdflibEnabled;
        @XmlAttribute(name = "pdflib-version")
        protected String pdflibVersion;

        /**
         * Gets the value of the module property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the module property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getModule().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Module }
         * 
         * 
         */
        public List<Module> getModule() {
            if (module == null) {
                module = new ArrayList<Module>();
            }
            return this.module;
        }

        /**
         * Ruft den Wert der pdflibEnabled-Eigenschaft ab.
         * 
         */
        public boolean isPdflibEnabled() {
            return pdflibEnabled;
        }

        /**
         * Legt den Wert der pdflibEnabled-Eigenschaft fest.
         * 
         */
        public void setPdflibEnabled(boolean value) {
            this.pdflibEnabled = value;
        }

        /**
         * Ruft den Wert der pdflibVersion-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPdflibVersion() {
            return pdflibVersion;
        }

        /**
         * Legt den Wert der pdflibVersion-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPdflibVersion(String value) {
            this.pdflibVersion = value;
        }

    }

}
