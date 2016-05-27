/**************************************************************************
 * TypeObjetUtilsBase.java, pokemon Android
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
import com.kerhomjarnoin.pokemon.entity.TypeObjet;



import com.kerhomjarnoin.pokemon.test.utils.TestUtils;


public abstract class TypeObjetUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static TypeObjet generateRandom(android.content.Context ctx){
        TypeObjet typeObjet = new TypeObjet();

        typeObjet.setId(TestUtils.generateRandomInt(0,100) + 1);
        typeObjet.setNom("nom_"+TestUtils.generateRandomString(10));

        return typeObjet;
    }

    public static boolean equals(TypeObjet typeObjet1,
            TypeObjet typeObjet2){
        return equals(typeObjet1, typeObjet2, true);
    }
    
    public static boolean equals(TypeObjet typeObjet1,
            TypeObjet typeObjet2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(typeObjet1);
        Assert.assertNotNull(typeObjet2);
        if (typeObjet1!=null && typeObjet2 !=null){
            Assert.assertEquals(typeObjet1.getId(), typeObjet2.getId());
            Assert.assertEquals(typeObjet1.getNom(), typeObjet2.getNom());
        }

        return ret;
    }
}

