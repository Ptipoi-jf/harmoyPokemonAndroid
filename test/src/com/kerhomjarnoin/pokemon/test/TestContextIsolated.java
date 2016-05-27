/**************************************************************************
 * TestContextIsolated.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.test;

import android.test.RenamingDelegatingContext;
import android.test.mock.MockContentResolver;

import com.kerhomjarnoin.pokemon.test.base.TestContextIsolatedBase;

/** Context isolated test class. */
public class TestContextIsolated extends TestContextIsolatedBase {

    /**
     * Constructor.
     * @param mockContentResolver {@link MockContentResolver}
     * @param targetContextWrapper {@link RenamingDelegatingContext}
     */
    public TestContextIsolated(MockContentResolver mockContentResolver,
            RenamingDelegatingContext targetContextWrapper) {
        super(mockContentResolver, targetContextWrapper);
    }

}
