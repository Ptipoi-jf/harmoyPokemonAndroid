/**************************************************************************
 * BadgesContractBase.java, pokemon Android
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

import com.kerhomjarnoin.pokemon.entity.Badges;



import com.kerhomjarnoin.pokemon.provider.contract.BadgesContract;

/** Pokemon contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class BadgesContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            BadgesContract.TABLE_NAME + "." + COL_ID;

    /** nom. */
    public static final String COL_NOM =
            "nom";
    /** Alias. */
    public static final String ALIASED_COL_NOM =
            BadgesContract.TABLE_NAME + "." + COL_NOM;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Badges";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Badges";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        BadgesContract.COL_ID,
        
        BadgesContract.COL_NOM
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        BadgesContract.ALIASED_COL_ID,
        
        BadgesContract.ALIASED_COL_NOM
    };


    /**
     * Converts a Badges into a content values.
     *
     * @param item The Badges to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Badges item) {
        final ContentValues result = new ContentValues();

             result.put(BadgesContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getNom() != null) {
                result.put(BadgesContract.COL_NOM,
                    item.getNom());
            }


        return result;
    }

    /**
     * Converts a Cursor into a Badges.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Badges
     */
    public static Badges cursorToItem(final android.database.Cursor cursor) {
        Badges result = new Badges();
        BadgesContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Badges entity.
     * @param cursor Cursor object
     * @param result Badges entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Badges result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(BadgesContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(BadgesContract.COL_NOM);

            if (index > -1) {
                result.setNom(cursor.getString(index));
            }

        }
    }

    /**
     * Convert Cursor of database to Array of Badges entity.
     * @param cursor Cursor object
     * @return Array of Badges entity
     */
    public static ArrayList<Badges> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Badges> result = new ArrayList<Badges>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Badges item;
            do {
                item = BadgesContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
