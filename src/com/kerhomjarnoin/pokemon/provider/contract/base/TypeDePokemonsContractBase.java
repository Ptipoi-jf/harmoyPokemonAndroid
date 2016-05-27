/**************************************************************************
 * TypeDePokemonsContractBase.java, pokemon Android
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

import com.kerhomjarnoin.pokemon.entity.TypeDePokemons;
import com.kerhomjarnoin.pokemon.entity.Types;



import com.kerhomjarnoin.pokemon.provider.contract.TypeDePokemonsContract;

/** Pokemon contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class TypeDePokemonsContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            TypeDePokemonsContract.TABLE_NAME + "." + COL_ID;

    /** nom. */
    public static final String COL_NOM =
            "nom";
    /** Alias. */
    public static final String ALIASED_COL_NOM =
            TypeDePokemonsContract.TABLE_NAME + "." + COL_NOM;

    /** attaque. */
    public static final String COL_ATTAQUE =
            "attaque";
    /** Alias. */
    public static final String ALIASED_COL_ATTAQUE =
            TypeDePokemonsContract.TABLE_NAME + "." + COL_ATTAQUE;

    /** attaque_spe. */
    public static final String COL_ATTAQUE_SPE =
            "attaque_spe";
    /** Alias. */
    public static final String ALIASED_COL_ATTAQUE_SPE =
            TypeDePokemonsContract.TABLE_NAME + "." + COL_ATTAQUE_SPE;

    /** defence. */
    public static final String COL_DEFENCE =
            "defence";
    /** Alias. */
    public static final String ALIASED_COL_DEFENCE =
            TypeDePokemonsContract.TABLE_NAME + "." + COL_DEFENCE;

    /** defence_spe. */
    public static final String COL_DEFENCE_SPE =
            "defence_spe";
    /** Alias. */
    public static final String ALIASED_COL_DEFENCE_SPE =
            TypeDePokemonsContract.TABLE_NAME + "." + COL_DEFENCE_SPE;

    /** vitesse. */
    public static final String COL_VITESSE =
            "vitesse";
    /** Alias. */
    public static final String ALIASED_COL_VITESSE =
            TypeDePokemonsContract.TABLE_NAME + "." + COL_VITESSE;

    /** pv. */
    public static final String COL_PV =
            "pv";
    /** Alias. */
    public static final String ALIASED_COL_PV =
            TypeDePokemonsContract.TABLE_NAME + "." + COL_PV;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "TypeDePokemons";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "TypeDePokemons";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        TypeDePokemonsContract.COL_ID,
        
        TypeDePokemonsContract.COL_NOM,
        
        TypeDePokemonsContract.COL_ATTAQUE,
        
        TypeDePokemonsContract.COL_ATTAQUE_SPE,
        
        TypeDePokemonsContract.COL_DEFENCE,
        
        TypeDePokemonsContract.COL_DEFENCE_SPE,
        
        TypeDePokemonsContract.COL_VITESSE,
        
        TypeDePokemonsContract.COL_PV,
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        TypeDePokemonsContract.ALIASED_COL_ID,
        
        TypeDePokemonsContract.ALIASED_COL_NOM,
        
        TypeDePokemonsContract.ALIASED_COL_ATTAQUE,
        
        TypeDePokemonsContract.ALIASED_COL_ATTAQUE_SPE,
        
        TypeDePokemonsContract.ALIASED_COL_DEFENCE,
        
        TypeDePokemonsContract.ALIASED_COL_DEFENCE_SPE,
        
        TypeDePokemonsContract.ALIASED_COL_VITESSE,
        
        TypeDePokemonsContract.ALIASED_COL_PV,
        
    };


    /**
     * Converts a TypeDePokemons into a content values.
     *
     * @param item The TypeDePokemons to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final TypeDePokemons item) {
        final ContentValues result = new ContentValues();

             result.put(TypeDePokemonsContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getNom() != null) {
                result.put(TypeDePokemonsContract.COL_NOM,
                    item.getNom());
            }

             result.put(TypeDePokemonsContract.COL_ATTAQUE,
                String.valueOf(item.getAttaque()));

             result.put(TypeDePokemonsContract.COL_ATTAQUE_SPE,
                String.valueOf(item.getAttaque_spe()));

             result.put(TypeDePokemonsContract.COL_DEFENCE,
                String.valueOf(item.getDefence()));

             result.put(TypeDePokemonsContract.COL_DEFENCE_SPE,
                String.valueOf(item.getDefence_spe()));

             result.put(TypeDePokemonsContract.COL_VITESSE,
                String.valueOf(item.getVitesse()));

             result.put(TypeDePokemonsContract.COL_PV,
                String.valueOf(item.getPv()));

 
        return result;
    }

    /**
     * Converts a Cursor into a TypeDePokemons.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted TypeDePokemons
     */
    public static TypeDePokemons cursorToItem(final android.database.Cursor cursor) {
        TypeDePokemons result = new TypeDePokemons();
        TypeDePokemonsContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to TypeDePokemons entity.
     * @param cursor Cursor object
     * @param result TypeDePokemons entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final TypeDePokemons result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(TypeDePokemonsContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(TypeDePokemonsContract.COL_NOM);

            if (index > -1) {
                result.setNom(cursor.getString(index));
            }
            index = cursor.getColumnIndex(TypeDePokemonsContract.COL_ATTAQUE);

            if (index > -1) {
                result.setAttaque(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(TypeDePokemonsContract.COL_ATTAQUE_SPE);

            if (index > -1) {
                result.setAttaque_spe(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(TypeDePokemonsContract.COL_DEFENCE);

            if (index > -1) {
                result.setDefence(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(TypeDePokemonsContract.COL_DEFENCE_SPE);

            if (index > -1) {
                result.setDefence_spe(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(TypeDePokemonsContract.COL_VITESSE);

            if (index > -1) {
                result.setVitesse(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(TypeDePokemonsContract.COL_PV);

            if (index > -1) {
                result.setPv(cursor.getInt(index));
            }

        }
    }

    /**
     * Convert Cursor of database to Array of TypeDePokemons entity.
     * @param cursor Cursor object
     * @return Array of TypeDePokemons entity
     */
    public static ArrayList<TypeDePokemons> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<TypeDePokemons> result = new ArrayList<TypeDePokemons>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            TypeDePokemons item;
            do {
                item = TypeDePokemonsContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
