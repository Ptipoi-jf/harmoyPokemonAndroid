/**************************************************************************
 * TypeObjetTestProviderBase.java, pokemon Android
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

import com.kerhomjarnoin.pokemon.provider.TypeObjetProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.utils.TypeObjetProviderUtils;
import com.kerhomjarnoin.pokemon.provider.contract.TypeObjetContract;

import com.kerhomjarnoin.pokemon.data.TypeObjetSQLiteAdapter;

import com.kerhomjarnoin.pokemon.entity.TypeObjet;

import com.kerhomjarnoin.pokemon.fixture.TypeObjetDataLoader;

import java.util.ArrayList;
import com.kerhomjarnoin.pokemon.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** TypeObjet database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit TypeObjetTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class TypeObjetTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected TypeObjetSQLiteAdapter adapter;

    protected TypeObjet entity;
    protected ContentResolver provider;
    protected TypeObjetProviderUtils providerUtils;

    protected ArrayList<TypeObjet> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new TypeObjetSQLiteAdapter(this.ctx);

        this.entities = new ArrayList<TypeObjet>();
        this.entities.addAll(TypeObjetDataLoader.getInstance(this.ctx).getMap().values());
        if (this.entities.size()>0) {
            this.entity = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
        }

        this.nbEntities += TypeObjetDataLoader.getInstance(this.ctx).getMap().size();
        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new TypeObjetProviderUtils(this.getContext());
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
            TypeObjet typeObjet = TypeObjetUtils.generateRandom(this.ctx);

            try {
                ContentValues values = TypeObjetContract.itemToContentValues(typeObjet);
                values.remove(TypeObjetContract.COL_ID);
                result = this.provider.insert(TypeObjetProviderAdapter.TYPEOBJET_URI, values);

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
        TypeObjet result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        TypeObjetProviderAdapter.TYPEOBJET_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = TypeObjetContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            TypeObjetUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<TypeObjet> result = null;
        try {
            android.database.Cursor c = this.provider.query(TypeObjetProviderAdapter.TYPEOBJET_URI, this.adapter.getCols(), null, null, null);
            result = TypeObjetContract.cursorToItems(c);
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
            TypeObjet typeObjet = TypeObjetUtils.generateRandom(this.ctx);

            try {
                typeObjet.setId(this.entity.getId());

                ContentValues values = TypeObjetContract.itemToContentValues(typeObjet);
                result = this.provider.update(
                    Uri.parse(TypeObjetProviderAdapter.TYPEOBJET_URI
                        + "/"
                        + typeObjet.getId()),
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
                TypeObjet typeObjet = TypeObjetUtils.generateRandom(this.ctx);
    
                try {
                    ContentValues values = TypeObjetContract.itemToContentValues(typeObjet);
                    values.remove(TypeObjetContract.COL_ID);
    
                    result = this.provider.update(TypeObjetProviderAdapter.TYPEOBJET_URI, values, null, null);
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
                        Uri.parse(TypeObjetProviderAdapter.TYPEOBJET_URI
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
                    result = this.provider.delete(TypeObjetProviderAdapter.TYPEOBJET_URI, null, null);
    
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
        TypeObjet result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            TypeObjetUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<TypeObjet> result = null;
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
            TypeObjet typeObjet = TypeObjetUtils.generateRandom(this.ctx);

            typeObjet.setId(this.entity.getId());
            result = this.providerUtils.update(typeObjet);

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
