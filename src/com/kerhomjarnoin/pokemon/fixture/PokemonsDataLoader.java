/**************************************************************************
 * PokemonsDataLoader.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.fixture;

import java.util.Map;
import java.util.ArrayList;



import com.kerhomjarnoin.pokemon.entity.Pokemons;


/**
 * PokemonsDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class PokemonsDataLoader
                        extends FixtureBase<Pokemons> {
    /** PokemonsDataLoader name. */
    private static final String FILE_NAME = "Pokemons";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for surnom. */
    private static final String SURNOM = "surnom";
    /** Constant field for niveau. */
    private static final String NIVEAU = "niveau";
    /** Constant field for capture. */
    private static final String CAPTURE = "capture";
    /** Constant field for typePokemon. */
    private static final String TYPEPOKEMON = "typePokemon";
    /** Constant field for attaque1. */
    private static final String ATTAQUE1 = "attaque1";
    /** Constant field for attaque2. */
    private static final String ATTAQUE2 = "attaque2";
    /** Constant field for attaque3. */
    private static final String ATTAQUE3 = "attaque3";
    /** Constant field for attaque4. */
    private static final String ATTAQUE4 = "attaque4";


    /** PokemonsDataLoader instance (Singleton). */
    private static PokemonsDataLoader instance;

    /**
     * Get the PokemonsDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static PokemonsDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new PokemonsDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private PokemonsDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected Pokemons extractItem(final Map<?, ?> columns) {
        final Pokemons pokemons =
                new Pokemons();

        return this.extractItem(columns, pokemons);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param pokemons Entity to extract
     * @return A Pokemons entity
     */
    protected Pokemons extractItem(final Map<?, ?> columns,
                Pokemons pokemons) {
        pokemons.setId(this.parseIntField(columns, ID));
        pokemons.setSurnom(this.parseField(columns, SURNOM, String.class));
        pokemons.setNiveau(this.parseIntField(columns, NIVEAU));
        pokemons.setCapture(this.parseDateTimeField(columns, CAPTURE));
        pokemons.setTypePokemon(this.parseSimpleRelationField(columns, TYPEPOKEMON, TypeDePokemonsDataLoader.getInstance(this.ctx)));
        pokemons.setAttaque1(this.parseSimpleRelationField(columns, ATTAQUE1, AttaquesDataLoader.getInstance(this.ctx)));
        pokemons.setAttaque2(this.parseSimpleRelationField(columns, ATTAQUE2, AttaquesDataLoader.getInstance(this.ctx)));
        pokemons.setAttaque3(this.parseSimpleRelationField(columns, ATTAQUE3, AttaquesDataLoader.getInstance(this.ctx)));
        pokemons.setAttaque4(this.parseSimpleRelationField(columns, ATTAQUE4, AttaquesDataLoader.getInstance(this.ctx)));

        return pokemons;
    }
    /**
     * Loads Pokemonss into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final Pokemons pokemons : this.items.values()) {
            int id = dataManager.persist(pokemons);
            pokemons.setId(id);

        }
        dataManager.flush();
    }

    /**
     * Give priority for fixtures insertion in database.
     * 0 is the first.
     * @return The order
     */
    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * Get the fixture file name.
     * @return A String representing the file name
     */
    @Override
    public String getFixtureFileName() {
        return FILE_NAME;
    }

    @Override
    protected Pokemons get(final String key) {
        final Pokemons result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
