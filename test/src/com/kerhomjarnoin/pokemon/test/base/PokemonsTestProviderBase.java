/**************************************************************************
 * PokemonsTestProviderBase.java, pokemon Android
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

import com.kerhomjarnoin.pokemon.provider.PokemonsProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.utils.PokemonsProviderUtils;
import com.kerhomjarnoin.pokemon.provider.contract.PokemonsContract;

import com.kerhomjarnoin.pokemon.data.PokemonsSQLiteAdapter;

import com.kerhomjarnoin.pokemon.entity.Pokemons;

import com.kerhomjarnoin.pokemon.fixture.PokemonsDataLoader;

import java.util.ArrayList;
import com.kerhomjarnoin.pokemon.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** Pokemons database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit PokemonsTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class PokemonsTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected PokemonsSQLiteAdapter adapter;

    protected Pokemons entity;
    protected ContentResolver provider;
    protected PokemonsProviderUtils providerUtils;

    protected ArrayList<Pokemons> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new PokemonsSQLiteAdapter(this.ctx);

        this.entities = new ArrayList<Pokemons>();
        this.entities.addAll(PokemonsDataLoader.getInstance(this.ctx).getMap().values());
        if (this.entities.size()>0) {
            this.entity = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
        }

        this.nbEntities += PokemonsDataLoader.getInstance(this.ctx).getMap().size();
        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new PokemonsProviderUtils(this.getContext());
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
            Pokemons pokemons = PokemonsUtils.generateRandom(this.ctx);

            try {
                ContentValues values = PokemonsContract.itemToContentValues(pokemons, 0);
                values.remove(PokemonsContract.COL_ID);
                result = this.provider.insert(PokemonsProviderAdapter.POKEMONS_URI, values);

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
        Pokemons result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        PokemonsProviderAdapter.POKEMONS_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = PokemonsContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            PokemonsUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<Pokemons> result = null;
        try {
            android.database.Cursor c = this.provider.query(PokemonsProviderAdapter.POKEMONS_URI, this.adapter.getCols(), null, null, null);
            result = PokemonsContract.cursorToItems(c);
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
            Pokemons pokemons = PokemonsUtils.generateRandom(this.ctx);

            try {
                pokemons.setId(this.entity.getId());

                ContentValues values = PokemonsContract.itemToContentValues(pokemons, 0);
                result = this.provider.update(
                    Uri.parse(PokemonsProviderAdapter.POKEMONS_URI
                        + "/"
                        + pokemons.getId()),
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
                Pokemons pokemons = PokemonsUtils.generateRandom(this.ctx);
    
                try {
                    ContentValues values = PokemonsContract.itemToContentValues(pokemons, 0);
                    values.remove(PokemonsContract.COL_ID);
    
                    result = this.provider.update(PokemonsProviderAdapter.POKEMONS_URI, values, null, null);
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
                        Uri.parse(PokemonsProviderAdapter.POKEMONS_URI
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
                    result = this.provider.delete(PokemonsProviderAdapter.POKEMONS_URI, null, null);
    
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
        Pokemons result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            PokemonsUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<Pokemons> result = null;
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
            Pokemons pokemons = PokemonsUtils.generateRandom(this.ctx);

            pokemons.setId(this.entity.getId());
            result = this.providerUtils.update(pokemons);

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
