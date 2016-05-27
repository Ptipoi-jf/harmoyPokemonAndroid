/**************************************************************************
 * ObjetsUtilsBase.java, pokemon Android
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
import com.kerhomjarnoin.pokemon.entity.Objets;



import com.kerhomjarnoin.pokemon.test.utils.TestUtils;
import com.kerhomjarnoin.pokemon.entity.TypeObjet;
import com.kerhomjarnoin.pokemon.fixture.TypeObjetDataLoader;


import java.util.ArrayList;

public abstract class ObjetsUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Objets generateRandom(android.content.Context ctx){
        Objets objets = new Objets();

        objets.setId(TestUtils.generateRandomInt(0,100) + 1);
        objets.setNom("nom_"+TestUtils.generateRandomString(10));
        objets.setQuantite(TestUtils.generateRandomInt(0,100));
        ArrayList<TypeObjet> types =
            new ArrayList<TypeObjet>();
        types.addAll(TypeObjetDataLoader.getInstance(ctx).getMap().values());
        if (!types.isEmpty()) {
            objets.setType(types.get(TestUtils.generateRandomInt(0, types.size())));
        }

        return objets;
    }

    public static boolean equals(Objets objets1,
            Objets objets2){
        return equals(objets1, objets2, true);
    }
    
    public static boolean equals(Objets objets1,
            Objets objets2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(objets1);
        Assert.assertNotNull(objets2);
        if (objets1!=null && objets2 !=null){
            Assert.assertEquals(objets1.getId(), objets2.getId());
            Assert.assertEquals(objets1.getNom(), objets2.getNom());
            Assert.assertEquals(objets1.getQuantite(), objets2.getQuantite());
            if (objets1.getType() != null
                    && objets2.getType() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(objets1.getType().getId(),
                            objets2.getType().getId());
                }
            }
        }

        return ret;
    }
}

