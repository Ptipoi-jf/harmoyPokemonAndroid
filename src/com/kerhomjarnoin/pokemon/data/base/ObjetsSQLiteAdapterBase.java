
/**************************************************************************
 * ObjetsSQLiteAdapterBase.java, pokemon Android
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
import com.kerhomjarnoin.pokemon.data.ObjetsSQLiteAdapter;
import com.kerhomjarnoin.pokemon.data.TypeObjetSQLiteAdapter;
import com.kerhomjarnoin.pokemon.provider.contract.ObjetsContract;
import com.kerhomjarnoin.pokemon.provider.contract.NpcContract;
import com.kerhomjarnoin.pokemon.provider.contract.TypeObjetContract;
import com.kerhomjarnoin.pokemon.entity.Objets;
import com.kerhomjarnoin.pokemon.entity.TypeObjet;


import com.kerhomjarnoin.pokemon.PokemonApplication;



/** Objets adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit ObjetsAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class ObjetsSQLiteAdapterBase
                        extends SQLiteAdapter<Objets> {

    /** TAG for debug purpose. */
    protected static final String TAG = "ObjetsDBAdapter";


    /**
     * Get the table name used in DB for your Objets entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return ObjetsContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your Objets entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = ObjetsContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the Objets entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return ObjetsContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + ObjetsContract.TABLE_NAME    + " ("
        
         + ObjetsContract.COL_NPCOBJETSINTERNAL_ID    + " INTEGER,"
         + ObjetsContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + ObjetsContract.COL_NOM    + " VARCHAR NOT NULL,"
         + ObjetsContract.COL_QUANTITE    + " INTEGER NOT NULL,"
         + ObjetsContract.COL_TYPE_ID    + " INTEGER NOT NULL,"

        
         + "FOREIGN KEY(" + ObjetsContract.COL_NPCOBJETSINTERNAL_ID + ") REFERENCES " 
             + NpcContract.TABLE_NAME 
                + " (" + NpcContract.COL_ID + "),"
         + "FOREIGN KEY(" + ObjetsContract.COL_TYPE_ID + ") REFERENCES " 
             + TypeObjetContract.TABLE_NAME 
                + " (" + TypeObjetContract.COL_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public ObjetsSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert Objets entity to Content Values for database.
     * @param item Objets entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final Objets item) {
        return ObjetsContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to Objets entity.
     * @param cursor android.database.Cursor object
     * @return Objets entity
     */
    public Objets cursorToItem(final android.database.Cursor cursor) {
        return ObjetsContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to Objets entity.
     * @param cursor android.database.Cursor object
     * @param result Objets entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final Objets result) {
        ObjetsContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read Objets by id in database.
     *
     * @param id Identify of Objets
     * @return Objets entity
     */
    public Objets getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final Objets result = this.cursorToItem(cursor);
        cursor.close();

        if (result.getType() != null) {
            final TypeObjetSQLiteAdapter typeAdapter =
                    new TypeObjetSQLiteAdapter(this.ctx);
            typeAdapter.open(this.mDatabase);

            result.setType(typeAdapter.getByID(
                            result.getType().getId()));
        }
        return result;
    }

    /**
     * Find & read Objets by NpcobjetsInternal.
     * @param npcobjetsinternalId npcobjetsinternalId
     * @param orderBy Order by string (can be null)
     * @return List of Objets entities
     */
     public android.database.Cursor getByNpcobjetsInternal(final int npcobjetsinternalId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = ObjetsContract.COL_NPCOBJETSINTERNAL_ID + "= ?";
        String idSelectionArgs = String.valueOf(npcobjetsinternalId);
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
     * Find & read Objets by type.
     * @param typeId typeId
     * @param orderBy Order by string (can be null)
     * @return List of Objets entities
     */
     public android.database.Cursor getByType(final int typeId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = ObjetsContract.COL_TYPE_ID + "= ?";
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
     * Read All Objetss entities.
     *
     * @return List of Objets entities
     */
    public ArrayList<Objets> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<Objets> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a Objets entity into database.
     *
     * @param item The Objets entity to persist
     * @return Id of the Objets entity
     */
    public long insert(final Objets item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + ObjetsContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                ObjetsContract.itemToContentValues(item, 0);
        values.remove(ObjetsContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    ObjetsContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a Objets entity into database whether.
     * it already exists or not.
     *
     * @param item The Objets entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final Objets item) {
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
     * Update a Objets entity into database.
     *
     * @param item The Objets entity to persist
     * @return count of updated entities
     */
    public int update(final Objets item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + ObjetsContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                ObjetsContract.itemToContentValues(item, 0);
        final String whereClause =
                 ObjetsContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Update a Objets entity into database.
     *
     * @param item The Objets entity to persist
     * @param npcId The npc id
     * @return count of updated entities
     */
    public int updateWithNpcObjets(
                    Objets item,
                    int npcobjetsInternalId) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + ObjetsContract.TABLE_NAME + ")");
        }

        ContentValues values =
                ObjetsContract.itemToContentValues(item);
        values.put(
                ObjetsContract.COL_NPCOBJETSINTERNAL_ID,
                npcobjetsInternalId);
        String whereClause =
                 ObjetsContract.COL_ID
                 + "=?";
        String[] whereArgs =
                new String[] {String.valueOf(item.getId())};

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Either insert or update a Objets entity into database whether.
     * it already exists or not.
     *
     * @param item The Objets entity to persist
     * @param npcId The npc id
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdateWithNpcObjets(
            Objets item, int npcId) {
        int result = 0;
        if (this.getByID(item.getId()) != null) {
            // Item already exists => update it
            result = this.updateWithNpcObjets(item,
                    npcId);
        } else {
            // Item doesn't exist => create it
            long id = this.insertWithNpcObjets(item,
                    npcId);
            if (id != 0) {
                result = 1;
            }
        }

        return result;
    }


    /**
     * Insert a Objets entity into database.
     *
     * @param item The Objets entity to persist
     * @param npcId The npc id
     * @return Id of the Objets entity
     */
    public long insertWithNpcObjets(
            Objets item, int npcId) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + ObjetsContract.TABLE_NAME + ")");
        }

        ContentValues values = ObjetsContract.itemToContentValues(item,
                npcId);
        values.remove(ObjetsContract.COL_ID);
        int newid = (int) this.insert(
            null,
            values);


        return newid;
    }


    /**
     * Delete a Objets entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + ObjetsContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                ObjetsContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param objets The entity to delete
     * @return count of updated entities
     */
    public int delete(final Objets objets) {
        return this.remove(objets.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the Objets corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                ObjetsContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                ObjetsContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a Objets entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = ObjetsContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                ObjetsContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

