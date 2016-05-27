/**************************************************************************
 * TypesUtilsBase.java, pokemon Android
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
import com.kerhomjarnoin.pokemon.entity.Types;



import com.kerhomjarnoin.pokemon.test.utils.TestUtils;
import com.kerhomjarnoin.pokemon.entity.Types;
import com.kerhomjarnoin.pokemon.fixture.TypesDataLoader;


import java.util.ArrayList;

public abstract class TypesUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Types generateRandom(android.content.Context ctx){
        Types types = new Types();

        types.setId(TestUtils.generateRandomInt(0,100) + 1);
        types.setNom("nom_"+TestUtils.generateRandomString(10));
        ArrayList<Types> faibleContres =
            new ArrayList<Types>();
        faibleContres.addAll(TypesDataLoader.getInstance(ctx).getMap().values());
        ArrayList<Types> relatedFaibleContres = new ArrayList<Types>();
        if (!faibleContres.isEmpty()) {
            relatedFaibleContres.add(faibleContres.get(TestUtils.generateRandomInt(0, faibleContres.size())));
            types.setFaibleContre(relatedFaibleContres);
        }
        ArrayList<Types> fortContres =
            new ArrayList<Types>();
        fortContres.addAll(TypesDataLoader.getInstance(ctx).getMap().values());
        ArrayList<Types> relatedFortContres = new ArrayList<Types>();
        if (!fortContres.isEmpty()) {
            relatedFortContres.add(fortContres.get(TestUtils.generateRandomInt(0, fortContres.size())));
            types.setFortContre(relatedFortContres);
        }

        return types;
    }

    public static boolean equals(Types types1,
            Types types2){
        return equals(types1, types2, true);
    }
    
    public static boolean equals(Types types1,
            Types types2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(types1);
        Assert.assertNotNull(types2);
        if (types1!=null && types2 !=null){
            Assert.assertEquals(types1.getId(), types2.getId());
            Assert.assertEquals(types1.getNom(), types2.getNom());
            if (types1.getFaibleContre() != null
                    && types2.getFaibleContre() != null) {
                Assert.assertEquals(types1.getFaibleContre().size(),
                    types2.getFaibleContre().size());
                if (checkRecursiveId) {
                    for (Types faibleContre1 : types1.getFaibleContre()) {
                        boolean found = false;
                        for (Types faibleContre2 : types2.getFaibleContre()) {
                            if (faibleContre1.getId() == faibleContre2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated faibleContre (id = %s) in Types (id = %s)",
                                        faibleContre1.getId(),
                                        types1.getId()),
                                found);
                    }
                }
            }
            if (types1.getFortContre() != null
                    && types2.getFortContre() != null) {
                Assert.assertEquals(types1.getFortContre().size(),
                    types2.getFortContre().size());
                if (checkRecursiveId) {
                    for (Types fortContre1 : types1.getFortContre()) {
                        boolean found = false;
                        for (Types fortContre2 : types2.getFortContre()) {
                            if (fortContre1.getId() == fortContre2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated fortContre (id = %s) in Types (id = %s)",
                                        fortContre1.getId(),
                                        types1.getId()),
                                found);
                    }
                }
            }
        }

        return ret;
    }
}

