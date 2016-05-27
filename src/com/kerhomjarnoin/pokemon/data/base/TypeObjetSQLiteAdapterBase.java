
/**************************************************************************
 * TypeObjetSQLiteAdapterBase.java, pokemon Android
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
import com.kerhomjarnoin.pokemon.data.TypeObjetSQLiteAdapter;
import com.kerhomjarnoin.pokemon.provider.contract.TypeObjetContract;
import com.kerhomjarnoin.pokemon.entity.TypeObjet;


import com.kerhomjarnoin.pokemon.PokemonApplication;



/** TypeObjet adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit TypeObjetAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class TypeObjetSQLiteAdapterBase
                        extends SQLiteAdapter<TypeObjet> {

    /** TAG for debug purpose. */
    protected static final String TAG = "TypeObjetDBAdapter";


    /**
     * Get the table name used in DB for your TypeObjet entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return TypeObjetContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your TypeObjet entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = TypeObjetContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the TypeObjet entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return TypeObjetContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + TypeObjetContract.TABLE_NAME    + " ("
        
         + TypeObjetContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + TypeObjetContract.COL_NOM    + " VARCHAR NOT NULL"

        
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public TypeObjetSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert TypeObjet entity to Content Values for database.
     * @param item TypeObjet entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final TypeObjet item) {
        return TypeObjetContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to TypeObjet entity.
     * @param cursor android.database.Cursor object
     * @return TypeObjet entity
     */
    public TypeObjet cursorToItem(final android.database.Cursor cursor) {
        return TypeObjetContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to TypeObjet entity.
     * @param cursor android.database.Cursor object
     * @param result TypeObjet entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final TypeObjet result) {
        TypeObjetContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read TypeObjet by id in database.
     *
     * @param id Identify of TypeObjet
     * @return TypeObjet entity
     */
    public TypeObjet getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final TypeObjet result = this.cursorToItem(cursor);
        cursor.close();

        return result;
    }


    /**
     * Read All TypeObjets entities.
     *
     * @return List of TypeObjet entities
     */
    public ArrayList<TypeObjet> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<TypeObjet> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a TypeObjet entity into database.
     *
     * @param item The TypeObjet entity to persist
     * @return Id of the TypeObjet entity
     */
    public long insert(final TypeObjet item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + TypeObjetContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                TypeObjetContract.itemToContentValues(item);
        values.remove(TypeObjetContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    TypeObjetContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a TypeObjet entity into database whether.
     * it already exists or not.
     *
     * @param item The TypeObjet entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final TypeObjet item) {
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
     * Update a TypeObjet entity into database.
     *
     * @param item The TypeObjet entity to persist
     * @return count of updated entities
     */
    public int update(final TypeObjet item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + TypeObjetContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                TypeObjetContract.itemToContentValues(item);
        final String whereClause =
                 TypeObjetContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a TypeObjet entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + TypeObjetContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                TypeObjetContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param typeObjet The entity to delete
     * @return count of updated entities
     */
    public int delete(final TypeObjet typeObjet) {
        return this.remove(typeObjet.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the TypeObjet corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                TypeObjetContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                TypeObjetContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a TypeObjet entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = TypeObjetContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                TypeObjetContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

