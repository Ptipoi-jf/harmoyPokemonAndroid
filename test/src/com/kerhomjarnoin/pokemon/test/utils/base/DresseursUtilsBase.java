/**************************************************************************
 * DresseursUtilsBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.test.utils.base;


import junit.framework.Assert;
import com.kerhomjarnoin.pokemon.entity.Dresseurs;



import com.kerhomjarnoin.pokemon.test.utils.TestUtils;
import com.kerhomjarnoin.pokemon.entity.Npc;
import com.kerhomjarnoin.pokemon.fixture.NpcDataLoader;


import java.util.ArrayList;

public abstract class DresseursUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Dresseurs generateRandom(android.content.Context ctx){
        Dresseurs dresseurs = new Dresseurs();

        dresseurs.setId(TestUtils.generateRandomInt(0,100) + 1);
        dresseurs.setNom("nom_"+TestUtils.generateRandomString(10));
        ArrayList<Npc> npcs =
            new ArrayList<Npc>();
        npcs.addAll(NpcDataLoader.getInstance(ctx).getMap().values());
        if (!npcs.isEmpty()) {
            dresseurs.setNpc(npcs.get(TestUtils.generateRandomInt(0, npcs.size())));
        }

        return dresseurs;
    }

    public static boolean equals(Dresseurs dresseurs1,
            Dresseurs dresseurs2){
        return equals(dresseurs1, dresseurs2, true);
    }
    
    public static boolean equals(Dresseurs dresseurs1,
            Dresseurs dresseurs2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(dresseurs1);
        Assert.assertNotNull(dresseurs2);
        if (dresseurs1!=null && dresseurs2 !=null){
            Assert.assertEquals(dresseurs1.getId(), dresseurs2.getId());
            Assert.assertEquals(dresseurs1.getNom(), dresseurs2.getNom());
            if (dresseurs1.getNpc() != null
                    && dresseurs2.getNpc() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(dresseurs1.getNpc().getId(),
                            dresseurs2.getNpc().getId());
                }
            }
        }

        return ret;
    }
}

