/**************************************************************************
 * TypesContractBase.java, pokemon Android
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

import com.kerhomjarnoin.pokemon.entity.Types;



import com.kerhomjarnoin.pokemon.provider.contract.TypesContract;

/** Pokemon contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class TypesContractBase {


        /** TypeDePokemonstypesInternal_id. */
    public static final String COL_TYPEDEPOKEMONSTYPESINTERNAL_ID =
            "TypeDePokemons_types_internal_id";
    /** Alias. */
    public static final String ALIASED_COL_TYPEDEPOKEMONSTYPESINTERNAL_ID =
            TypesContract.TABLE_NAME + "." + COL_TYPEDEPOKEMONSTYPESINTERNAL_ID;

    /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            TypesContract.TABLE_NAME + "." + COL_ID;

    /** nom. */
    public static final String COL_NOM =
            "nom";
    /** Alias. */
    public static final String ALIASED_COL_NOM =
            TypesContract.TABLE_NAME + "." + COL_NOM;

    /** TypesfaibleContreInternal_id. */
    public static final String COL_TYPESFAIBLECONTREINTERNAL_ID =
            "Types_faibleContre_internal_id";
    /** Alias. */
    public static final String ALIASED_COL_TYPESFAIBLECONTREINTERNAL_ID =
            TypesContract.TABLE_NAME + "." + COL_TYPESFAIBLECONTREINTERNAL_ID;

    /** TypesfortContreInternal_id. */
    public static final String COL_TYPESFORTCONTREINTERNAL_ID =
            "Types_fortContre_internal_id";
    /** Alias. */
    public static final String ALIASED_COL_TYPESFORTCONTREINTERNAL_ID =
            TypesContract.TABLE_NAME + "." + COL_TYPESFORTCONTREINTERNAL_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Types";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Types";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        TypesContract.COL_TYPEDEPOKEMONSTYPESINTERNAL_ID,
        
        TypesContract.COL_ID,
        
        TypesContract.COL_NOM,
        
        TypesContract.COL_TYPESFAIBLECONTREINTERNAL_ID,
        
        TypesContract.COL_TYPESFORTCONTREINTERNAL_ID,
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        TypesContract.ALIASED_COL_TYPEDEPOKEMONSTYPESINTERNAL_ID,
        
        TypesContract.ALIASED_COL_ID,
        
        TypesContract.ALIASED_COL_NOM,
        
        TypesContract.ALIASED_COL_TYPESFAIBLECONTREINTERNAL_ID,
        
        
        TypesContract.ALIASED_COL_TYPESFORTCONTREINTERNAL_ID,
        
    };

    /** Convert Types entity to Content Values for database.
     *
     * @param item Types entity object
     * @param typedepokemonsId typedepokemons id
     * @param typesId types id
     * @param typesId types id
     * @return ContentValues object
     */
    public static ContentValues itemToContentValues(final Types item,
                final int typeDePokemonstypesInternalId,
                final int typesfaibleContreInternalId,
                final int typesfortContreInternalId) {
        final ContentValues result = TypesContract.itemToContentValues(item);
        result.put(TypesContract.COL_TYPEDEPOKEMONSTYPESINTERNAL_ID,
                String.valueOf(typeDePokemonstypesInternalId));
        result.put(TypesContract.COL_TYPESFAIBLECONTREINTERNAL_ID,
                String.valueOf(typesfaibleContreInternalId));
        result.put(TypesContract.COL_TYPESFORTCONTREINTERNAL_ID,
                String.valueOf(typesfortContreInternalId));
        return result;
    }

    /**
     * Converts a Types into a content values.
     *
     * @param item The Types to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Types item) {
        final ContentValues result = new ContentValues();

              result.put(TypesContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getNom() != null) {
                result.put(TypesContract.COL_NOM,
                    item.getNom());
            }

    
        return result;
    }

    /**
     * Converts a Cursor into a Types.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Types
     */
    public static Types cursorToItem(final android.database.Cursor cursor) {
        Types result = new Types();
        TypesContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Types entity.
     * @param cursor Cursor object
     * @param result Types entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Types result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(TypesContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(TypesContract.COL_NOM);

            if (index > -1) {
                result.setNom(cursor.getString(index));
            }

        }
    }

    /**
     * Convert Cursor of database to Array of Types entity.
     * @param cursor Cursor object
     * @return Array of Types entity
     */
    public static ArrayList<Types> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Types> result = new ArrayList<Types>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Types item;
            do {
                item = TypesContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
