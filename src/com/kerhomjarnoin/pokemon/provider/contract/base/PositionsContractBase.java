/**************************************************************************
 * PositionsContractBase.java, pokemon Android
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

import com.kerhomjarnoin.pokemon.entity.Positions;



import com.kerhomjarnoin.pokemon.provider.contract.PositionsContract;

/** Pokemon contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class PositionsContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            PositionsContract.TABLE_NAME + "." + COL_ID;

    /** x. */
    public static final String COL_X =
            "x";
    /** Alias. */
    public static final String ALIASED_COL_X =
            PositionsContract.TABLE_NAME + "." + COL_X;

    /** y. */
    public static final String COL_Y =
            "y";
    /** Alias. */
    public static final String ALIASED_COL_Y =
            PositionsContract.TABLE_NAME + "." + COL_Y;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Positions";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Positions";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        PositionsContract.COL_ID,
        
        PositionsContract.COL_X,
        
        PositionsContract.COL_Y
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        PositionsContract.ALIASED_COL_ID,
        
        PositionsContract.ALIASED_COL_X,
        
        PositionsContract.ALIASED_COL_Y
    };


    /**
     * Converts a Positions into a content values.
     *
     * @param item The Positions to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Positions item) {
        final ContentValues result = new ContentValues();

             result.put(PositionsContract.COL_ID,
                String.valueOf(item.getId()));

             result.put(PositionsContract.COL_X,
                String.valueOf(item.getX()));

             result.put(PositionsContract.COL_Y,
                String.valueOf(item.getY()));


        return result;
    }

    /**
     * Converts a Cursor into a Positions.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Positions
     */
    public static Positions cursorToItem(final android.database.Cursor cursor) {
        Positions result = new Positions();
        PositionsContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Positions entity.
     * @param cursor Cursor object
     * @param result Positions entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Positions result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(PositionsContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(PositionsContract.COL_X);

            if (index > -1) {
                result.setX(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(PositionsContract.COL_Y);

            if (index > -1) {
                result.setY(cursor.getInt(index));
            }

        }
    }

    /**
     * Convert Cursor of database to Array of Positions entity.
     * @param cursor Cursor object
     * @return Array of Positions entity
     */
    public static ArrayList<Positions> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Positions> result = new ArrayList<Positions>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Positions item;
            do {
                item = PositionsContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
