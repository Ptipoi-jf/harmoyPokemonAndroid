/**************************************************************************
 * NpcTestProviderBase.java, pokemon Android
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

import com.kerhomjarnoin.pokemon.provider.NpcProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.utils.NpcProviderUtils;
import com.kerhomjarnoin.pokemon.provider.contract.NpcContract;

import com.kerhomjarnoin.pokemon.data.NpcSQLiteAdapter;

import com.kerhomjarnoin.pokemon.entity.Npc;
import com.kerhomjarnoin.pokemon.entity.Badges;

import com.kerhomjarnoin.pokemon.fixture.NpcDataLoader;

import java.util.ArrayList;
import com.kerhomjarnoin.pokemon.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** Npc database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit NpcTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class NpcTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected NpcSQLiteAdapter adapter;

    protected Npc entity;
    protected ContentResolver provider;
    protected NpcProviderUtils providerUtils;

    protected ArrayList<Npc> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new NpcSQLiteAdapter(this.ctx);

        this.entities = new ArrayList<Npc>();
        this.entities.addAll(NpcDataLoader.getInstance(this.ctx).getMap().values());
        if (this.entities.size()>0) {
            this.entity = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
        }

        this.nbEntities += NpcDataLoader.getInstance(this.ctx).getMap().size();
        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new NpcProviderUtils(this.getContext());
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
            Npc npc = NpcUtils.generateRandom(this.ctx);

            try {
                ContentValues values = NpcContract.itemToContentValues(npc);
                values.remove(NpcContract.COL_ID);
                result = this.provider.insert(NpcProviderAdapter.NPC_URI, values);

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
        Npc result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        NpcProviderAdapter.NPC_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = NpcContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            NpcUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<Npc> result = null;
        try {
            android.database.Cursor c = this.provider.query(NpcProviderAdapter.NPC_URI, this.adapter.getCols(), null, null, null);
            result = NpcContract.cursorToItems(c);
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
            Npc npc = NpcUtils.generateRandom(this.ctx);

            try {
                npc.setId(this.entity.getId());
                if (this.entity.getBadges() != null) {
                    npc.getBadges().addAll(this.entity.getBadges());
                }

                ContentValues values = NpcContract.itemToContentValues(npc);
                result = this.provider.update(
                    Uri.parse(NpcProviderAdapter.NPC_URI
                        + "/"
                        + npc.getId()),
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
                Npc npc = NpcUtils.generateRandom(this.ctx);
    
                try {
                    ContentValues values = NpcContract.itemToContentValues(npc);
                    values.remove(NpcContract.COL_ID);
    
                    result = this.provider.update(NpcProviderAdapter.NPC_URI, values, null, null);
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
                        Uri.parse(NpcProviderAdapter.NPC_URI
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
                    result = this.provider.delete(NpcProviderAdapter.NPC_URI, null, null);
    
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
        Npc result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            NpcUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<Npc> result = null;
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
            Npc npc = NpcUtils.generateRandom(this.ctx);

            npc.setId(this.entity.getId());
            if (this.entity.getBadges() != null) {
                for (Badges badges : this.entity.getBadges()) {
                    boolean found = false;
                    for (Badges badges2 : npc.getBadges()) {
                        if (badges.getId() == badges2.getId() ) {
                            found = true;
                            break;
                        }
                    }                    
                    if(!found) {
                        npc.getBadges().add(badges);
                    }
                }
            }
            result = this.providerUtils.update(npc);

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
