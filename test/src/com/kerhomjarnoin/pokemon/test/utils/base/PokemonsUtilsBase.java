/**************************************************************************
 * PokemonsUtilsBase.java, pokemon Android
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
import com.kerhomjarnoin.pokemon.entity.Pokemons;



import com.kerhomjarnoin.pokemon.test.utils.TestUtils;
import com.kerhomjarnoin.pokemon.entity.TypeDePokemons;
import com.kerhomjarnoin.pokemon.fixture.TypeDePokemonsDataLoader;

import com.kerhomjarnoin.pokemon.entity.Attaques;
import com.kerhomjarnoin.pokemon.fixture.AttaquesDataLoader;


import java.util.ArrayList;

public abstract class PokemonsUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Pokemons generateRandom(android.content.Context ctx){
        Pokemons pokemons = new Pokemons();

        pokemons.setId(TestUtils.generateRandomInt(0,100) + 1);
        pokemons.setSurnom("surnom_"+TestUtils.generateRandomString(10));
        pokemons.setNiveau(TestUtils.generateRandomInt(0,100));
        pokemons.setCapture(TestUtils.generateRandomDateTime());
        ArrayList<TypeDePokemons> typePokemons =
            new ArrayList<TypeDePokemons>();
        typePokemons.addAll(TypeDePokemonsDataLoader.getInstance(ctx).getMap().values());
        if (!typePokemons.isEmpty()) {
            pokemons.setTypePokemon(typePokemons.get(TestUtils.generateRandomInt(0, typePokemons.size())));
        }
        ArrayList<Attaques> attaque1s =
            new ArrayList<Attaques>();
        attaque1s.addAll(AttaquesDataLoader.getInstance(ctx).getMap().values());
        if (!attaque1s.isEmpty()) {
            pokemons.setAttaque1(attaque1s.get(TestUtils.generateRandomInt(0, attaque1s.size())));
        }
        ArrayList<Attaques> attaque2s =
            new ArrayList<Attaques>();
        attaque2s.addAll(AttaquesDataLoader.getInstance(ctx).getMap().values());
        if (!attaque2s.isEmpty()) {
            pokemons.setAttaque2(attaque2s.get(TestUtils.generateRandomInt(0, attaque2s.size())));
        }
        ArrayList<Attaques> attaque3s =
            new ArrayList<Attaques>();
        attaque3s.addAll(AttaquesDataLoader.getInstance(ctx).getMap().values());
        if (!attaque3s.isEmpty()) {
            pokemons.setAttaque3(attaque3s.get(TestUtils.generateRandomInt(0, attaque3s.size())));
        }
        ArrayList<Attaques> attaque4s =
            new ArrayList<Attaques>();
        attaque4s.addAll(AttaquesDataLoader.getInstance(ctx).getMap().values());
        if (!attaque4s.isEmpty()) {
            pokemons.setAttaque4(attaque4s.get(TestUtils.generateRandomInt(0, attaque4s.size())));
        }

        return pokemons;
    }

    public static boolean equals(Pokemons pokemons1,
            Pokemons pokemons2){
        return equals(pokemons1, pokemons2, true);
    }
    
    public static boolean equals(Pokemons pokemons1,
            Pokemons pokemons2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(pokemons1);
        Assert.assertNotNull(pokemons2);
        if (pokemons1!=null && pokemons2 !=null){
            Assert.assertEquals(pokemons1.getId(), pokemons2.getId());
            Assert.assertEquals(pokemons1.getSurnom(), pokemons2.getSurnom());
            Assert.assertEquals(pokemons1.getNiveau(), pokemons2.getNiveau());
            Assert.assertEquals(pokemons1.getCapture(), pokemons2.getCapture());
            if (pokemons1.getTypePokemon() != null
                    && pokemons2.getTypePokemon() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(pokemons1.getTypePokemon().getId(),
                            pokemons2.getTypePokemon().getId());
                }
            }
            if (pokemons1.getAttaque1() != null
                    && pokemons2.getAttaque1() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(pokemons1.getAttaque1().getId(),
                            pokemons2.getAttaque1().getId());
                }
            }
            if (pokemons1.getAttaque2() != null
                    && pokemons2.getAttaque2() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(pokemons1.getAttaque2().getId(),
                            pokemons2.getAttaque2().getId());
                }
            }
            if (pokemons1.getAttaque3() != null
                    && pokemons2.getAttaque3() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(pokemons1.getAttaque3().getId(),
                            pokemons2.getAttaque3().getId());
                }
            }
            if (pokemons1.getAttaque4() != null
                    && pokemons2.getAttaque4() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(pokemons1.getAttaque4().getId(),
                            pokemons2.getAttaque4().getId());
                }
            }
        }

        return ret;
    }
}

