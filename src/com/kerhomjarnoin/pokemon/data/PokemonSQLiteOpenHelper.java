/**************************************************************************
 * PokemonSQLiteOpenHelper.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.data;

import com.kerhomjarnoin.pokemon.data.base.PokemonSQLiteOpenHelperBase;

import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * This class makes it easy for ContentProvider implementations to defer <br />
 * opening and upgrading the database until first use, to avoid blocking <br />
 * application startup with long-running database upgrades.
 * @see android.database.sqlite.SQLiteOpenHelper
 */
public class PokemonSQLiteOpenHelper
                    extends PokemonSQLiteOpenHelperBase {

    /**
     * Constructor.
     * @param ctx context
     * @param name name
     * @param factory factory
     * @param version version
     */
    public PokemonSQLiteOpenHelper(final android.content.Context ctx,
           final String name, final CursorFactory factory, final int version) {
        super(ctx, name, factory, version);
    }

}
