/**************************************************************************
 * NpcContractBase.java, pokemon Android
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

import com.kerhomjarnoin.pokemon.entity.Npc;
import com.kerhomjarnoin.pokemon.entity.Objets;
import com.kerhomjarnoin.pokemon.entity.Pokemons;
import com.kerhomjarnoin.pokemon.entity.Badges;
import com.kerhomjarnoin.pokemon.entity.Positions;



import com.kerhomjarnoin.pokemon.provider.contract.NpcContract;

/** Pokemon contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class NpcContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            NpcContract.TABLE_NAME + "." + COL_ID;

    /** nom. */
    public static final String COL_NOM =
            "nom";
    /** Alias. */
    public static final String ALIASED_COL_NOM =
            NpcContract.TABLE_NAME + "." + COL_NOM;

    /** profession. */
    public static final String COL_PROFESSION =
            "profession";
    /** Alias. */
    public static final String ALIASED_COL_PROFESSION =
            NpcContract.TABLE_NAME + "." + COL_PROFESSION;

    /** texte. */
    public static final String COL_TEXTE =
            "texte";
    /** Alias. */
    public static final String ALIASED_COL_TEXTE =
            NpcContract.TABLE_NAME + "." + COL_TEXTE;

    /** position_id. */
    public static final String COL_POSITION_ID =
            "position_id";
    /** Alias. */
    public static final String ALIASED_COL_POSITION_ID =
            NpcContract.TABLE_NAME + "." + COL_POSITION_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Npc";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Npc";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        NpcContract.COL_ID,
        
        NpcContract.COL_NOM,
        
        NpcContract.COL_PROFESSION,
        
        NpcContract.COL_TEXTE,
        
        NpcContract.COL_POSITION_ID
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        NpcContract.ALIASED_COL_ID,
        
        NpcContract.ALIASED_COL_NOM,
        
        NpcContract.ALIASED_COL_PROFESSION,
        
        NpcContract.ALIASED_COL_TEXTE,
        
        
        
        
        NpcContract.ALIASED_COL_POSITION_ID
    };


    /**
     * Converts a Npc into a content values.
     *
     * @param item The Npc to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Npc item) {
        final ContentValues result = new ContentValues();

             result.put(NpcContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getNom() != null) {
                result.put(NpcContract.COL_NOM,
                    item.getNom());
            }

             if (item.getProfession() != null) {
                result.put(NpcContract.COL_PROFESSION,
                    item.getProfession());
            }

             if (item.getTexte() != null) {
                result.put(NpcContract.COL_TEXTE,
                    item.getTexte());
            }

                if (item.getPosition() != null) {
                result.put(NpcContract.COL_POSITION_ID,
                    item.getPosition().getId());
            } else {
                result.put(NpcContract.COL_POSITION_ID, (String) null);
            }


        return result;
    }

    /**
     * Converts a Cursor into a Npc.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Npc
     */
    public static Npc cursorToItem(final android.database.Cursor cursor) {
        Npc result = new Npc();
        NpcContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Npc entity.
     * @param cursor Cursor object
     * @param result Npc entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Npc result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(NpcContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(NpcContract.COL_NOM);

            if (index > -1) {
                result.setNom(cursor.getString(index));
            }
            index = cursor.getColumnIndex(NpcContract.COL_PROFESSION);

            if (index > -1) {
                result.setProfession(cursor.getString(index));
            }
            index = cursor.getColumnIndex(NpcContract.COL_TEXTE);

            if (index > -1) {
                result.setTexte(cursor.getString(index));
            }
            if (result.getPosition() == null) {
                final Positions position = new Positions();
                index = cursor.getColumnIndex(NpcContract.COL_POSITION_ID);

                if (index > -1) {
                    if (!cursor.isNull(index)) {
                        position.setId(cursor.getInt(index));
                        result.setPosition(position);
                    }
                }

            }

        }
    }

    /**
     * Convert Cursor of database to Array of Npc entity.
     * @param cursor Cursor object
     * @return Array of Npc entity
     */
    public static ArrayList<Npc> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Npc> result = new ArrayList<Npc>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Npc item;
            do {
                item = NpcContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
