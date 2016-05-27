/**************************************************************************
 * NotImplementedException.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.harmony.exception;

/**
 * Exception thrown when a feature hasn't been implemented.
 */
public class NotImplementedException extends RuntimeException {
    /**
     * Serial Version UID.
     */
    private static final long    serialVersionUID    = 6531619427489977103L;

    /**
     * Constructor.
     * @param message The exception message
     */
    public NotImplementedException(String message) {
        super(message);
    }

    /**
     * Constructor.
     * @param message The exception message
     * @param throwable The associated throwable
     */
    public NotImplementedException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

