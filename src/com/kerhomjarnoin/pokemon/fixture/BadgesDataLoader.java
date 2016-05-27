/**************************************************************************
 * BadgesDataLoader.java, pokemon Android
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




import com.kerhomjarnoin.pokemon.entity.Badges;


/**
 * BadgesDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class BadgesDataLoader
                        extends FixtureBase<Badges> {
    /** BadgesDataLoader name. */
    private static final String FILE_NAME = "Badges";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for nom. */
    private static final String NOM = "nom";


    /** BadgesDataLoader instance (Singleton). */
    private static BadgesDataLoader instance;

    /**
     * Get the BadgesDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static BadgesDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new BadgesDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private BadgesDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected Badges extractItem(final Map<?, ?> columns) {
        final Badges badges =
                new Badges();

        return this.extractItem(columns, badges);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param badges Entity to extract
     * @return A Badges entity
     */
    protected Badges extractItem(final Map<?, ?> columns,
                Badges badges) {
        badges.setId(this.parseIntField(columns, ID));
        badges.setNom(this.parseField(columns, NOM, String.class));

        return badges;
    }
    /**
     * Loads Badgess into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final Badges badges : this.items.values()) {
            int id = dataManager.persist(badges);
            badges.setId(id);

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
    protected Badges get(final String key) {
        final Badges result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
