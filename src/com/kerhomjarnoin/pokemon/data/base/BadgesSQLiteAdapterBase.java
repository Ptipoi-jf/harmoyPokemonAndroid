
/**************************************************************************
 * BadgesSQLiteAdapterBase.java, pokemon Android
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
import com.kerhomjarnoin.pokemon.data.BadgesSQLiteAdapter;
import com.kerhomjarnoin.pokemon.provider.contract.BadgesContract;
import com.kerhomjarnoin.pokemon.entity.Badges;


import com.kerhomjarnoin.pokemon.PokemonApplication;



/** Badges adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit BadgesAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class BadgesSQLiteAdapterBase
                        extends SQLiteAdapter<Badges> {

    /** TAG for debug purpose. */
    protected static final String TAG = "BadgesDBAdapter";


    /**
     * Get the table name used in DB for your Badges entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return BadgesContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your Badges entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = BadgesContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the Badges entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return BadgesContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + BadgesContract.TABLE_NAME    + " ("
        
         + BadgesContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + BadgesContract.COL_NOM    + " VARCHAR NOT NULL"

        
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public BadgesSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert Badges entity to Content Values for database.
     * @param item Badges entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final Badges item) {
        return BadgesContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to Badges entity.
     * @param cursor android.database.Cursor object
     * @return Badges entity
     */
    public Badges cursorToItem(final android.database.Cursor cursor) {
        return BadgesContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to Badges entity.
     * @param cursor android.database.Cursor object
     * @param result Badges entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final Badges result) {
        BadgesContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read Badges by id in database.
     *
     * @param id Identify of Badges
     * @return Badges entity
     */
    public Badges getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final Badges result = this.cursorToItem(cursor);
        cursor.close();

        return result;
    }


    /**
     * Read All Badgess entities.
     *
     * @return List of Badges entities
     */
    public ArrayList<Badges> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<Badges> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a Badges entity into database.
     *
     * @param item The Badges entity to persist
     * @return Id of the Badges entity
     */
    public long insert(final Badges item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + BadgesContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                BadgesContract.itemToContentValues(item);
        values.remove(BadgesContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    BadgesContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a Badges entity into database whether.
     * it already exists or not.
     *
     * @param item The Badges entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final Badges item) {
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
     * Update a Badges entity into database.
     *
     * @param item The Badges entity to persist
     * @return count of updated entities
     */
    public int update(final Badges item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + BadgesContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                BadgesContract.itemToContentValues(item);
        final String whereClause =
                 BadgesContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a Badges entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + BadgesContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                BadgesContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param badges The entity to delete
     * @return count of updated entities
     */
    public int delete(final Badges badges) {
        return this.remove(badges.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the Badges corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                BadgesContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                BadgesContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a Badges entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = BadgesContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                BadgesContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

