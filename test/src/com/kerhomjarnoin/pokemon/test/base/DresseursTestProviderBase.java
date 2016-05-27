/**************************************************************************
 * DresseursTestProviderBase.java, pokemon Android
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

import com.kerhomjarnoin.pokemon.provider.DresseursProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.utils.DresseursProviderUtils;
import com.kerhomjarnoin.pokemon.provider.contract.DresseursContract;

import com.kerhomjarnoin.pokemon.data.DresseursSQLiteAdapter;

import com.kerhomjarnoin.pokemon.entity.Dresseurs;

import com.kerhomjarnoin.pokemon.fixture.DresseursDataLoader;

import java.util.ArrayList;
import com.kerhomjarnoin.pokemon.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** Dresseurs database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit DresseursTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class DresseursTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected DresseursSQLiteAdapter adapter;

    protected Dresseurs entity;
    protected ContentResolver provider;
    protected DresseursProviderUtils providerUtils;

    protected ArrayList<Dresseurs> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new DresseursSQLiteAdapter(this.ctx);

        this.entities = new ArrayList<Dresseurs>();
        this.entities.addAll(DresseursDataLoader.getInstance(this.ctx).getMap().values());
        if (this.entities.size()>0) {
            this.entity = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
        }

        this.nbEntities += DresseursDataLoader.getInstance(this.ctx).getMap().size();
        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new DresseursProviderUtils(this.getContext());
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
            Dresseurs dresseurs = DresseursUtils.generateRandom(this.ctx);

            try {
                ContentValues values = DresseursContract.itemToContentValues(dresseurs);
                values.remove(DresseursContract.COL_ID);
                result = this.provider.insert(DresseursProviderAdapter.DRESSEURS_URI, values);

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
        Dresseurs result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        DresseursProviderAdapter.DRESSEURS_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = DresseursContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            DresseursUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<Dresseurs> result = null;
        try {
            android.database.Cursor c = this.provider.query(DresseursProviderAdapter.DRESSEURS_URI, this.adapter.getCols(), null, null, null);
            result = DresseursContract.cursorToItems(c);
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
            Dresseurs dresseurs = DresseursUtils.generateRandom(this.ctx);

            try {
                dresseurs.setId(this.entity.getId());

                ContentValues values = DresseursContract.itemToContentValues(dresseurs);
                result = this.provider.update(
                    Uri.parse(DresseursProviderAdapter.DRESSEURS_URI
                        + "/"
                        + dresseurs.getId()),
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
                Dresseurs dresseurs = DresseursUtils.generateRandom(this.ctx);
    
                try {
                    ContentValues values = DresseursContract.itemToContentValues(dresseurs);
                    values.remove(DresseursContract.COL_ID);
    
                    result = this.provider.update(DresseursProviderAdapter.DRESSEURS_URI, values, null, null);
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
                        Uri.parse(DresseursProviderAdapter.DRESSEURS_URI
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
                    result = this.provider.delete(DresseursProviderAdapter.DRESSEURS_URI, null, null);
    
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
        Dresseurs result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            DresseursUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<Dresseurs> result = null;
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
            Dresseurs dresseurs = DresseursUtils.generateRandom(this.ctx);

            dresseurs.setId(this.entity.getId());
            result = this.providerUtils.update(dresseurs);

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
