/**************************************************************************
 * ZonesDataLoader.java, pokemon Android
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




import com.kerhomjarnoin.pokemon.entity.Zones;


/**
 * ZonesDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class ZonesDataLoader
                        extends FixtureBase<Zones> {
    /** ZonesDataLoader name. */
    private static final String FILE_NAME = "Zones";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for nom. */
    private static final String NOM = "nom";


    /** ZonesDataLoader instance (Singleton). */
    private static ZonesDataLoader instance;

    /**
     * Get the ZonesDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static ZonesDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new ZonesDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private ZonesDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected Zones extractItem(final Map<?, ?> columns) {
        final Zones zones =
                new Zones();

        return this.extractItem(columns, zones);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param zones Entity to extract
     * @return A Zones entity
     */
    protected Zones extractItem(final Map<?, ?> columns,
                Zones zones) {
        zones.setId(this.parseIntField(columns, ID));
        zones.setNom(this.parseField(columns, NOM, String.class));

        return zones;
    }
    /**
     * Loads Zoness into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final Zones zones : this.items.values()) {
            int id = dataManager.persist(zones);
            zones.setId(id);

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
    protected Zones get(final String key) {
        final Zones result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
