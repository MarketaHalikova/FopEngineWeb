package com.marketahalikova.fopengineweb.exceptions;

import javax.xml.xpath.XPathExpressionException;

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
