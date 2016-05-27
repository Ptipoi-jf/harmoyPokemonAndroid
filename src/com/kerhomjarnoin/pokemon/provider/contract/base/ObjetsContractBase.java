/**************************************************************************
 * ObjetsContractBase.java, pokemon Android
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

import com.kerhomjarnoin.pokemon.entity.Objets;
import com.kerhomjarnoin.pokemon.entity.TypeObjet;



import com.kerhomjarnoin.pokemon.provider.contract.ObjetsContract;

/** Pokemon contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class ObjetsContractBase {


        /** NpcobjetsInternal_id. */
    public static final String COL_NPCOBJETSINTERNAL_ID =
            "Npc_objets_internal_id";
    /** Alias. */
    public static final String ALIASED_COL_NPCOBJETSINTERNAL_ID =
            ObjetsContract.TABLE_NAME + "." + COL_NPCOBJETSINTERNAL_ID;

    /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            ObjetsContract.TABLE_NAME + "." + COL_ID;

    /** nom. */
    public static final String COL_NOM =
            "nom";
    /** Alias. */
    public static final String ALIASED_COL_NOM =
            ObjetsContract.TABLE_NAME + "." + COL_NOM;

    /** quantite. */
    public static final String COL_QUANTITE =
            "quantite";
    /** Alias. */
    public static final String ALIASED_COL_QUANTITE =
            ObjetsContract.TABLE_NAME + "." + COL_QUANTITE;

    /** type_id. */
    public static final String COL_TYPE_ID =
            "type_id";
    /** Alias. */
    public static final String ALIASED_COL_TYPE_ID =
            ObjetsContract.TABLE_NAME + "." + COL_TYPE_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Objets";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Objets";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        ObjetsContract.COL_NPCOBJETSINTERNAL_ID,
        
        ObjetsContract.COL_ID,
        
        ObjetsContract.COL_NOM,
        
        ObjetsContract.COL_QUANTITE,
        
        ObjetsContract.COL_TYPE_ID
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        ObjetsContract.ALIASED_COL_NPCOBJETSINTERNAL_ID,
        
        ObjetsContract.ALIASED_COL_ID,
        
        ObjetsContract.ALIASED_COL_NOM,
        
        ObjetsContract.ALIASED_COL_QUANTITE,
        
        ObjetsContract.ALIASED_COL_TYPE_ID
    };

    /** Convert Objets entity to Content Values for database.
     *
     * @param item Objets entity object
     * @param npcId npc id
     * @return ContentValues object
     */
    public static ContentValues itemToContentValues(final Objets item,
                final int npcobjetsInternalId) {
        final ContentValues result = ObjetsContract.itemToContentValues(item);
        result.put(ObjetsContract.COL_NPCOBJETSINTERNAL_ID,
                String.valueOf(npcobjetsInternalId));
        return result;
    }

    /**
     * Converts a Objets into a content values.
     *
     * @param item The Objets to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Objets item) {
        final ContentValues result = new ContentValues();

              result.put(ObjetsContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getNom() != null) {
                result.put(ObjetsContract.COL_NOM,
                    item.getNom());
            }

             result.put(ObjetsContract.COL_QUANTITE,
                String.valueOf(item.getQuantite()));

             if (item.getType() != null) {
                result.put(ObjetsContract.COL_TYPE_ID,
                    item.getType().getId());
            }


        return result;
    }

    /**
     * Converts a Cursor into a Objets.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Objets
     */
    public static Objets cursorToItem(final android.database.Cursor cursor) {
        Objets result = new Objets();
        ObjetsContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Objets entity.
     * @param cursor Cursor object
     * @param result Objets entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Objets result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(ObjetsContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(ObjetsContract.COL_NOM);

            if (index > -1) {
                result.setNom(cursor.getString(index));
            }
            index = cursor.getColumnIndex(ObjetsContract.COL_QUANTITE);

            if (index > -1) {
                result.setQuantite(cursor.getInt(index));
            }
            if (result.getType() == null) {
                final TypeObjet type = new TypeObjet();
                index = cursor.getColumnIndex(ObjetsContract.COL_TYPE_ID);

                if (index > -1) {
                    type.setId(cursor.getInt(index));
                    result.setType(type);
                }

            }

        }
    }

    /**
     * Convert Cursor of database to Array of Objets entity.
     * @param cursor Cursor object
     * @return Array of Objets entity
     */
    public static ArrayList<Objets> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Objets> result = new ArrayList<Objets>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Objets item;
            do {
                item = ObjetsContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
