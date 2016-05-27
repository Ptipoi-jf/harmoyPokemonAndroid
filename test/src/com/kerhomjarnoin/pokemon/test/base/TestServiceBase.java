/**************************************************************************
 * TestServiceBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.test.base;

import android.app.Service;

import android.test.AndroidTestCase;
import android.test.ServiceTestCase;;


/** Base test abstract class for Service Test <br/>
 * <b><i>This class will be overwrited whenever
 * you regenerate the project with Harmony.</i></b>
 */
public abstract class TestServiceBase<T extends Service>
        extends ServiceTestCase<T> {

    /** The Mocked android {@link android.content.Context}. */
    protected TestContextMock testContextMock;

    protected TestServiceBase(Class<T> serviceClass) {
        super(serviceClass);
        this.testContextMock = new TestContextMock(this);
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        this.testContextMock.setUp();
    }
}
