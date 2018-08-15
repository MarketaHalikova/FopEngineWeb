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
 *         &lt;element name="Preprocessors" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{}Processor" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{}Transformers" minOccurs="0"/>
 *         &lt;element ref="{}Postprocessors" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="step-key" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="output-file-mask" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="input-type" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="file"/>
 *             &lt;enumeration value="source"/>
 *             &lt;enumeration value="string"/>
 *             &lt;enumeration value="internal"/>
 *             &lt;enumeration value="output_stream"/>
 *             &lt;enumeration value="input_stream"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="output-type" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="file"/>
 *             &lt;enumeration value="output_stream"/>
 *             &lt;enumeration value="string"/>
 *             &lt;enumeration value="internal"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "preprocessors",
    "transformers",
    "postprocessors"
})
@XmlRootElement(name = "Step")
public class Step {

    @XmlElement(name = "Preprocessors")
    protected Step.Preprocessors preprocessors;
    @XmlElement(name = "Transformers")
    protected Transformers transformers;
    @XmlElement(name = "Postprocessors")
    protected Postprocessors postprocessors;
    @XmlAttribute(name = "step-key", required = true)
    protected String stepKey;
    @XmlAttribute(name = "output-file-mask")
    @XmlSchemaType(name = "anySimpleType")
    protected String outputFileMask;
    @XmlAttribute(name = "input-type", required = true)
    protected String inputType;
    @XmlAttribute(name = "output-type", required = true)
    protected String outputType;

    /**
     * Ruft den Wert der preprocessors-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Step.Preprocessors }
     *     
     */
    public Step.Preprocessors getPreprocessors() {
        return preprocessors;
    }

    /**
     * Legt den Wert der preprocessors-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Step.Preprocessors }
     *     
     */
    public void setPreprocessors(Step.Preprocessors value) {
        this.preprocessors = value;
    }

    /**
     * Ruft den Wert der transformers-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Transformers }
     *     
     */
    public Transformers getTransformers() {
        return transformers;
    }

    /**
     * Legt den Wert der transformers-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Transformers }
     *     
     */
    public void setTransformers(Transformers value) {
        this.transformers = value;
    }

    /**
     * Ruft den Wert der postprocessors-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Postprocessors }
     *     
     */
    public Postprocessors getPostprocessors() {
        return postprocessors;
    }

    /**
     * Legt den Wert der postprocessors-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Postprocessors }
     *     
     */
    public void setPostprocessors(Postprocessors value) {
        this.postprocessors = value;
    }

    /**
     * Ruft den Wert der stepKey-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStepKey() {
        return stepKey;
    }

    /**
     * Legt den Wert der stepKey-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStepKey(String value) {
        this.stepKey = value;
    }

    /**
     * Ruft den Wert der outputFileMask-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutputFileMask() {
        return outputFileMask;
    }

    /**
     * Legt den Wert der outputFileMask-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutputFileMask(String value) {
        this.outputFileMask = value;
    }

    /**
     * Ruft den Wert der inputType-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInputType() {
        return inputType;
    }

    /**
     * Legt den Wert der inputType-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInputType(String value) {
        this.inputType = value;
    }

    /**
     * Ruft den Wert der outputType-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutputType() {
        return outputType;
    }

    /**
     * Legt den Wert der outputType-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutputType(String value) {
        this.outputType = value;
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
     *         &lt;element ref="{}Processor" maxOccurs="unbounded"/>
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
        "processor"
    })
    public static class Preprocessors {

        @XmlElement(name = "Processor", required = true)
        protected List<Processor> processor;

        /**
         * Gets the value of the processor property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the processor property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getProcessor().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Processor }
         * 
         * 
         */
        public List<Processor> getProcessor() {
            if (processor == null) {
                processor = new ArrayList<Processor>();
            }
            return this.processor;
        }

    }

}
