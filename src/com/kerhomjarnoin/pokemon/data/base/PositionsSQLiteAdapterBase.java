
/**************************************************************************
 * PositionsSQLiteAdapterBase.java, pokemon Android
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


import com.kerhomjarnoin.pokemon.data.SQLiteAdapter;
import com.kerhomjarnoin.pokemon.data.PositionsSQLiteAdapter;
import com.kerhomjarnoin.pokemon.provider.contract.PositionsContract;
import com.kerhomjarnoin.pokemon.entity.Positions;


import com.kerhomjarnoin.pokemon.PokemonApplication;



/** Positions adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit PositionsAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class PositionsSQLiteAdapterBase
                        extends SQLiteAdapter<Positions> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PositionsDBAdapter";


    /**
     * Get the table name used in DB for your Positions entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return PositionsContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your Positions entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = PositionsContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the Positions entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return PositionsContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + PositionsContract.TABLE_NAME    + " ("
        
         + PositionsContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + PositionsContract.COL_X    + " INTEGER NOT NULL,"
         + PositionsContract.COL_Y    + " INTEGER NOT NULL"

        
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public PositionsSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert Positions entity to Content Values for database.
     * @param item Positions entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final Positions item) {
        return PositionsContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to Positions entity.
     * @param cursor android.database.Cursor object
     * @return Positions entity
     */
    public Positions cursorToItem(final android.database.Cursor cursor) {
        return PositionsContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to Positions entity.
     * @param cursor android.database.Cursor object
     * @param result Positions entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final Positions result) {
        PositionsContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read Positions by id in database.
     *
     * @param id Identify of Positions
     * @return Positions entity
     */
    public Positions getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final Positions result = this.cursorToItem(cursor);
        cursor.close();

        return result;
    }


    /**
     * Read All Positionss entities.
     *
     * @return List of Positions entities
     */
    public ArrayList<Positions> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<Positions> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a Positions entity into database.
     *
     * @param item The Positions entity to persist
     * @return Id of the Positions entity
     */
    public long insert(final Positions item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + PositionsContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PositionsContract.itemToContentValues(item);
        values.remove(PositionsContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    PositionsContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a Positions entity into database whether.
     * it already exists or not.
     *
     * @param item The Positions entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final Positions item) {
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
     * Update a Positions entity into database.
     *
     * @param item The Positions entity to persist
     * @return count of updated entities
     */
    public int update(final Positions item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + PositionsContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PositionsContract.itemToContentValues(item);
        final String whereClause =
                 PositionsContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a Positions entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + PositionsContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                PositionsContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param positions The entity to delete
     * @return count of updated entities
     */
    public int delete(final Positions positions) {
        return this.remove(positions.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the Positions corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                PositionsContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                PositionsContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a Positions entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = PositionsContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                PositionsContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

