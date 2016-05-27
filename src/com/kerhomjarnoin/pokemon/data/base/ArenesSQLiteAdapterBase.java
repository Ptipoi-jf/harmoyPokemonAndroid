
/**************************************************************************
 * ArenesSQLiteAdapterBase.java, pokemon Android
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
import com.kerhomjarnoin.pokemon.data.ArenesSQLiteAdapter;
import com.kerhomjarnoin.pokemon.data.PositionsSQLiteAdapter;
import com.kerhomjarnoin.pokemon.data.BadgesSQLiteAdapter;
import com.kerhomjarnoin.pokemon.provider.contract.ArenesContract;
import com.kerhomjarnoin.pokemon.provider.contract.PositionsContract;
import com.kerhomjarnoin.pokemon.provider.contract.BadgesContract;
import com.kerhomjarnoin.pokemon.entity.Arenes;
import com.kerhomjarnoin.pokemon.entity.Positions;
import com.kerhomjarnoin.pokemon.entity.Badges;


import com.kerhomjarnoin.pokemon.PokemonApplication;



/** Arenes adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit ArenesAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class ArenesSQLiteAdapterBase
                        extends SQLiteAdapter<Arenes> {

    /** TAG for debug purpose. */
    protected static final String TAG = "ArenesDBAdapter";


    /**
     * Get the table name used in DB for your Arenes entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return ArenesContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your Arenes entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = ArenesContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the Arenes entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return ArenesContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + ArenesContract.TABLE_NAME    + " ("
        
         + ArenesContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + ArenesContract.COL_NOM    + " VARCHAR NOT NULL,"
         + ArenesContract.COL_POSITION_ID    + " INTEGER NOT NULL,"
         + ArenesContract.COL_BADGE_ID    + " INTEGER NOT NULL,"

        
         + "FOREIGN KEY(" + ArenesContract.COL_POSITION_ID + ") REFERENCES " 
             + PositionsContract.TABLE_NAME 
                + " (" + PositionsContract.COL_ID + "),"
         + "FOREIGN KEY(" + ArenesContract.COL_BADGE_ID + ") REFERENCES " 
             + BadgesContract.TABLE_NAME 
                + " (" + BadgesContract.COL_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public ArenesSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert Arenes entity to Content Values for database.
     * @param item Arenes entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final Arenes item) {
        return ArenesContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to Arenes entity.
     * @param cursor android.database.Cursor object
     * @return Arenes entity
     */
    public Arenes cursorToItem(final android.database.Cursor cursor) {
        return ArenesContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to Arenes entity.
     * @param cursor android.database.Cursor object
     * @param result Arenes entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final Arenes result) {
        ArenesContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read Arenes by id in database.
     *
     * @param id Identify of Arenes
     * @return Arenes entity
     */
    public Arenes getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final Arenes result = this.cursorToItem(cursor);
        cursor.close();

        if (result.getPosition() != null) {
            final PositionsSQLiteAdapter positionAdapter =
                    new PositionsSQLiteAdapter(this.ctx);
            positionAdapter.open(this.mDatabase);

            result.setPosition(positionAdapter.getByID(
                            result.getPosition().getId()));
        }
        if (result.getBadge() != null) {
            final BadgesSQLiteAdapter badgeAdapter =
                    new BadgesSQLiteAdapter(this.ctx);
            badgeAdapter.open(this.mDatabase);

            result.setBadge(badgeAdapter.getByID(
                            result.getBadge().getId()));
        }
        return result;
    }

    /**
     * Find & read Arenes by position.
     * @param positionId positionId
     * @param orderBy Order by string (can be null)
     * @return List of Arenes entities
     */
     public android.database.Cursor getByPosition(final int positionId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = ArenesContract.COL_POSITION_ID + "= ?";
        String idSelectionArgs = String.valueOf(positionId);
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
     * Find & read Arenes by badge.
     * @param badgeId badgeId
     * @param orderBy Order by string (can be null)
     * @return List of Arenes entities
     */
     public android.database.Cursor getByBadge(final int badgeId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = ArenesContract.COL_BADGE_ID + "= ?";
        String idSelectionArgs = String.valueOf(badgeId);
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
     * Read All Areness entities.
     *
     * @return List of Arenes entities
     */
    public ArrayList<Arenes> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<Arenes> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a Arenes entity into database.
     *
     * @param item The Arenes entity to persist
     * @return Id of the Arenes entity
     */
    public long insert(final Arenes item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + ArenesContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                ArenesContract.itemToContentValues(item);
        values.remove(ArenesContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    ArenesContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a Arenes entity into database whether.
     * it already exists or not.
     *
     * @param item The Arenes entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final Arenes item) {
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
     * Update a Arenes entity into database.
     *
     * @param item The Arenes entity to persist
     * @return count of updated entities
     */
    public int update(final Arenes item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + ArenesContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                ArenesContract.itemToContentValues(item);
        final String whereClause =
                 ArenesContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a Arenes entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + ArenesContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                ArenesContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param arenes The entity to delete
     * @return count of updated entities
     */
    public int delete(final Arenes arenes) {
        return this.remove(arenes.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the Arenes corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                ArenesContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                ArenesContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a Arenes entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = ArenesContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                ArenesContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

