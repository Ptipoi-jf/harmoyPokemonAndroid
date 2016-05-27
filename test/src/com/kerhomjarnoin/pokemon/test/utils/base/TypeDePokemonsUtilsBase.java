/**************************************************************************
 * TypeDePokemonsUtilsBase.java, pokemon Android
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
import com.kerhomjarnoin.pokemon.entity.TypeDePokemons;



import com.kerhomjarnoin.pokemon.test.utils.TestUtils;
import com.kerhomjarnoin.pokemon.entity.Types;
import com.kerhomjarnoin.pokemon.fixture.TypesDataLoader;


import java.util.ArrayList;

public abstract class TypeDePokemonsUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static TypeDePokemons generateRandom(android.content.Context ctx){
        TypeDePokemons typeDePokemons = new TypeDePokemons();

        typeDePokemons.setId(TestUtils.generateRandomInt(0,100) + 1);
        typeDePokemons.setNom("nom_"+TestUtils.generateRandomString(10));
        typeDePokemons.setAttaque(TestUtils.generateRandomInt(0,100));
        typeDePokemons.setAttaque_spe(TestUtils.generateRandomInt(0,100));
        typeDePokemons.setDefence(TestUtils.generateRandomInt(0,100));
        typeDePokemons.setDefence_spe(TestUtils.generateRandomInt(0,100));
        typeDePokemons.setVitesse(TestUtils.generateRandomInt(0,100));
        typeDePokemons.setPv(TestUtils.generateRandomInt(0,100));
        ArrayList<Types> typess =
            new ArrayList<Types>();
        typess.addAll(TypesDataLoader.getInstance(ctx).getMap().values());
        ArrayList<Types> relatedTypess = new ArrayList<Types>();
        if (!typess.isEmpty()) {
            relatedTypess.add(typess.get(TestUtils.generateRandomInt(0, typess.size())));
            typeDePokemons.setTypes(relatedTypess);
        }

        return typeDePokemons;
    }

    public static boolean equals(TypeDePokemons typeDePokemons1,
            TypeDePokemons typeDePokemons2){
        return equals(typeDePokemons1, typeDePokemons2, true);
    }
    
    public static boolean equals(TypeDePokemons typeDePokemons1,
            TypeDePokemons typeDePokemons2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(typeDePokemons1);
        Assert.assertNotNull(typeDePokemons2);
        if (typeDePokemons1!=null && typeDePokemons2 !=null){
            Assert.assertEquals(typeDePokemons1.getId(), typeDePokemons2.getId());
            Assert.assertEquals(typeDePokemons1.getNom(), typeDePokemons2.getNom());
            Assert.assertEquals(typeDePokemons1.getAttaque(), typeDePokemons2.getAttaque());
            Assert.assertEquals(typeDePokemons1.getAttaque_spe(), typeDePokemons2.getAttaque_spe());
            Assert.assertEquals(typeDePokemons1.getDefence(), typeDePokemons2.getDefence());
            Assert.assertEquals(typeDePokemons1.getDefence_spe(), typeDePokemons2.getDefence_spe());
            Assert.assertEquals(typeDePokemons1.getVitesse(), typeDePokemons2.getVitesse());
            Assert.assertEquals(typeDePokemons1.getPv(), typeDePokemons2.getPv());
            if (typeDePokemons1.getTypes() != null
                    && typeDePokemons2.getTypes() != null) {
                Assert.assertEquals(typeDePokemons1.getTypes().size(),
                    typeDePokemons2.getTypes().size());
                if (checkRecursiveId) {
                    for (Types types1 : typeDePokemons1.getTypes()) {
                        boolean found = false;
                        for (Types types2 : typeDePokemons2.getTypes()) {
                            if (types1.getId() == types2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated types (id = %s) in TypeDePokemons (id = %s)",
                                        types1.getId(),
                                        typeDePokemons1.getId()),
                                found);
                    }
                }
            }
        }

        return ret;
    }
}

