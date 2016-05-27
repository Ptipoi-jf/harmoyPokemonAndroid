/**************************************************************************
 * AttaquesContractBase.java, pokemon Android
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

import com.kerhomjarnoin.pokemon.entity.Attaques;
import com.kerhomjarnoin.pokemon.entity.Types;



import com.kerhomjarnoin.pokemon.provider.contract.AttaquesContract;

/** Pokemon contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class AttaquesContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            AttaquesContract.TABLE_NAME + "." + COL_ID;

    /** nom. */
    public static final String COL_NOM =
            "nom";
    /** Alias. */
    public static final String ALIASED_COL_NOM =
            AttaquesContract.TABLE_NAME + "." + COL_NOM;

    /** puissance. */
    public static final String COL_PUISSANCE =
            "puissance";
    /** Alias. */
    public static final String ALIASED_COL_PUISSANCE =
            AttaquesContract.TABLE_NAME + "." + COL_PUISSANCE;

    /** precis. */
    public static final String COL_PRECIS =
            "precis";
    /** Alias. */
    public static final String ALIASED_COL_PRECIS =
            AttaquesContract.TABLE_NAME + "." + COL_PRECIS;

    /** type_id. */
    public static final String COL_TYPE_ID =
            "type_id";
    /** Alias. */
    public static final String ALIASED_COL_TYPE_ID =
            AttaquesContract.TABLE_NAME + "." + COL_TYPE_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Attaques";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Attaques";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        AttaquesContract.COL_ID,
        
        AttaquesContract.COL_NOM,
        
        AttaquesContract.COL_PUISSANCE,
        
        AttaquesContract.COL_PRECIS,
        
        AttaquesContract.COL_TYPE_ID
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        AttaquesContract.ALIASED_COL_ID,
        
        AttaquesContract.ALIASED_COL_NOM,
        
        AttaquesContract.ALIASED_COL_PUISSANCE,
        
        AttaquesContract.ALIASED_COL_PRECIS,
        
        AttaquesContract.ALIASED_COL_TYPE_ID
    };


    /**
     * Converts a Attaques into a content values.
     *
     * @param item The Attaques to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Attaques item) {
        final ContentValues result = new ContentValues();

             result.put(AttaquesContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getNom() != null) {
                result.put(AttaquesContract.COL_NOM,
                    item.getNom());
            }

             result.put(AttaquesContract.COL_PUISSANCE,
                String.valueOf(item.getPuissance()));

             result.put(AttaquesContract.COL_PRECIS,
                String.valueOf(item.getPrecis()));

             if (item.getType() != null) {
                result.put(AttaquesContract.COL_TYPE_ID,
                    item.getType().getId());
            }


        return result;
    }

    /**
     * Converts a Cursor into a Attaques.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Attaques
     */
    public static Attaques cursorToItem(final android.database.Cursor cursor) {
        Attaques result = new Attaques();
        AttaquesContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Attaques entity.
     * @param cursor Cursor object
     * @param result Attaques entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Attaques result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(AttaquesContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(AttaquesContract.COL_NOM);

            if (index > -1) {
                result.setNom(cursor.getString(index));
            }
            index = cursor.getColumnIndex(AttaquesContract.COL_PUISSANCE);

            if (index > -1) {
                result.setPuissance(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(AttaquesContract.COL_PRECIS);

            if (index > -1) {
                result.setPrecis(cursor.getInt(index));
            }
            if (result.getType() == null) {
                final Types type = new Types();
                index = cursor.getColumnIndex(AttaquesContract.COL_TYPE_ID);

                if (index > -1) {
                    type.setId(cursor.getInt(index));
                    result.setType(type);
                }

            }

        }
    }

    /**
     * Convert Cursor of database to Array of Attaques entity.
     * @param cursor Cursor object
     * @return Array of Attaques entity
     */
    public static ArrayList<Attaques> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Attaques> result = new ArrayList<Attaques>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Attaques item;
            do {
                item = AttaquesContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
