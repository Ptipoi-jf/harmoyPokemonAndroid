/**************************************************************************
 * AttaquesUtilsBase.java, pokemon Android
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
import com.kerhomjarnoin.pokemon.entity.Attaques;



import com.kerhomjarnoin.pokemon.test.utils.TestUtils;
import com.kerhomjarnoin.pokemon.entity.Types;
import com.kerhomjarnoin.pokemon.fixture.TypesDataLoader;


import java.util.ArrayList;

public abstract class AttaquesUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Attaques generateRandom(android.content.Context ctx){
        Attaques attaques = new Attaques();

        attaques.setId(TestUtils.generateRandomInt(0,100) + 1);
        attaques.setNom("nom_"+TestUtils.generateRandomString(10));
        attaques.setPuissance(TestUtils.generateRandomInt(0,100));
        attaques.setPrecis(TestUtils.generateRandomInt(0,100));
        ArrayList<Types> types =
            new ArrayList<Types>();
        types.addAll(TypesDataLoader.getInstance(ctx).getMap().values());
        if (!types.isEmpty()) {
            attaques.setType(types.get(TestUtils.generateRandomInt(0, types.size())));
        }

        return attaques;
    }

    public static boolean equals(Attaques attaques1,
            Attaques attaques2){
        return equals(attaques1, attaques2, true);
    }
    
    public static boolean equals(Attaques attaques1,
            Attaques attaques2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(attaques1);
        Assert.assertNotNull(attaques2);
        if (attaques1!=null && attaques2 !=null){
            Assert.assertEquals(attaques1.getId(), attaques2.getId());
            Assert.assertEquals(attaques1.getNom(), attaques2.getNom());
            Assert.assertEquals(attaques1.getPuissance(), attaques2.getPuissance());
            Assert.assertEquals(attaques1.getPrecis(), attaques2.getPrecis());
            if (attaques1.getType() != null
                    && attaques2.getType() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(attaques1.getType().getId(),
                            attaques2.getType().getId());
                }
            }
        }

        return ret;
    }
}

