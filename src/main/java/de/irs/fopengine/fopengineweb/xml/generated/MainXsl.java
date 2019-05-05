//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2018.08.13 um 03:51:00 PM CEST 
//


package de.irs.fopengine.fopengineweb.xml.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element ref="{}File"/>
 *         &lt;element ref="{}Xsls" minOccurs="0"/>
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
    "xsls"
})
@XmlRootElement(name = "Main-xsl")
public class MainXsl {

    @XmlElement(name = "File", required = true)
    protected File file;
    @XmlElement(name = "Xsls")
    protected Xsls xsls;

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
     * Ruft den Wert der xsls-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Xsls }
     *     
     */
    public Xsls getXsls() {
        return xsls;
    }

    /**
     * Legt den Wert der xsls-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Xsls }
     *     
     */
    public void setXsls(Xsls value) {
        this.xsls = value;
    }

}
