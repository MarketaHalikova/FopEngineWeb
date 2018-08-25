package com.marketahalikova.fopengineweb.exceptions;

public class GitException extends Exception {

    public GitException(String message) {
        super(message);
    }

    public GitException(Throwable cause) {
        super(cause);
    }
}