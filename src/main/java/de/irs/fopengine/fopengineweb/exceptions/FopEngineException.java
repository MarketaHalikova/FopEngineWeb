package de.irs.fopengine.fopengineweb.exceptions;

/**
 * Exception from FopEngine
 */
public class FopEngineException extends Exception{

    public FopEngineException(String message) {
        super(message);
    }

    public FopEngineException(Throwable cause) {
        super(cause);
    }

    public FopEngineException(String message, Throwable cause) {
        super(message, cause);
    }

}