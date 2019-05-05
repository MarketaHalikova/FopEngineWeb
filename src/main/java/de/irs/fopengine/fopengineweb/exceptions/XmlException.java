package de.irs.fopengine.fopengineweb.exceptions;

import javax.xml.xpath.XPathExpressionException;

/**
 * Exception by marshaling or unmarshaling of xml files
 */
public class XmlException extends Exception {

    public XmlException(XPathExpressionException e) {
        super();
    }

    public XmlException(String message) {
        super(message);
    }

    public XmlException(String message, Throwable cause) {
        super(message, cause);
    }
}
