/**************************************************************************
 * NpcSQLiteAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.data;

import com.kerhomjarnoin.pokemon.data.base.NpcSQLiteAdapterBase;


/**
 * Npc adapter database class. 
 * This class will help you access your database to do any basic operation you
 * need. 
 * Feel free to modify it, override, add more methods etc.
 */
public class NpcSQLiteAdapter extends NpcSQLiteAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public NpcSQLiteAdapter(final android.content.Context ctx) {
        super(ctx);
    }
}
