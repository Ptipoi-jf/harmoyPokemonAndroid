
/**************************************************************************
 * PokemonsSQLiteAdapterBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.data.base;

import java.util.ArrayList;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import android.content.ContentValues;


import android.database.sqlite.SQLiteDatabase;


import com.google.common.base.Strings;
import com.google.common.collect.ObjectArrays;
import com.kerhomjarnoin.pokemon.data.SQLiteAdapter;
import com.kerhomjarnoin.pokemon.data.PokemonsSQLiteAdapter;
import com.kerhomjarnoin.pokemon.data.TypeDePokemonsSQLiteAdapter;
import com.kerhomjarnoin.pokemon.data.AttaquesSQLiteAdapter;
import com.kerhomjarnoin.pokemon.provider.contract.PokemonsContract;
import com.kerhomjarnoin.pokemon.provider.contract.NpcContract;
import com.kerhomjarnoin.pokemon.provider.contract.TypeDePokemonsContract;
import com.kerhomjarnoin.pokemon.provider.contract.AttaquesContract;
import com.kerhomjarnoin.pokemon.entity.Pokemons;
import com.kerhomjarnoin.pokemon.entity.TypeDePokemons;
import com.kerhomjarnoin.pokemon.entity.Attaques;

import com.kerhomjarnoin.pokemon.harmony.util.DateUtils;
import com.kerhomjarnoin.pokemon.PokemonApplication;



/** Pokemons adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit PokemonsAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class PokemonsSQLiteAdapterBase
                        extends SQLiteAdapter<Pokemons> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PokemonsDBAdapter";


    /**
     * Get the table name used in DB for your Pokemons entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return PokemonsContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your Pokemons entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = PokemonsContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the Pokemons entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return PokemonsContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + PokemonsContract.TABLE_NAME    + " ("
        
         + PokemonsContract.COL_NPCPOKEMONSINTERNAL_ID    + " INTEGER,"
         + PokemonsContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + PokemonsContract.COL_SURNOM    + " VARCHAR NOT NULL,"
         + PokemonsContract.COL_NIVEAU    + " INTEGER NOT NULL,"
         + PokemonsContract.COL_CAPTURE    + " DATETIME NOT NULL,"
         + PokemonsContract.COL_TYPEPOKEMON_ID    + " INTEGER NOT NULL,"
         + PokemonsContract.COL_ATTAQUE1_ID    + " INTEGER NOT NULL,"
         + PokemonsContract.COL_ATTAQUE2_ID    + " INTEGER,"
         + PokemonsContract.COL_ATTAQUE3_ID    + " INTEGER,"
         + PokemonsContract.COL_ATTAQUE4_ID    + " INTEGER,"

        
         + "FOREIGN KEY(" + PokemonsContract.COL_NPCPOKEMONSINTERNAL_ID + ") REFERENCES " 
             + NpcContract.TABLE_NAME 
                + " (" + NpcContract.COL_ID + "),"
         + "FOREIGN KEY(" + PokemonsContract.COL_TYPEPOKEMON_ID + ") REFERENCES " 
             + TypeDePokemonsContract.TABLE_NAME 
                + " (" + TypeDePokemonsContract.COL_ID + "),"
         + "FOREIGN KEY(" + PokemonsContract.COL_ATTAQUE1_ID + ") REFERENCES " 
             + AttaquesContract.TABLE_NAME 
                + " (" + AttaquesContract.COL_ID + "),"
         + "FOREIGN KEY(" + PokemonsContract.COL_ATTAQUE2_ID + ") REFERENCES " 
             + AttaquesContract.TABLE_NAME 
                + " (" + AttaquesContract.COL_ID + "),"
         + "FOREIGN KEY(" + PokemonsContract.COL_ATTAQUE3_ID + ") REFERENCES " 
             + AttaquesContract.TABLE_NAME 
                + " (" + AttaquesContract.COL_ID + "),"
         + "FOREIGN KEY(" + PokemonsContract.COL_ATTAQUE4_ID + ") REFERENCES " 
             + AttaquesContract.TABLE_NAME 
                + " (" + AttaquesContract.COL_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public PokemonsSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert Pokemons entity to Content Values for database.
     * @param item Pokemons entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final Pokemons item) {
        return PokemonsContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to Pokemons entity.
     * @param cursor android.database.Cursor object
     * @return Pokemons entity
     */
    public Pokemons cursorToItem(final android.database.Cursor cursor) {
        return PokemonsContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to Pokemons entity.
     * @param cursor android.database.Cursor object
     * @param result Pokemons entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final Pokemons result) {
        PokemonsContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read Pokemons by id in database.
     *
     * @param id Identify of Pokemons
     * @return Pokemons entity
     */
    public Pokemons getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final Pokemons result = this.cursorToItem(cursor);
        cursor.close();

        if (result.getTypePokemon() != null) {
            final TypeDePokemonsSQLiteAdapter typePokemonAdapter =
                    new TypeDePokemonsSQLiteAdapter(this.ctx);
            typePokemonAdapter.open(this.mDatabase);

            result.setTypePokemon(typePokemonAdapter.getByID(
                            result.getTypePokemon().getId()));
        }
        if (result.getAttaque1() != null) {
            final AttaquesSQLiteAdapter attaque1Adapter =
                    new AttaquesSQLiteAdapter(this.ctx);
            attaque1Adapter.open(this.mDatabase);

            result.setAttaque1(attaque1Adapter.getByID(
                            result.getAttaque1().getId()));
        }
        if (result.getAttaque2() != null) {
            final AttaquesSQLiteAdapter attaque2Adapter =
                    new AttaquesSQLiteAdapter(this.ctx);
            attaque2Adapter.open(this.mDatabase);

            result.setAttaque2(attaque2Adapter.getByID(
                            result.getAttaque2().getId()));
        }
        if (result.getAttaque3() != null) {
            final AttaquesSQLiteAdapter attaque3Adapter =
                    new AttaquesSQLiteAdapter(this.ctx);
            attaque3Adapter.open(this.mDatabase);

            result.setAttaque3(attaque3Adapter.getByID(
                            result.getAttaque3().getId()));
        }
        if (result.getAttaque4() != null) {
            final AttaquesSQLiteAdapter attaque4Adapter =
                    new AttaquesSQLiteAdapter(this.ctx);
            attaque4Adapter.open(this.mDatabase);

            result.setAttaque4(attaque4Adapter.getByID(
                            result.getAttaque4().getId()));
        }
        return result;
    }

    /**
     * Find & read Pokemons by NpcpokemonsInternal.
     * @param npcpokemonsinternalId npcpokemonsinternalId
     * @param orderBy Order by string (can be null)
     * @return List of Pokemons entities
     */
     public android.database.Cursor getByNpcpokemonsInternal(final int npcpokemonsinternalId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PokemonsContract.COL_NPCPOKEMONSINTERNAL_ID + "= ?";
        String idSelectionArgs = String.valueOf(npcpokemonsinternalId);
        if (!Strings.isNullOrEmpty(selection)) {
            selection += " AND " + idSelection;
            selectionArgs = ObjectArrays.concat(selectionArgs, idSelectionArgs);
        } else {
            selection = idSelection;
            selectionArgs = new String[]{idSelectionArgs};
        }
        final android.database.Cursor cursor = this.query(
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);

        return cursor;
     }
    /**
     * Find & read Pokemons by typePokemon.
     * @param typepokemonId typepokemonId
     * @param orderBy Order by string (can be null)
     * @return List of Pokemons entities
     */
     public android.database.Cursor getByTypePokemon(final int typepokemonId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PokemonsContract.COL_TYPEPOKEMON_ID + "= ?";
        String idSelectionArgs = String.valueOf(typepokemonId);
        if (!Strings.isNullOrEmpty(selection)) {
            selection += " AND " + idSelection;
            selectionArgs = ObjectArrays.concat(selectionArgs, idSelectionArgs);
        } else {
            selection = idSelection;
            selectionArgs = new String[]{idSelectionArgs};
        }
        final android.database.Cursor cursor = this.query(
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);

        return cursor;
     }
    /**
     * Find & read Pokemons by attaque1.
     * @param attaque1Id attaque1Id
     * @param orderBy Order by string (can be null)
     * @return List of Pokemons entities
     */
     public android.database.Cursor getByAttaque1(final int attaque1Id, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PokemonsContract.COL_ATTAQUE1_ID + "= ?";
        String idSelectionArgs = String.valueOf(attaque1Id);
        if (!Strings.isNullOrEmpty(selection)) {
            selection += " AND " + idSelection;
            selectionArgs = ObjectArrays.concat(selectionArgs, idSelectionArgs);
        } else {
            selection = idSelection;
            selectionArgs = new String[]{idSelectionArgs};
        }
        final android.database.Cursor cursor = this.query(
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);

        return cursor;
     }
    /**
     * Find & read Pokemons by attaque2.
     * @param attaque2Id attaque2Id
     * @param orderBy Order by string (can be null)
     * @return List of Pokemons entities
     */
     public android.database.Cursor getByAttaque2(final int attaque2Id, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PokemonsContract.COL_ATTAQUE2_ID + "= ?";
        String idSelectionArgs = String.valueOf(attaque2Id);
        if (!Strings.isNullOrEmpty(selection)) {
            selection += " AND " + idSelection;
            selectionArgs = ObjectArrays.concat(selectionArgs, idSelectionArgs);
        } else {
            selection = idSelection;
            selectionArgs = new String[]{idSelectionArgs};
        }
        final android.database.Cursor cursor = this.query(
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);

        return cursor;
     }
    /**
     * Find & read Pokemons by attaque3.
     * @param attaque3Id attaque3Id
     * @param orderBy Order by string (can be null)
     * @return List of Pokemons entities
     */
     public android.database.Cursor getByAttaque3(final int attaque3Id, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PokemonsContract.COL_ATTAQUE3_ID + "= ?";
        String idSelectionArgs = String.valueOf(attaque3Id);
        if (!Strings.isNullOrEmpty(selection)) {
            selection += " AND " + idSelection;
            selectionArgs = ObjectArrays.concat(selectionArgs, idSelectionArgs);
        } else {
            selection = idSelection;
            selectionArgs = new String[]{idSelectionArgs};
        }
        final android.database.Cursor cursor = this.query(
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);

        return cursor;
     }
    /**
     * Find & read Pokemons by attaque4.
     * @param attaque4Id attaque4Id
     * @param orderBy Order by string (can be null)
     * @return List of Pokemons entities
     */
     public android.database.Cursor getByAttaque4(final int attaque4Id, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PokemonsContract.COL_ATTAQUE4_ID + "= ?";
        String idSelectionArgs = String.valueOf(attaque4Id);
        if (!Strings.isNullOrEmpty(selection)) {
            selection += " AND " + idSelection;
            selectionArgs = ObjectArrays.concat(selectionArgs, idSelectionArgs);
        } else {
            selection = idSelection;
            selectionArgs = new String[]{idSelectionArgs};
        }
        final android.database.Cursor cursor = this.query(
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);

        return cursor;
     }

    /**
     * Read All Pokemonss entities.
     *
     * @return List of Pokemons entities
     */
    public ArrayList<Pokemons> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<Pokemons> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a Pokemons entity into database.
     *
     * @param item The Pokemons entity to persist
     * @return Id of the Pokemons entity
     */
    public long insert(final Pokemons item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + PokemonsContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PokemonsContract.itemToContentValues(item, 0);
        values.remove(PokemonsContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    PokemonsContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a Pokemons entity into database whether.
     * it already exists or not.
     *
     * @param item The Pokemons entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final Pokemons item) {
        int result = 0;
        if (this.getByID(item.getId()) != null) {
            // Item already exists => update it
            result = this.update(item);
        } else {
            // Item doesn't exist => create it
            final long id = this.insert(item);
            if (id != 0) {
                result = 1;
            }
        }

        return result;
    }

    /**
     * Update a Pokemons entity into database.
     *
     * @param item The Pokemons entity to persist
     * @return count of updated entities
     */
    public int update(final Pokemons item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + PokemonsContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PokemonsContract.itemToContentValues(item, 0);
        final String whereClause =
                 PokemonsContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Update a Pokemons entity into database.
     *
     * @param item The Pokemons entity to persist
     * @param npcId The npc id
     * @return count of updated entities
     */
    public int updateWithNpcPokemons(
                    Pokemons item,
                    int npcpokemonsInternalId) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + PokemonsContract.TABLE_NAME + ")");
        }

        ContentValues values =
                PokemonsContract.itemToContentValues(item);
        values.put(
                PokemonsContract.COL_NPCPOKEMONSINTERNAL_ID,
                npcpokemonsInternalId);
        String whereClause =
                 PokemonsContract.COL_ID
                 + "=?";
        String[] whereArgs =
                new String[] {String.valueOf(item.getId())};

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Either insert or update a Pokemons entity into database whether.
     * it already exists or not.
     *
     * @param item The Pokemons entity to persist
     * @param npcId The npc id
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdateWithNpcPokemons(
            Pokemons item, int npcId) {
        int result = 0;
        if (this.getByID(item.getId()) != null) {
            // Item already exists => update it
            result = this.updateWithNpcPokemons(item,
                    npcId);
        } else {
            // Item doesn't exist => create it
            long id = this.insertWithNpcPokemons(item,
                    npcId);
            if (id != 0) {
                result = 1;
            }
        }

        return result;
    }


    /**
     * Insert a Pokemons entity into database.
     *
     * @param item The Pokemons entity to persist
     * @param npcId The npc id
     * @return Id of the Pokemons entity
     */
    public long insertWithNpcPokemons(
            Pokemons item, int npcId) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + PokemonsContract.TABLE_NAME + ")");
        }

        ContentValues values = PokemonsContract.itemToContentValues(item,
                npcId);
        values.remove(PokemonsContract.COL_ID);
        int newid = (int) this.insert(
            null,
            values);


        return newid;
    }


    /**
     * Delete a Pokemons entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + PokemonsContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                PokemonsContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param pokemons The entity to delete
     * @return count of updated entities
     */
    public int delete(final Pokemons pokemons) {
        return this.remove(pokemons.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the Pokemons corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                PokemonsContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                PokemonsContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a Pokemons entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = PokemonsContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                PokemonsContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

