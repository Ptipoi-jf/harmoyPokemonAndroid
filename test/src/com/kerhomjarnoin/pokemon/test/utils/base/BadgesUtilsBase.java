/**************************************************************************
 * BadgesUtilsBase.java, pokemon Android
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
import com.kerhomjarnoin.pokemon.entity.Badges;



import com.kerhomjarnoin.pokemon.test.utils.TestUtils;


public abstract class BadgesUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Badges generateRandom(android.content.Context ctx){
        Badges badges = new Badges();

        badges.setId(TestUtils.generateRandomInt(0,100) + 1);
        badges.setNom("nom_"+TestUtils.generateRandomString(10));

        return badges;
    }

    public static boolean equals(Badges badges1,
            Badges badges2){
        return equals(badges1, badges2, true);
    }
    
    public static boolean equals(Badges badges1,
            Badges badges2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(badges1);
        Assert.assertNotNull(badges2);
        if (badges1!=null && badges2 !=null){
            Assert.assertEquals(badges1.getId(), badges2.getId());
            Assert.assertEquals(badges1.getNom(), badges2.getNom());
        }

        return ret;
    }
}

