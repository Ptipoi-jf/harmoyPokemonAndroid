/**************************************************************************
 * NpctoBadgesSQLiteAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.data;

import com.kerhomjarnoin.pokemon.data.base.NpctoBadgesSQLiteAdapterBase;


/**
 * NpctoBadges adapter database class. 
 * This class will help you access your database to do any basic operation you
 * need. 
 * Feel free to modify it, override, add more methods etc.
 */
public class NpctoBadgesSQLiteAdapter extends NpctoBadgesSQLiteAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public NpctoBadgesSQLiteAdapter(final android.content.Context ctx) {
        super(ctx);
    }
}
