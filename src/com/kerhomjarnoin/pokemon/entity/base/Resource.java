

/**************************************************************************
 * Resource.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.entity.base;

import java.io.Serializable;

import org.joda.time.DateTime;

public interface Resource {
    /**
     * @return the id
     */
    int getId();

    /**
     * @param value the id to set
     */
    void setId(final int value);

    /**
     * @return the path
     */
    String getPath();

    /**
     * @param value the path to set
     */
    void setPath(final String value);
}