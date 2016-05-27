/**************************************************************************
 * TypeObjetDataLoader.java, pokemon Android
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




import com.kerhomjarnoin.pokemon.entity.TypeObjet;


/**
 * TypeObjetDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class TypeObjetDataLoader
                        extends FixtureBase<TypeObjet> {
    /** TypeObjetDataLoader name. */
    private static final String FILE_NAME = "TypeObjet";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for nom. */
    private static final String NOM = "nom";


    /** TypeObjetDataLoader instance (Singleton). */
    private static TypeObjetDataLoader instance;

    /**
     * Get the TypeObjetDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static TypeObjetDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new TypeObjetDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private TypeObjetDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected TypeObjet extractItem(final Map<?, ?> columns) {
        final TypeObjet typeObjet =
                new TypeObjet();

        return this.extractItem(columns, typeObjet);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param typeObjet Entity to extract
     * @return A TypeObjet entity
     */
    protected TypeObjet extractItem(final Map<?, ?> columns,
                TypeObjet typeObjet) {
        typeObjet.setId(this.parseIntField(columns, ID));
        typeObjet.setNom(this.parseField(columns, NOM, String.class));

        return typeObjet;
    }
    /**
     * Loads TypeObjets into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final TypeObjet typeObjet : this.items.values()) {
            int id = dataManager.persist(typeObjet);
            typeObjet.setId(id);

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
    protected TypeObjet get(final String key) {
        final TypeObjet result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
