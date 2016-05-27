/**************************************************************************
 * PokemonsContractBase.java, pokemon Android
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

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import com.kerhomjarnoin.pokemon.entity.Pokemons;
import com.kerhomjarnoin.pokemon.entity.TypeDePokemons;
import com.kerhomjarnoin.pokemon.entity.Attaques;



import com.kerhomjarnoin.pokemon.provider.contract.PokemonsContract;
import com.kerhomjarnoin.pokemon.harmony.util.DateUtils;

/** Pokemon contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class PokemonsContractBase {


        /** NpcpokemonsInternal_id. */
    public static final String COL_NPCPOKEMONSINTERNAL_ID =
            "Npc_pokemons_internal_id";
    /** Alias. */
    public static final String ALIASED_COL_NPCPOKEMONSINTERNAL_ID =
            PokemonsContract.TABLE_NAME + "." + COL_NPCPOKEMONSINTERNAL_ID;

    /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            PokemonsContract.TABLE_NAME + "." + COL_ID;

    /** surnom. */
    public static final String COL_SURNOM =
            "surnom";
    /** Alias. */
    public static final String ALIASED_COL_SURNOM =
            PokemonsContract.TABLE_NAME + "." + COL_SURNOM;

    /** niveau. */
    public static final String COL_NIVEAU =
            "niveau";
    /** Alias. */
    public static final String ALIASED_COL_NIVEAU =
            PokemonsContract.TABLE_NAME + "." + COL_NIVEAU;

    /** capture. */
    public static final String COL_CAPTURE =
            "capture";
    /** Alias. */
    public static final String ALIASED_COL_CAPTURE =
            PokemonsContract.TABLE_NAME + "." + COL_CAPTURE;

    /** typePokemon_id. */
    public static final String COL_TYPEPOKEMON_ID =
            "typePokemon_id";
    /** Alias. */
    public static final String ALIASED_COL_TYPEPOKEMON_ID =
            PokemonsContract.TABLE_NAME + "." + COL_TYPEPOKEMON_ID;

    /** attaque1_id. */
    public static final String COL_ATTAQUE1_ID =
            "attaque1_id";
    /** Alias. */
    public static final String ALIASED_COL_ATTAQUE1_ID =
            PokemonsContract.TABLE_NAME + "." + COL_ATTAQUE1_ID;

    /** attaque2_id. */
    public static final String COL_ATTAQUE2_ID =
            "attaque2_id";
    /** Alias. */
    public static final String ALIASED_COL_ATTAQUE2_ID =
            PokemonsContract.TABLE_NAME + "." + COL_ATTAQUE2_ID;

    /** attaque3_id. */
    public static final String COL_ATTAQUE3_ID =
            "attaque3_id";
    /** Alias. */
    public static final String ALIASED_COL_ATTAQUE3_ID =
            PokemonsContract.TABLE_NAME + "." + COL_ATTAQUE3_ID;

    /** attaque4_id. */
    public static final String COL_ATTAQUE4_ID =
            "attaque4_id";
    /** Alias. */
    public static final String ALIASED_COL_ATTAQUE4_ID =
            PokemonsContract.TABLE_NAME + "." + COL_ATTAQUE4_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Pokemons";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Pokemons";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        PokemonsContract.COL_NPCPOKEMONSINTERNAL_ID,
        
        PokemonsContract.COL_ID,
        
        PokemonsContract.COL_SURNOM,
        
        PokemonsContract.COL_NIVEAU,
        
        PokemonsContract.COL_CAPTURE,
        
        PokemonsContract.COL_TYPEPOKEMON_ID,
        
        PokemonsContract.COL_ATTAQUE1_ID,
        
        PokemonsContract.COL_ATTAQUE2_ID,
        
        PokemonsContract.COL_ATTAQUE3_ID,
        
        PokemonsContract.COL_ATTAQUE4_ID
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        PokemonsContract.ALIASED_COL_NPCPOKEMONSINTERNAL_ID,
        
        PokemonsContract.ALIASED_COL_ID,
        
        PokemonsContract.ALIASED_COL_SURNOM,
        
        PokemonsContract.ALIASED_COL_NIVEAU,
        
        PokemonsContract.ALIASED_COL_CAPTURE,
        
        PokemonsContract.ALIASED_COL_TYPEPOKEMON_ID,
        
        PokemonsContract.ALIASED_COL_ATTAQUE1_ID,
        
        PokemonsContract.ALIASED_COL_ATTAQUE2_ID,
        
        PokemonsContract.ALIASED_COL_ATTAQUE3_ID,
        
        PokemonsContract.ALIASED_COL_ATTAQUE4_ID
    };

    /** Convert Pokemons entity to Content Values for database.
     *
     * @param item Pokemons entity object
     * @param npcId npc id
     * @return ContentValues object
     */
    public static ContentValues itemToContentValues(final Pokemons item,
                final int npcpokemonsInternalId) {
        final ContentValues result = PokemonsContract.itemToContentValues(item);
        result.put(PokemonsContract.COL_NPCPOKEMONSINTERNAL_ID,
                String.valueOf(npcpokemonsInternalId));
        return result;
    }

    /**
     * Converts a Pokemons into a content values.
     *
     * @param item The Pokemons to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Pokemons item) {
        final ContentValues result = new ContentValues();

              result.put(PokemonsContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getSurnom() != null) {
                result.put(PokemonsContract.COL_SURNOM,
                    item.getSurnom());
            }

             result.put(PokemonsContract.COL_NIVEAU,
                String.valueOf(item.getNiveau()));

             if (item.getCapture() != null) {
                result.put(PokemonsContract.COL_CAPTURE,
                    item.getCapture().toString(ISODateTimeFormat.dateTime()));
            }

             if (item.getTypePokemon() != null) {
                result.put(PokemonsContract.COL_TYPEPOKEMON_ID,
                    item.getTypePokemon().getId());
            }

             if (item.getAttaque1() != null) {
                result.put(PokemonsContract.COL_ATTAQUE1_ID,
                    item.getAttaque1().getId());
            }

             if (item.getAttaque2() != null) {
                result.put(PokemonsContract.COL_ATTAQUE2_ID,
                    item.getAttaque2().getId());
            } else {
                result.put(PokemonsContract.COL_ATTAQUE2_ID, (String) null);
            }

             if (item.getAttaque3() != null) {
                result.put(PokemonsContract.COL_ATTAQUE3_ID,
                    item.getAttaque3().getId());
            } else {
                result.put(PokemonsContract.COL_ATTAQUE3_ID, (String) null);
            }

             if (item.getAttaque4() != null) {
                result.put(PokemonsContract.COL_ATTAQUE4_ID,
                    item.getAttaque4().getId());
            } else {
                result.put(PokemonsContract.COL_ATTAQUE4_ID, (String) null);
            }


        return result;
    }

    /**
     * Converts a Cursor into a Pokemons.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Pokemons
     */
    public static Pokemons cursorToItem(final android.database.Cursor cursor) {
        Pokemons result = new Pokemons();
        PokemonsContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Pokemons entity.
     * @param cursor Cursor object
     * @param result Pokemons entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Pokemons result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(PokemonsContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(PokemonsContract.COL_SURNOM);

            if (index > -1) {
                result.setSurnom(cursor.getString(index));
            }
            index = cursor.getColumnIndex(PokemonsContract.COL_NIVEAU);

            if (index > -1) {
                result.setNiveau(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(PokemonsContract.COL_CAPTURE);

            if (index > -1) {
                final DateTime dtCapture =
                        DateUtils.formatISOStringToDateTime(cursor.getString(index));
                if (dtCapture != null) {
                        result.setCapture(dtCapture);
                } else {
                    result.setCapture(new DateTime());
                }
            }
            if (result.getTypePokemon() == null) {
                final TypeDePokemons typePokemon = new TypeDePokemons();
                index = cursor.getColumnIndex(PokemonsContract.COL_TYPEPOKEMON_ID);

                if (index > -1) {
                    typePokemon.setId(cursor.getInt(index));
                    result.setTypePokemon(typePokemon);
                }

            }
            if (result.getAttaque1() == null) {
                final Attaques attaque1 = new Attaques();
                index = cursor.getColumnIndex(PokemonsContract.COL_ATTAQUE1_ID);

                if (index > -1) {
                    attaque1.setId(cursor.getInt(index));
                    result.setAttaque1(attaque1);
                }

            }
            if (result.getAttaque2() == null) {
                final Attaques attaque2 = new Attaques();
                index = cursor.getColumnIndex(PokemonsContract.COL_ATTAQUE2_ID);

                if (index > -1) {
                    if (!cursor.isNull(index)) {
                        attaque2.setId(cursor.getInt(index));
                        result.setAttaque2(attaque2);
                    }
                }

            }
            if (result.getAttaque3() == null) {
                final Attaques attaque3 = new Attaques();
                index = cursor.getColumnIndex(PokemonsContract.COL_ATTAQUE3_ID);

                if (index > -1) {
                    if (!cursor.isNull(index)) {
                        attaque3.setId(cursor.getInt(index));
                        result.setAttaque3(attaque3);
                    }
                }

            }
            if (result.getAttaque4() == null) {
                final Attaques attaque4 = new Attaques();
                index = cursor.getColumnIndex(PokemonsContract.COL_ATTAQUE4_ID);

                if (index > -1) {
                    if (!cursor.isNull(index)) {
                        attaque4.setId(cursor.getInt(index));
                        result.setAttaque4(attaque4);
                    }
                }

            }

        }
    }

    /**
     * Convert Cursor of database to Array of Pokemons entity.
     * @param cursor Cursor object
     * @return Array of Pokemons entity
     */
    public static ArrayList<Pokemons> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Pokemons> result = new ArrayList<Pokemons>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Pokemons item;
            do {
                item = PokemonsContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
