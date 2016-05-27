/**************************************************************************
 * TypeDePokemonsDataLoader.java, pokemon Android
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




import com.kerhomjarnoin.pokemon.entity.TypeDePokemons;


/**
 * TypeDePokemonsDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class TypeDePokemonsDataLoader
                        extends FixtureBase<TypeDePokemons> {
    /** TypeDePokemonsDataLoader name. */
    private static final String FILE_NAME = "TypeDePokemons";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for nom. */
    private static final String NOM = "nom";
    /** Constant field for attaque. */
    private static final String ATTAQUE = "attaque";
    /** Constant field for attaque_spe. */
    private static final String ATTAQUE_SPE = "attaque_spe";
    /** Constant field for defence. */
    private static final String DEFENCE = "defence";
    /** Constant field for defence_spe. */
    private static final String DEFENCE_SPE = "defence_spe";
    /** Constant field for vitesse. */
    private static final String VITESSE = "vitesse";
    /** Constant field for pv. */
    private static final String PV = "pv";
    /** Constant field for types. */
    private static final String TYPES = "types";


    /** TypeDePokemonsDataLoader instance (Singleton). */
    private static TypeDePokemonsDataLoader instance;

    /**
     * Get the TypeDePokemonsDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static TypeDePokemonsDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new TypeDePokemonsDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private TypeDePokemonsDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected TypeDePokemons extractItem(final Map<?, ?> columns) {
        final TypeDePokemons typeDePokemons =
                new TypeDePokemons();

        return this.extractItem(columns, typeDePokemons);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param typeDePokemons Entity to extract
     * @return A TypeDePokemons entity
     */
    protected TypeDePokemons extractItem(final Map<?, ?> columns,
                TypeDePokemons typeDePokemons) {
        typeDePokemons.setId(this.parseIntField(columns, ID));
        typeDePokemons.setNom(this.parseField(columns, NOM, String.class));
        typeDePokemons.setAttaque(this.parseIntField(columns, ATTAQUE));
        typeDePokemons.setAttaque_spe(this.parseIntField(columns, ATTAQUE_SPE));
        typeDePokemons.setDefence(this.parseIntField(columns, DEFENCE));
        typeDePokemons.setDefence_spe(this.parseIntField(columns, DEFENCE_SPE));
        typeDePokemons.setVitesse(this.parseIntField(columns, VITESSE));
        typeDePokemons.setPv(this.parseIntField(columns, PV));
        typeDePokemons.setTypes(this.parseMultiRelationField(columns, TYPES, TypesDataLoader.getInstance(this.ctx)));

        return typeDePokemons;
    }
    /**
     * Loads TypeDePokemonss into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final TypeDePokemons typeDePokemons : this.items.values()) {
            int id = dataManager.persist(typeDePokemons);
            typeDePokemons.setId(id);

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
    protected TypeDePokemons get(final String key) {
        final TypeDePokemons result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
