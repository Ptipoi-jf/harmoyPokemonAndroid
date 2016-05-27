/**************************************************************************
 * ObjetsTestProviderBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.test.base;

import android.test.suitebuilder.annotation.SmallTest;

import com.kerhomjarnoin.pokemon.provider.ObjetsProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.utils.ObjetsProviderUtils;
import com.kerhomjarnoin.pokemon.provider.contract.ObjetsContract;

import com.kerhomjarnoin.pokemon.data.ObjetsSQLiteAdapter;

import com.kerhomjarnoin.pokemon.entity.Objets;

import com.kerhomjarnoin.pokemon.fixture.ObjetsDataLoader;

import java.util.ArrayList;
import com.kerhomjarnoin.pokemon.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** Objets database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit ObjetsTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class ObjetsTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected ObjetsSQLiteAdapter adapter;

    protected Objets entity;
    protected ContentResolver provider;
    protected ObjetsProviderUtils providerUtils;

    protected ArrayList<Objets> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new ObjetsSQLiteAdapter(this.ctx);

        this.entities = new ArrayList<Objets>();
        this.entities.addAll(ObjetsDataLoader.getInstance(this.ctx).getMap().values());
        if (this.entities.size()>0) {
            this.entity = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
        }

        this.nbEntities += ObjetsDataLoader.getInstance(this.ctx).getMap().size();
        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new ObjetsProviderUtils(this.getContext());
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /********** Direct Provider calls. *******/

    /** Test case Create Entity */
    @SmallTest
    public void testCreate() {
        Uri result = null;
        if (this.entity != null) {
            Objets objets = ObjetsUtils.generateRandom(this.ctx);

            try {
                ContentValues values = ObjetsContract.itemToContentValues(objets, 0);
                values.remove(ObjetsContract.COL_ID);
                result = this.provider.insert(ObjetsProviderAdapter.OBJETS_URI, values);

            } catch (Exception e) {
                e.printStackTrace();
            }

            Assert.assertNotNull(result);
            Assert.assertTrue(Integer.parseInt(result.getPathSegments().get(1)) > 0);        
            
        }
    }

    /** Test case Read Entity */
    @SmallTest
    public void testRead() {
        Objets result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        ObjetsProviderAdapter.OBJETS_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = ObjetsContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            ObjetsUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<Objets> result = null;
        try {
            android.database.Cursor c = this.provider.query(ObjetsProviderAdapter.OBJETS_URI, this.adapter.getCols(), null, null, null);
            result = ObjetsContract.cursorToItems(c);
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(result);
        if (result != null) {
            Assert.assertEquals(result.size(), this.nbEntities);
        }
    }

    /** Test case Update Entity */
    @SmallTest
    public void testUpdate() {
        int result = -1;
        if (this.entity != null) {
            Objets objets = ObjetsUtils.generateRandom(this.ctx);

            try {
                objets.setId(this.entity.getId());

                ContentValues values = ObjetsContract.itemToContentValues(objets, 0);
                result = this.provider.update(
                    Uri.parse(ObjetsProviderAdapter.OBJETS_URI
                        + "/"
                        + objets.getId()),
                    values,
                    null,
                    null);

            } catch (Exception e) {
                e.printStackTrace();
            }

            Assert.assertTrue(result > 0);
        }
    }

    /** Test case UpdateAll Entity */
    @SmallTest
    public void testUpdateAll() {
        int result = -1;
        if (this.entities != null) {
            if (this.entities.size() > 0) {
                Objets objets = ObjetsUtils.generateRandom(this.ctx);
    
                try {
                    ContentValues values = ObjetsContract.itemToContentValues(objets, 0);
                    values.remove(ObjetsContract.COL_ID);
    
                    result = this.provider.update(ObjetsProviderAdapter.OBJETS_URI, values, null, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
    
                Assert.assertEquals(result, this.nbEntities);
            }
        }
    }

    /** Test case Delete Entity */
    @SmallTest
    public void testDelete() {
        int result = -1;
        if (this.entity != null) {
            try {
                result = this.provider.delete(
                        Uri.parse(ObjetsProviderAdapter.OBJETS_URI
                            + "/" 
                            + this.entity.getId()),
                        null,
                        null);

            } catch (Exception e) {
                e.printStackTrace();
            }
            Assert.assertTrue(result >= 0);
        }

    }

    /** Test case DeleteAll Entity */
    @SmallTest
    public void testDeleteAll() {
        int result = -1;
        if (this.entities != null) {
            if (this.entities.size() > 0) {
    
                try {
                    result = this.provider.delete(ObjetsProviderAdapter.OBJETS_URI, null, null);
    
                } catch (Exception e) {
                    e.printStackTrace();
                }
    
                Assert.assertEquals(result, this.nbEntities);
            }
        }
    }

    /****** Provider Utils calls ********/

    /** Test case Read Entity by provider utils. */
    @SmallTest
    public void testUtilsRead() {
        Objets result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            ObjetsUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<Objets> result = null;
        result = this.providerUtils.queryAll();

        Assert.assertNotNull(result);
        if (result != null) {
            Assert.assertEquals(result.size(), this.nbEntities);
        }
    }

    /** Test case Update Entity by provider utils. */
    @SmallTest
    public void testUtilsUpdate() {
        int result = -1;
        if (this.entity != null) {
            Objets objets = ObjetsUtils.generateRandom(this.ctx);

            objets.setId(this.entity.getId());
            result = this.providerUtils.update(objets);

            Assert.assertTrue(result > 0);
        }
    }


    /** Test case Delete Entity by provider utils. */
    @SmallTest
    public void testUtilsDelete() {
        int result = -1;
        if (this.entity != null) {
            result = this.providerUtils.delete(this.entity);
            Assert.assertTrue(result >= 0);
        }

    }
}
