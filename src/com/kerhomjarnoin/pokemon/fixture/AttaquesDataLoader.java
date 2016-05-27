/**************************************************************************
 * AttaquesDataLoader.java, pokemon Android
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




import com.kerhomjarnoin.pokemon.entity.Attaques;


/**
 * AttaquesDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class AttaquesDataLoader
                        extends FixtureBase<Attaques> {
    /** AttaquesDataLoader name. */
    private static final String FILE_NAME = "Attaques";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for nom. */
    private static final String NOM = "nom";
    /** Constant field for puissance. */
    private static final String PUISSANCE = "puissance";
    /** Constant field for precis. */
    private static final String PRECIS = "precis";
    /** Constant field for type. */
    private static final String TYPE = "type";


    /** AttaquesDataLoader instance (Singleton). */
    private static AttaquesDataLoader instance;

    /**
     * Get the AttaquesDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static AttaquesDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new AttaquesDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private AttaquesDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected Attaques extractItem(final Map<?, ?> columns) {
        final Attaques attaques =
                new Attaques();

        return this.extractItem(columns, attaques);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param attaques Entity to extract
     * @return A Attaques entity
     */
    protected Attaques extractItem(final Map<?, ?> columns,
                Attaques attaques) {
        attaques.setId(this.parseIntField(columns, ID));
        attaques.setNom(this.parseField(columns, NOM, String.class));
        attaques.setPuissance(this.parseIntField(columns, PUISSANCE));
        attaques.setPrecis(this.parseIntField(columns, PRECIS));
        attaques.setType(this.parseSimpleRelationField(columns, TYPE, TypesDataLoader.getInstance(this.ctx)));

        return attaques;
    }
    /**
     * Loads Attaquess into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final Attaques attaques : this.items.values()) {
            int id = dataManager.persist(attaques);
            attaques.setId(id);

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
    protected Attaques get(final String key) {
        final Attaques result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
