
/**************************************************************************
 * ZonesSQLiteAdapterBase.java, pokemon Android
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
import com.kerhomjarnoin.pokemon.data.ZonesSQLiteAdapter;
import com.kerhomjarnoin.pokemon.provider.contract.ZonesContract;
import com.kerhomjarnoin.pokemon.entity.Zones;


import com.kerhomjarnoin.pokemon.PokemonApplication;



/** Zones adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit ZonesAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class ZonesSQLiteAdapterBase
                        extends SQLiteAdapter<Zones> {

    /** TAG for debug purpose. */
    protected static final String TAG = "ZonesDBAdapter";


    /**
     * Get the table name used in DB for your Zones entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return ZonesContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your Zones entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = ZonesContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the Zones entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return ZonesContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + ZonesContract.TABLE_NAME    + " ("
        
         + ZonesContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + ZonesContract.COL_NOM    + " VARCHAR NOT NULL"

        
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public ZonesSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert Zones entity to Content Values for database.
     * @param item Zones entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final Zones item) {
        return ZonesContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to Zones entity.
     * @param cursor android.database.Cursor object
     * @return Zones entity
     */
    public Zones cursorToItem(final android.database.Cursor cursor) {
        return ZonesContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to Zones entity.
     * @param cursor android.database.Cursor object
     * @param result Zones entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final Zones result) {
        ZonesContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read Zones by id in database.
     *
     * @param id Identify of Zones
     * @return Zones entity
     */
    public Zones getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final Zones result = this.cursorToItem(cursor);
        cursor.close();

        return result;
    }


    /**
     * Read All Zoness entities.
     *
     * @return List of Zones entities
     */
    public ArrayList<Zones> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<Zones> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a Zones entity into database.
     *
     * @param item The Zones entity to persist
     * @return Id of the Zones entity
     */
    public long insert(final Zones item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + ZonesContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                ZonesContract.itemToContentValues(item);
        values.remove(ZonesContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    ZonesContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a Zones entity into database whether.
     * it already exists or not.
     *
     * @param item The Zones entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final Zones item) {
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
     * Update a Zones entity into database.
     *
     * @param item The Zones entity to persist
     * @return count of updated entities
     */
    public int update(final Zones item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + ZonesContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                ZonesContract.itemToContentValues(item);
        final String whereClause =
                 ZonesContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a Zones entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + ZonesContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                ZonesContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param zones The entity to delete
     * @return count of updated entities
     */
    public int delete(final Zones zones) {
        return this.remove(zones.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the Zones corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                ZonesContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                ZonesContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a Zones entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = ZonesContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                ZonesContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

