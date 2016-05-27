/**************************************************************************
 * DresseursContractBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.provider.contract.base;

import android.content.ContentValues;


import java.util.ArrayList;

import com.kerhomjarnoin.pokemon.entity.Dresseurs;
import com.kerhomjarnoin.pokemon.entity.Npc;



import com.kerhomjarnoin.pokemon.provider.contract.DresseursContract;

/** Pokemon contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class DresseursContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            DresseursContract.TABLE_NAME + "." + COL_ID;

    /** nom. */
    public static final String COL_NOM =
            "nom";
    /** Alias. */
    public static final String ALIASED_COL_NOM =
            DresseursContract.TABLE_NAME + "." + COL_NOM;

    /** npc_id. */
    public static final String COL_NPC_ID =
            "npc_id";
    /** Alias. */
    public static final String ALIASED_COL_NPC_ID =
            DresseursContract.TABLE_NAME + "." + COL_NPC_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Dresseurs";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Dresseurs";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        DresseursContract.COL_ID,
        
        DresseursContract.COL_NOM,
        
        DresseursContract.COL_NPC_ID
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        DresseursContract.ALIASED_COL_ID,
        
        DresseursContract.ALIASED_COL_NOM,
        
        DresseursContract.ALIASED_COL_NPC_ID
    };


    /**
     * Converts a Dresseurs into a content values.
     *
     * @param item The Dresseurs to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Dresseurs item) {
        final ContentValues result = new ContentValues();

             result.put(DresseursContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getNom() != null) {
                result.put(DresseursContract.COL_NOM,
                    item.getNom());
            }

             if (item.getNpc() != null) {
                result.put(DresseursContract.COL_NPC_ID,
                    item.getNpc().getId());
            } else {
                result.put(DresseursContract.COL_NPC_ID, (String) null);
            }


        return result;
    }

    /**
     * Converts a Cursor into a Dresseurs.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Dresseurs
     */
    public static Dresseurs cursorToItem(final android.database.Cursor cursor) {
        Dresseurs result = new Dresseurs();
        DresseursContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Dresseurs entity.
     * @param cursor Cursor object
     * @param result Dresseurs entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Dresseurs result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(DresseursContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(DresseursContract.COL_NOM);

            if (index > -1) {
                result.setNom(cursor.getString(index));
            }
            if (result.getNpc() == null) {
                final Npc npc = new Npc();
                index = cursor.getColumnIndex(DresseursContract.COL_NPC_ID);

                if (index > -1) {
                    if (!cursor.isNull(index)) {
                        npc.setId(cursor.getInt(index));
                        result.setNpc(npc);
                    }
                }

            }

        }
    }

    /**
     * Convert Cursor of database to Array of Dresseurs entity.
     * @param cursor Cursor object
     * @return Array of Dresseurs entity
     */
    public static ArrayList<Dresseurs> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Dresseurs> result = new ArrayList<Dresseurs>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Dresseurs item;
            do {
                item = DresseursContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
