/**************************************************************************
 * NpcUtilsBase.java, pokemon Android
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
import com.kerhomjarnoin.pokemon.entity.Npc;



import com.kerhomjarnoin.pokemon.test.utils.TestUtils;
import com.kerhomjarnoin.pokemon.entity.Objets;
import com.kerhomjarnoin.pokemon.fixture.ObjetsDataLoader;

import com.kerhomjarnoin.pokemon.entity.Pokemons;
import com.kerhomjarnoin.pokemon.fixture.PokemonsDataLoader;

import com.kerhomjarnoin.pokemon.entity.Badges;
import com.kerhomjarnoin.pokemon.fixture.BadgesDataLoader;

import com.kerhomjarnoin.pokemon.entity.Positions;
import com.kerhomjarnoin.pokemon.fixture.PositionsDataLoader;


import java.util.ArrayList;

public abstract class NpcUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Npc generateRandom(android.content.Context ctx){
        Npc npc = new Npc();

        npc.setId(TestUtils.generateRandomInt(0,100) + 1);
        npc.setNom("nom_"+TestUtils.generateRandomString(10));
        npc.setProfession("profession_"+TestUtils.generateRandomString(10));
        npc.setTexte("texte_"+TestUtils.generateRandomString(10));
        ArrayList<Objets> objetss =
            new ArrayList<Objets>();
        objetss.addAll(ObjetsDataLoader.getInstance(ctx).getMap().values());
        ArrayList<Objets> relatedObjetss = new ArrayList<Objets>();
        if (!objetss.isEmpty()) {
            relatedObjetss.add(objetss.get(TestUtils.generateRandomInt(0, objetss.size())));
            npc.setObjets(relatedObjetss);
        }
        ArrayList<Pokemons> pokemonss =
            new ArrayList<Pokemons>();
        pokemonss.addAll(PokemonsDataLoader.getInstance(ctx).getMap().values());
        ArrayList<Pokemons> relatedPokemonss = new ArrayList<Pokemons>();
        if (!pokemonss.isEmpty()) {
            relatedPokemonss.add(pokemonss.get(TestUtils.generateRandomInt(0, pokemonss.size())));
            npc.setPokemons(relatedPokemonss);
        }
        ArrayList<Badges> badgess =
            new ArrayList<Badges>();
        badgess.addAll(BadgesDataLoader.getInstance(ctx).getMap().values());
        ArrayList<Badges> relatedBadgess = new ArrayList<Badges>();
        if (!badgess.isEmpty()) {
            relatedBadgess.add(badgess.get(TestUtils.generateRandomInt(0, badgess.size())));
            npc.setBadges(relatedBadgess);
        }
        ArrayList<Positions> positions =
            new ArrayList<Positions>();
        positions.addAll(PositionsDataLoader.getInstance(ctx).getMap().values());
        if (!positions.isEmpty()) {
            npc.setPosition(positions.get(TestUtils.generateRandomInt(0, positions.size())));
        }

        return npc;
    }

    public static boolean equals(Npc npc1,
            Npc npc2){
        return equals(npc1, npc2, true);
    }
    
    public static boolean equals(Npc npc1,
            Npc npc2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(npc1);
        Assert.assertNotNull(npc2);
        if (npc1!=null && npc2 !=null){
            Assert.assertEquals(npc1.getId(), npc2.getId());
            Assert.assertEquals(npc1.getNom(), npc2.getNom());
            Assert.assertEquals(npc1.getProfession(), npc2.getProfession());
            Assert.assertEquals(npc1.getTexte(), npc2.getTexte());
            if (npc1.getObjets() != null
                    && npc2.getObjets() != null) {
                Assert.assertEquals(npc1.getObjets().size(),
                    npc2.getObjets().size());
                if (checkRecursiveId) {
                    for (Objets objets1 : npc1.getObjets()) {
                        boolean found = false;
                        for (Objets objets2 : npc2.getObjets()) {
                            if (objets1.getId() == objets2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated objets (id = %s) in Npc (id = %s)",
                                        objets1.getId(),
                                        npc1.getId()),
                                found);
                    }
                }
            }
            if (npc1.getPokemons() != null
                    && npc2.getPokemons() != null) {
                Assert.assertEquals(npc1.getPokemons().size(),
                    npc2.getPokemons().size());
                if (checkRecursiveId) {
                    for (Pokemons pokemons1 : npc1.getPokemons()) {
                        boolean found = false;
                        for (Pokemons pokemons2 : npc2.getPokemons()) {
                            if (pokemons1.getId() == pokemons2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated pokemons (id = %s) in Npc (id = %s)",
                                        pokemons1.getId(),
                                        npc1.getId()),
                                found);
                    }
                }
            }
            if (npc1.getBadges() != null
                    && npc2.getBadges() != null) {
                Assert.assertEquals(npc1.getBadges().size(),
                    npc2.getBadges().size());
                if (checkRecursiveId) {
                    for (Badges badges1 : npc1.getBadges()) {
                        boolean found = false;
                        for (Badges badges2 : npc2.getBadges()) {
                            if (badges1.getId() == badges2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated badges (id = %s) in Npc (id = %s)",
                                        badges1.getId(),
                                        npc1.getId()),
                                found);
                    }
                }
            }
            if (npc1.getPosition() != null
                    && npc2.getPosition() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(npc1.getPosition().getId(),
                            npc2.getPosition().getId());
                }
            }
        }

        return ret;
    }
}

