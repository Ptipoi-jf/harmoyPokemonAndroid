/**************************************************************************
 * ObjetsDataLoader.java, pokemon Android
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



import com.kerhomjarnoin.pokemon.entity.Objets;


/**
 * ObjetsDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class ObjetsDataLoader
                        extends FixtureBase<Objets> {
    /** ObjetsDataLoader name. */
    private static final String FILE_NAME = "Objets";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for nom. */
    private static final String NOM = "nom";
    /** Constant field for quantite. */
    private static final String QUANTITE = "quantite";
    /** Constant field for type. */
    private static final String TYPE = "type";


    /** ObjetsDataLoader instance (Singleton). */
    private static ObjetsDataLoader instance;

    /**
     * Get the ObjetsDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static ObjetsDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new ObjetsDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private ObjetsDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected Objets extractItem(final Map<?, ?> columns) {
        final Objets objets =
                new Objets();

        return this.extractItem(columns, objets);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param objets Entity to extract
     * @return A Objets entity
     */
    protected Objets extractItem(final Map<?, ?> columns,
                Objets objets) {
        objets.setId(this.parseIntField(columns, ID));
        objets.setNom(this.parseField(columns, NOM, String.class));
        objets.setQuantite(this.parseIntField(columns, QUANTITE));
        objets.setType(this.parseSimpleRelationField(columns, TYPE, TypeObjetDataLoader.getInstance(this.ctx)));

        return objets;
    }
    /**
     * Loads Objetss into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final Objets objets : this.items.values()) {
            int id = dataManager.persist(objets);
            objets.setId(id);

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
    protected Objets get(final String key) {
        final Objets result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
