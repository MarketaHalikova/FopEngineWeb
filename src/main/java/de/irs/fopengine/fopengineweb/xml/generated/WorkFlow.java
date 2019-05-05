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
 *         &lt;element ref="{}Processes"/>
 *         &lt;element ref="{}Steps" minOccurs="0"/>
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
    "processes",
    "steps"
})
@XmlRootElement(name = "WorkFlow")
public class WorkFlow {

    @XmlElement(name = "Processes", required = true)
    protected Processes processes;
    @XmlElement(name = "Steps")
    protected Steps steps;

    /**
     * Ruft den Wert der processes-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Processes }
     *     
     */
    public Processes getProcesses() {
        return processes;
    }

    /**
     * Legt den Wert der processes-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Processes }
     *     
     */
    public void setProcesses(Processes value) {
        this.processes = value;
    }

    /**
     * Ruft den Wert der steps-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Steps }
     *     
     */
    public Steps getSteps() {
        return steps;
    }

    /**
     * Legt den Wert der steps-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Steps }
     *     
     */
    public void setSteps(Steps value) {
        this.steps = value;
    }

}
