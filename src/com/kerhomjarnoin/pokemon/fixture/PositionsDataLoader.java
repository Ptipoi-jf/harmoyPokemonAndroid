/**************************************************************************
 * PositionsDataLoader.java, pokemon Android
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




import com.kerhomjarnoin.pokemon.entity.Positions;


/**
 * PositionsDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class PositionsDataLoader
                        extends FixtureBase<Positions> {
    /** PositionsDataLoader name. */
    private static final String FILE_NAME = "Positions";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for x. */
    private static final String X = "x";
    /** Constant field for y. */
    private static final String Y = "y";


    /** PositionsDataLoader instance (Singleton). */
    private static PositionsDataLoader instance;

    /**
     * Get the PositionsDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static PositionsDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new PositionsDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private PositionsDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected Positions extractItem(final Map<?, ?> columns) {
        final Positions positions =
                new Positions();

        return this.extractItem(columns, positions);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param positions Entity to extract
     * @return A Positions entity
     */
    protected Positions extractItem(final Map<?, ?> columns,
                Positions positions) {
        positions.setId(this.parseIntField(columns, ID));
        positions.setX(this.parseIntField(columns, X));
        positions.setY(this.parseIntField(columns, Y));

        return positions;
    }
    /**
     * Loads Positionss into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final Positions positions : this.items.values()) {
            int id = dataManager.persist(positions);
            positions.setId(id);

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
    protected Positions get(final String key) {
        final Positions result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
