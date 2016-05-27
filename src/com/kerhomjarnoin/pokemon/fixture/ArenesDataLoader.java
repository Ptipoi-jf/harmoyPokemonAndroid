/**************************************************************************
 * ArenesDataLoader.java, pokemon Android
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




import com.kerhomjarnoin.pokemon.entity.Arenes;


/**
 * ArenesDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class ArenesDataLoader
                        extends FixtureBase<Arenes> {
    /** ArenesDataLoader name. */
    private static final String FILE_NAME = "Arenes";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for nom. */
    private static final String NOM = "nom";
    /** Constant field for position. */
    private static final String POSITION = "position";
    /** Constant field for badge. */
    private static final String BADGE = "badge";


    /** ArenesDataLoader instance (Singleton). */
    private static ArenesDataLoader instance;

    /**
     * Get the ArenesDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static ArenesDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new ArenesDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private ArenesDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected Arenes extractItem(final Map<?, ?> columns) {
        final Arenes arenes =
                new Arenes();

        return this.extractItem(columns, arenes);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param arenes Entity to extract
     * @return A Arenes entity
     */
    protected Arenes extractItem(final Map<?, ?> columns,
                Arenes arenes) {
        arenes.setId(this.parseIntField(columns, ID));
        arenes.setNom(this.parseField(columns, NOM, String.class));
        arenes.setPosition(this.parseSimpleRelationField(columns, POSITION, PositionsDataLoader.getInstance(this.ctx)));
        arenes.setBadge(this.parseSimpleRelationField(columns, BADGE, BadgesDataLoader.getInstance(this.ctx)));

        return arenes;
    }
    /**
     * Loads Areness into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final Arenes arenes : this.items.values()) {
            int id = dataManager.persist(arenes);
            arenes.setId(id);

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
    protected Arenes get(final String key) {
        final Arenes result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
