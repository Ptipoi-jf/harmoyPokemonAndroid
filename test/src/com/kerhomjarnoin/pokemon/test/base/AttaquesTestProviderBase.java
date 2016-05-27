/**************************************************************************
 * AttaquesTestProviderBase.java, pokemon Android
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

import com.kerhomjarnoin.pokemon.provider.AttaquesProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.utils.AttaquesProviderUtils;
import com.kerhomjarnoin.pokemon.provider.contract.AttaquesContract;

import com.kerhomjarnoin.pokemon.data.AttaquesSQLiteAdapter;

import com.kerhomjarnoin.pokemon.entity.Attaques;

import com.kerhomjarnoin.pokemon.fixture.AttaquesDataLoader;

import java.util.ArrayList;
import com.kerhomjarnoin.pokemon.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** Attaques database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit AttaquesTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class AttaquesTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected AttaquesSQLiteAdapter adapter;

    protected Attaques entity;
    protected ContentResolver provider;
    protected AttaquesProviderUtils providerUtils;

    protected ArrayList<Attaques> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new AttaquesSQLiteAdapter(this.ctx);

        this.entities = new ArrayList<Attaques>();
        this.entities.addAll(AttaquesDataLoader.getInstance(this.ctx).getMap().values());
        if (this.entities.size()>0) {
            this.entity = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
        }

        this.nbEntities += AttaquesDataLoader.getInstance(this.ctx).getMap().size();
        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new AttaquesProviderUtils(this.getContext());
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
            Attaques attaques = AttaquesUtils.generateRandom(this.ctx);

            try {
                ContentValues values = AttaquesContract.itemToContentValues(attaques);
                values.remove(AttaquesContract.COL_ID);
                result = this.provider.insert(AttaquesProviderAdapter.ATTAQUES_URI, values);

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
        Attaques result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        AttaquesProviderAdapter.ATTAQUES_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = AttaquesContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            AttaquesUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<Attaques> result = null;
        try {
            android.database.Cursor c = this.provider.query(AttaquesProviderAdapter.ATTAQUES_URI, this.adapter.getCols(), null, null, null);
            result = AttaquesContract.cursorToItems(c);
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
            Attaques attaques = AttaquesUtils.generateRandom(this.ctx);

            try {
                attaques.setId(this.entity.getId());

                ContentValues values = AttaquesContract.itemToContentValues(attaques);
                result = this.provider.update(
                    Uri.parse(AttaquesProviderAdapter.ATTAQUES_URI
                        + "/"
                        + attaques.getId()),
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
                Attaques attaques = AttaquesUtils.generateRandom(this.ctx);
    
                try {
                    ContentValues values = AttaquesContract.itemToContentValues(attaques);
                    values.remove(AttaquesContract.COL_ID);
    
                    result = this.provider.update(AttaquesProviderAdapter.ATTAQUES_URI, values, null, null);
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
                        Uri.parse(AttaquesProviderAdapter.ATTAQUES_URI
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
                    result = this.provider.delete(AttaquesProviderAdapter.ATTAQUES_URI, null, null);
    
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
        Attaques result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            AttaquesUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<Attaques> result = null;
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
            Attaques attaques = AttaquesUtils.generateRandom(this.ctx);

            attaques.setId(this.entity.getId());
            result = this.providerUtils.update(attaques);

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
