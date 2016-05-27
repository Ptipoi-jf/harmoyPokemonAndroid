
/**************************************************************************
 * AttaquesSQLiteAdapterBase.java, pokemon Android
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
import com.kerhomjarnoin.pokemon.data.AttaquesSQLiteAdapter;
import com.kerhomjarnoin.pokemon.data.TypesSQLiteAdapter;
import com.kerhomjarnoin.pokemon.provider.contract.AttaquesContract;
import com.kerhomjarnoin.pokemon.provider.contract.TypesContract;
import com.kerhomjarnoin.pokemon.entity.Attaques;
import com.kerhomjarnoin.pokemon.entity.Types;


import com.kerhomjarnoin.pokemon.PokemonApplication;



/** Attaques adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit AttaquesAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class AttaquesSQLiteAdapterBase
                        extends SQLiteAdapter<Attaques> {

    /** TAG for debug purpose. */
    protected static final String TAG = "AttaquesDBAdapter";


    /**
     * Get the table name used in DB for your Attaques entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return AttaquesContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your Attaques entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = AttaquesContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the Attaques entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return AttaquesContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + AttaquesContract.TABLE_NAME    + " ("
        
         + AttaquesContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + AttaquesContract.COL_NOM    + " VARCHAR NOT NULL,"
         + AttaquesContract.COL_PUISSANCE    + " INTEGER NOT NULL,"
         + AttaquesContract.COL_PRECIS    + " INTEGER NOT NULL,"
         + AttaquesContract.COL_TYPE_ID    + " INTEGER NOT NULL,"

        
         + "FOREIGN KEY(" + AttaquesContract.COL_TYPE_ID + ") REFERENCES " 
             + TypesContract.TABLE_NAME 
                + " (" + TypesContract.COL_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public AttaquesSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert Attaques entity to Content Values for database.
     * @param item Attaques entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final Attaques item) {
        return AttaquesContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to Attaques entity.
     * @param cursor android.database.Cursor object
     * @return Attaques entity
     */
    public Attaques cursorToItem(final android.database.Cursor cursor) {
        return AttaquesContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to Attaques entity.
     * @param cursor android.database.Cursor object
     * @param result Attaques entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final Attaques result) {
        AttaquesContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read Attaques by id in database.
     *
     * @param id Identify of Attaques
     * @return Attaques entity
     */
    public Attaques getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final Attaques result = this.cursorToItem(cursor);
        cursor.close();

        if (result.getType() != null) {
            final TypesSQLiteAdapter typeAdapter =
                    new TypesSQLiteAdapter(this.ctx);
            typeAdapter.open(this.mDatabase);

            result.setType(typeAdapter.getByID(
                            result.getType().getId()));
        }
        return result;
    }

    /**
     * Find & read Attaques by type.
     * @param typeId typeId
     * @param orderBy Order by string (can be null)
     * @return List of Attaques entities
     */
     public android.database.Cursor getByType(final int typeId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = AttaquesContract.COL_TYPE_ID + "= ?";
        String idSelectionArgs = String.valueOf(typeId);
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
     * Read All Attaquess entities.
     *
     * @return List of Attaques entities
     */
    public ArrayList<Attaques> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<Attaques> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a Attaques entity into database.
     *
     * @param item The Attaques entity to persist
     * @return Id of the Attaques entity
     */
    public long insert(final Attaques item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + AttaquesContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                AttaquesContract.itemToContentValues(item);
        values.remove(AttaquesContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    AttaquesContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a Attaques entity into database whether.
     * it already exists or not.
     *
     * @param item The Attaques entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final Attaques item) {
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
     * Update a Attaques entity into database.
     *
     * @param item The Attaques entity to persist
     * @return count of updated entities
     */
    public int update(final Attaques item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + AttaquesContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                AttaquesContract.itemToContentValues(item);
        final String whereClause =
                 AttaquesContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a Attaques entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + AttaquesContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                AttaquesContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param attaques The entity to delete
     * @return count of updated entities
     */
    public int delete(final Attaques attaques) {
        return this.remove(attaques.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the Attaques corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                AttaquesContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                AttaquesContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a Attaques entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = AttaquesContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                AttaquesContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

