/**************************************************************************
 * DresseursDataLoader.java, pokemon Android
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




import com.kerhomjarnoin.pokemon.entity.Dresseurs;


/**
 * DresseursDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class DresseursDataLoader
                        extends FixtureBase<Dresseurs> {
    /** DresseursDataLoader name. */
    private static final String FILE_NAME = "Dresseurs";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for nom. */
    private static final String NOM = "nom";
    /** Constant field for npc. */
    private static final String NPC = "npc";


    /** DresseursDataLoader instance (Singleton). */
    private static DresseursDataLoader instance;

    /**
     * Get the DresseursDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static DresseursDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new DresseursDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private DresseursDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected Dresseurs extractItem(final Map<?, ?> columns) {
        final Dresseurs dresseurs =
                new Dresseurs();

        return this.extractItem(columns, dresseurs);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param dresseurs Entity to extract
     * @return A Dresseurs entity
     */
    protected Dresseurs extractItem(final Map<?, ?> columns,
                Dresseurs dresseurs) {
        dresseurs.setId(this.parseIntField(columns, ID));
        dresseurs.setNom(this.parseField(columns, NOM, String.class));
        dresseurs.setNpc(this.parseSimpleRelationField(columns, NPC, NpcDataLoader.getInstance(this.ctx)));

        return dresseurs;
    }
    /**
     * Loads Dresseurss into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final Dresseurs dresseurs : this.items.values()) {
            int id = dataManager.persist(dresseurs);
            dresseurs.setId(id);

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
    protected Dresseurs get(final String key) {
        final Dresseurs result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
