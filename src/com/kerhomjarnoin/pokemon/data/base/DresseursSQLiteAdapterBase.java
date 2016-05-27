
/**************************************************************************
 * DresseursSQLiteAdapterBase.java, pokemon Android
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
import android.content.ContentValues;


import android.database.sqlite.SQLiteDatabase;


import com.google.common.base.Strings;
import com.google.common.collect.ObjectArrays;
import com.kerhomjarnoin.pokemon.data.SQLiteAdapter;
import com.kerhomjarnoin.pokemon.data.DresseursSQLiteAdapter;
import com.kerhomjarnoin.pokemon.data.NpcSQLiteAdapter;
import com.kerhomjarnoin.pokemon.provider.contract.DresseursContract;
import com.kerhomjarnoin.pokemon.provider.contract.NpcContract;
import com.kerhomjarnoin.pokemon.entity.Dresseurs;
import com.kerhomjarnoin.pokemon.entity.Npc;


import com.kerhomjarnoin.pokemon.PokemonApplication;



/** Dresseurs adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit DresseursAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class DresseursSQLiteAdapterBase
                        extends SQLiteAdapter<Dresseurs> {

    /** TAG for debug purpose. */
    protected static final String TAG = "DresseursDBAdapter";


    /**
     * Get the table name used in DB for your Dresseurs entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return DresseursContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your Dresseurs entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = DresseursContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the Dresseurs entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return DresseursContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + DresseursContract.TABLE_NAME    + " ("
        
         + DresseursContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + DresseursContract.COL_NOM    + " VARCHAR NOT NULL,"
         + DresseursContract.COL_NPC_ID    + " INTEGER,"

        
         + "FOREIGN KEY(" + DresseursContract.COL_NPC_ID + ") REFERENCES " 
             + NpcContract.TABLE_NAME 
                + " (" + NpcContract.COL_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public DresseursSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert Dresseurs entity to Content Values for database.
     * @param item Dresseurs entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final Dresseurs item) {
        return DresseursContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to Dresseurs entity.
     * @param cursor android.database.Cursor object
     * @return Dresseurs entity
     */
    public Dresseurs cursorToItem(final android.database.Cursor cursor) {
        return DresseursContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to Dresseurs entity.
     * @param cursor android.database.Cursor object
     * @param result Dresseurs entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final Dresseurs result) {
        DresseursContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read Dresseurs by id in database.
     *
     * @param id Identify of Dresseurs
     * @return Dresseurs entity
     */
    public Dresseurs getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final Dresseurs result = this.cursorToItem(cursor);
        cursor.close();

        if (result.getNpc() != null) {
            final NpcSQLiteAdapter npcAdapter =
                    new NpcSQLiteAdapter(this.ctx);
            npcAdapter.open(this.mDatabase);

            result.setNpc(npcAdapter.getByID(
                            result.getNpc().getId()));
        }
        return result;
    }

    /**
     * Find & read Dresseurs by npc.
     * @param npcId npcId
     * @param orderBy Order by string (can be null)
     * @return List of Dresseurs entities
     */
     public android.database.Cursor getByNpc(final int npcId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = DresseursContract.COL_NPC_ID + "= ?";
        String idSelectionArgs = String.valueOf(npcId);
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
     * Read All Dresseurss entities.
     *
     * @return List of Dresseurs entities
     */
    public ArrayList<Dresseurs> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<Dresseurs> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a Dresseurs entity into database.
     *
     * @param item The Dresseurs entity to persist
     * @return Id of the Dresseurs entity
     */
    public long insert(final Dresseurs item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + DresseursContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                DresseursContract.itemToContentValues(item);
        values.remove(DresseursContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    DresseursContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a Dresseurs entity into database whether.
     * it already exists or not.
     *
     * @param item The Dresseurs entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final Dresseurs item) {
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
     * Update a Dresseurs entity into database.
     *
     * @param item The Dresseurs entity to persist
     * @return count of updated entities
     */
    public int update(final Dresseurs item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + DresseursContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                DresseursContract.itemToContentValues(item);
        final String whereClause =
                 DresseursContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a Dresseurs entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + DresseursContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                DresseursContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param dresseurs The entity to delete
     * @return count of updated entities
     */
    public int delete(final Dresseurs dresseurs) {
        return this.remove(dresseurs.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the Dresseurs corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                DresseursContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                DresseursContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a Dresseurs entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = DresseursContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                DresseursContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

