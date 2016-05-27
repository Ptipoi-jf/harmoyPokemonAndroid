/**************************************************************************
 * NpcDataLoader.java, pokemon Android
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




import com.kerhomjarnoin.pokemon.entity.Npc;
import com.kerhomjarnoin.pokemon.entity.Badges;


/**
 * NpcDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class NpcDataLoader
                        extends FixtureBase<Npc> {
    /** NpcDataLoader name. */
    private static final String FILE_NAME = "Npc";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for nom. */
    private static final String NOM = "nom";
    /** Constant field for profession. */
    private static final String PROFESSION = "profession";
    /** Constant field for texte. */
    private static final String TEXTE = "texte";
    /** Constant field for objets. */
    private static final String OBJETS = "objets";
    /** Constant field for pokemons. */
    private static final String POKEMONS = "pokemons";
    /** Constant field for badges. */
    private static final String BADGES = "badges";
    /** Constant field for position. */
    private static final String POSITION = "position";


    /** NpcDataLoader instance (Singleton). */
    private static NpcDataLoader instance;

    /**
     * Get the NpcDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static NpcDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new NpcDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private NpcDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected Npc extractItem(final Map<?, ?> columns) {
        final Npc npc =
                new Npc();

        return this.extractItem(columns, npc);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param npc Entity to extract
     * @return A Npc entity
     */
    protected Npc extractItem(final Map<?, ?> columns,
                Npc npc) {
        npc.setId(this.parseIntField(columns, ID));
        npc.setNom(this.parseField(columns, NOM, String.class));
        npc.setProfession(this.parseField(columns, PROFESSION, String.class));
        npc.setTexte(this.parseField(columns, TEXTE, String.class));
        npc.setObjets(this.parseMultiRelationField(columns, OBJETS, ObjetsDataLoader.getInstance(this.ctx)));
        npc.setPokemons(this.parseMultiRelationField(columns, POKEMONS, PokemonsDataLoader.getInstance(this.ctx)));
        npc.setBadges(this.parseMultiRelationField(columns, BADGES, BadgesDataLoader.getInstance(this.ctx)));
        npc.setPosition(this.parseSimpleRelationField(columns, POSITION, PositionsDataLoader.getInstance(this.ctx)));

        return npc;
    }
    /**
     * Loads Npcs into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final Npc npc : this.items.values()) {
            int id = dataManager.persist(npc);
            npc.setId(id);

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
    protected Npc get(final String key) {
        final Npc result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
