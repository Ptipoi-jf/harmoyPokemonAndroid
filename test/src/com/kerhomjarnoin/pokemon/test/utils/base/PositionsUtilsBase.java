/**************************************************************************
 * PositionsUtilsBase.java, pokemon Android
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
import com.kerhomjarnoin.pokemon.entity.Positions;



import com.kerhomjarnoin.pokemon.test.utils.TestUtils;


public abstract class PositionsUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Positions generateRandom(android.content.Context ctx){
        Positions positions = new Positions();

        positions.setId(TestUtils.generateRandomInt(0,100) + 1);
        positions.setX(TestUtils.generateRandomInt(0,100));
        positions.setY(TestUtils.generateRandomInt(0,100));

        return positions;
    }

    public static boolean equals(Positions positions1,
            Positions positions2){
        return equals(positions1, positions2, true);
    }
    
    public static boolean equals(Positions positions1,
            Positions positions2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(positions1);
        Assert.assertNotNull(positions2);
        if (positions1!=null && positions2 !=null){
            Assert.assertEquals(positions1.getId(), positions2.getId());
            Assert.assertEquals(positions1.getX(), positions2.getX());
            Assert.assertEquals(positions1.getY(), positions2.getY());
        }

        return ret;
    }
}

