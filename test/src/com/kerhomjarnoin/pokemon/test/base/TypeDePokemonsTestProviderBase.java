/**************************************************************************
 * TypeDePokemonsTestProviderBase.java, pokemon Android
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

import com.kerhomjarnoin.pokemon.provider.TypeDePokemonsProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.utils.TypeDePokemonsProviderUtils;
import com.kerhomjarnoin.pokemon.provider.contract.TypeDePokemonsContract;

import com.kerhomjarnoin.pokemon.data.TypeDePokemonsSQLiteAdapter;

import com.kerhomjarnoin.pokemon.entity.TypeDePokemons;

import com.kerhomjarnoin.pokemon.fixture.TypeDePokemonsDataLoader;

import java.util.ArrayList;
import com.kerhomjarnoin.pokemon.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** TypeDePokemons database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit TypeDePokemonsTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class TypeDePokemonsTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected TypeDePokemonsSQLiteAdapter adapter;

    protected TypeDePokemons entity;
    protected ContentResolver provider;
    protected TypeDePokemonsProviderUtils providerUtils;

    protected ArrayList<TypeDePokemons> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new TypeDePokemonsSQLiteAdapter(this.ctx);

        this.entities = new ArrayList<TypeDePokemons>();
        this.entities.addAll(TypeDePokemonsDataLoader.getInstance(this.ctx).getMap().values());
        if (this.entities.size()>0) {
            this.entity = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
        }

        this.nbEntities += TypeDePokemonsDataLoader.getInstance(this.ctx).getMap().size();
        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new TypeDePokemonsProviderUtils(this.getContext());
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
            TypeDePokemons typeDePokemons = TypeDePokemonsUtils.generateRandom(this.ctx);

            try {
                ContentValues values = TypeDePokemonsContract.itemToContentValues(typeDePokemons);
                values.remove(TypeDePokemonsContract.COL_ID);
                result = this.provider.insert(TypeDePokemonsProviderAdapter.TYPEDEPOKEMONS_URI, values);

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
        TypeDePokemons result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        TypeDePokemonsProviderAdapter.TYPEDEPOKEMONS_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = TypeDePokemonsContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            TypeDePokemonsUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<TypeDePokemons> result = null;
        try {
            android.database.Cursor c = this.provider.query(TypeDePokemonsProviderAdapter.TYPEDEPOKEMONS_URI, this.adapter.getCols(), null, null, null);
            result = TypeDePokemonsContract.cursorToItems(c);
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
            TypeDePokemons typeDePokemons = TypeDePokemonsUtils.generateRandom(this.ctx);

            try {
                typeDePokemons.setId(this.entity.getId());

                ContentValues values = TypeDePokemonsContract.itemToContentValues(typeDePokemons);
                result = this.provider.update(
                    Uri.parse(TypeDePokemonsProviderAdapter.TYPEDEPOKEMONS_URI
                        + "/"
                        + typeDePokemons.getId()),
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
                TypeDePokemons typeDePokemons = TypeDePokemonsUtils.generateRandom(this.ctx);
    
                try {
                    ContentValues values = TypeDePokemonsContract.itemToContentValues(typeDePokemons);
                    values.remove(TypeDePokemonsContract.COL_ID);
    
                    result = this.provider.update(TypeDePokemonsProviderAdapter.TYPEDEPOKEMONS_URI, values, null, null);
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
                        Uri.parse(TypeDePokemonsProviderAdapter.TYPEDEPOKEMONS_URI
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
                    result = this.provider.delete(TypeDePokemonsProviderAdapter.TYPEDEPOKEMONS_URI, null, null);
    
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
        TypeDePokemons result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            TypeDePokemonsUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<TypeDePokemons> result = null;
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
            TypeDePokemons typeDePokemons = TypeDePokemonsUtils.generateRandom(this.ctx);

            typeDePokemons.setId(this.entity.getId());
            result = this.providerUtils.update(typeDePokemons);

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
