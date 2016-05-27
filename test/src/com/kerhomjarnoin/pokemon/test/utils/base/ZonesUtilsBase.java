/**************************************************************************
 * ZonesUtilsBase.java, pokemon Android
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
import com.kerhomjarnoin.pokemon.entity.Zones;



import com.kerhomjarnoin.pokemon.test.utils.TestUtils;


public abstract class ZonesUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Zones generateRandom(android.content.Context ctx){
        Zones zones = new Zones();

        zones.setId(TestUtils.generateRandomInt(0,100) + 1);
        zones.setNom("nom_"+TestUtils.generateRandomString(10));

        return zones;
    }

    public static boolean equals(Zones zones1,
            Zones zones2){
        return equals(zones1, zones2, true);
    }
    
    public static boolean equals(Zones zones1,
            Zones zones2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(zones1);
        Assert.assertNotNull(zones2);
        if (zones1!=null && zones2 !=null){
            Assert.assertEquals(zones1.getId(), zones2.getId());
            Assert.assertEquals(zones1.getNom(), zones2.getNom());
        }

        return ret;
    }
}

