/**************************************************************************
 * ZonesContractBase.java, pokemon Android
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

import com.kerhomjarnoin.pokemon.entity.Zones;



import com.kerhomjarnoin.pokemon.provider.contract.ZonesContract;

/** Pokemon contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class ZonesContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            ZonesContract.TABLE_NAME + "." + COL_ID;

    /** nom. */
    public static final String COL_NOM =
            "nom";
    /** Alias. */
    public static final String ALIASED_COL_NOM =
            ZonesContract.TABLE_NAME + "." + COL_NOM;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Zones";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Zones";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        ZonesContract.COL_ID,
        
        ZonesContract.COL_NOM
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        ZonesContract.ALIASED_COL_ID,
        
        ZonesContract.ALIASED_COL_NOM
    };


    /**
     * Converts a Zones into a content values.
     *
     * @param item The Zones to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Zones item) {
        final ContentValues result = new ContentValues();

             result.put(ZonesContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getNom() != null) {
                result.put(ZonesContract.COL_NOM,
                    item.getNom());
            }


        return result;
    }

    /**
     * Converts a Cursor into a Zones.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Zones
     */
    public static Zones cursorToItem(final android.database.Cursor cursor) {
        Zones result = new Zones();
        ZonesContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Zones entity.
     * @param cursor Cursor object
     * @param result Zones entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Zones result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(ZonesContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(ZonesContract.COL_NOM);

            if (index > -1) {
                result.setNom(cursor.getString(index));
            }

        }
    }

    /**
     * Convert Cursor of database to Array of Zones entity.
     * @param cursor Cursor object
     * @return Array of Zones entity
     */
    public static ArrayList<Zones> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Zones> result = new ArrayList<Zones>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Zones item;
            do {
                item = ZonesContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
