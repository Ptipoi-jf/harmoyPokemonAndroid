
/**************************************************************************
 * TypesSQLiteAdapterBase.java, pokemon Android
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
import com.kerhomjarnoin.pokemon.data.TypesSQLiteAdapter;
import com.kerhomjarnoin.pokemon.provider.contract.TypesContract;
import com.kerhomjarnoin.pokemon.provider.contract.TypeDePokemonsContract;
import com.kerhomjarnoin.pokemon.entity.Types;


import com.kerhomjarnoin.pokemon.PokemonApplication;



/** Types adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit TypesAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class TypesSQLiteAdapterBase
                        extends SQLiteAdapter<Types> {

    /** TAG for debug purpose. */
    protected static final String TAG = "TypesDBAdapter";


    /**
     * Get the table name used in DB for your Types entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return TypesContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your Types entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = TypesContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the Types entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return TypesContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + TypesContract.TABLE_NAME    + " ("
        
         + TypesContract.COL_TYPEDEPOKEMONSTYPESINTERNAL_ID    + " INTEGER,"
         + TypesContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + TypesContract.COL_NOM    + " VARCHAR NOT NULL,"
         + TypesContract.COL_TYPESFAIBLECONTREINTERNAL_ID    + " INTEGER,"
         + TypesContract.COL_TYPESFORTCONTREINTERNAL_ID    + " INTEGER,"

        
         + "FOREIGN KEY(" + TypesContract.COL_TYPEDEPOKEMONSTYPESINTERNAL_ID + ") REFERENCES " 
             + TypeDePokemonsContract.TABLE_NAME 
                + " (" + TypeDePokemonsContract.COL_ID + "),"
         + "FOREIGN KEY(" + TypesContract.COL_TYPESFAIBLECONTREINTERNAL_ID + ") REFERENCES " 
             + TypesContract.TABLE_NAME 
                + " (" + TypesContract.COL_ID + "),"
         + "FOREIGN KEY(" + TypesContract.COL_TYPESFORTCONTREINTERNAL_ID + ") REFERENCES " 
             + TypesContract.TABLE_NAME 
                + " (" + TypesContract.COL_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public TypesSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert Types entity to Content Values for database.
     * @param item Types entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final Types item) {
        return TypesContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to Types entity.
     * @param cursor android.database.Cursor object
     * @return Types entity
     */
    public Types cursorToItem(final android.database.Cursor cursor) {
        return TypesContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to Types entity.
     * @param cursor android.database.Cursor object
     * @param result Types entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final Types result) {
        TypesContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read Types by id in database.
     *
     * @param id Identify of Types
     * @return Types entity
     */
    public Types getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final Types result = this.cursorToItem(cursor);
        cursor.close();

        final TypesSQLiteAdapter faibleContreAdapter =
                new TypesSQLiteAdapter(this.ctx);
        faibleContreAdapter.open(this.mDatabase);
        android.database.Cursor faiblecontreCursor = faibleContreAdapter
                    .getByTypesfaibleContreInternal(
                            result.getId(),
                            TypesContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setFaibleContre(faibleContreAdapter.cursorToItems(faiblecontreCursor));

        faiblecontreCursor.close();
        final TypesSQLiteAdapter fortContreAdapter =
                new TypesSQLiteAdapter(this.ctx);
        fortContreAdapter.open(this.mDatabase);
        android.database.Cursor fortcontreCursor = fortContreAdapter
                    .getByTypesfortContreInternal(
                            result.getId(),
                            TypesContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setFortContre(fortContreAdapter.cursorToItems(fortcontreCursor));

        fortcontreCursor.close();
        return result;
    }

    /**
     * Find & read Types by TypeDePokemonstypesInternal.
     * @param typedepokemonstypesinternalId typedepokemonstypesinternalId
     * @param orderBy Order by string (can be null)
     * @return List of Types entities
     */
     public android.database.Cursor getByTypeDePokemonstypesInternal(final int typedepokemonstypesinternalId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = TypesContract.COL_TYPEDEPOKEMONSTYPESINTERNAL_ID + "= ?";
        String idSelectionArgs = String.valueOf(typedepokemonstypesinternalId);
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
     * Find & read Types by TypesfaibleContreInternal.
     * @param typesfaiblecontreinternalId typesfaiblecontreinternalId
     * @param orderBy Order by string (can be null)
     * @return List of Types entities
     */
     public android.database.Cursor getByTypesfaibleContreInternal(final int typesfaiblecontreinternalId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = TypesContract.COL_TYPESFAIBLECONTREINTERNAL_ID + "= ?";
        String idSelectionArgs = String.valueOf(typesfaiblecontreinternalId);
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
     * Find & read Types by TypesfortContreInternal.
     * @param typesfortcontreinternalId typesfortcontreinternalId
     * @param orderBy Order by string (can be null)
     * @return List of Types entities
     */
     public android.database.Cursor getByTypesfortContreInternal(final int typesfortcontreinternalId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = TypesContract.COL_TYPESFORTCONTREINTERNAL_ID + "= ?";
        String idSelectionArgs = String.valueOf(typesfortcontreinternalId);
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
     * Read All Typess entities.
     *
     * @return List of Types entities
     */
    public ArrayList<Types> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<Types> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a Types entity into database.
     *
     * @param item The Types entity to persist
     * @return Id of the Types entity
     */
    public long insert(final Types item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + TypesContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                TypesContract.itemToContentValues(item, 0, 0, 0);
        values.remove(TypesContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    TypesContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        if (item.getFaibleContre() != null) {
            TypesSQLiteAdapterBase faibleContreAdapter =
                    new TypesSQLiteAdapter(this.ctx);
            faibleContreAdapter.open(this.mDatabase);
            for (Types types
                        : item.getFaibleContre()) {
                faibleContreAdapter.insertOrUpdateWithTypesFaibleContre(
                                    types,
                                    insertResult);
            }
        }
        if (item.getFortContre() != null) {
            TypesSQLiteAdapterBase fortContreAdapter =
                    new TypesSQLiteAdapter(this.ctx);
            fortContreAdapter.open(this.mDatabase);
            for (Types types
                        : item.getFortContre()) {
                fortContreAdapter.insertOrUpdateWithTypesFortContre(
                                    types,
                                    insertResult);
            }
        }
        return insertResult;
    }

    /**
     * Either insert or update a Types entity into database whether.
     * it already exists or not.
     *
     * @param item The Types entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final Types item) {
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
     * Update a Types entity into database.
     *
     * @param item The Types entity to persist
     * @return count of updated entities
     */
    public int update(final Types item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + TypesContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                TypesContract.itemToContentValues(item, 0, 0, 0);
        final String whereClause =
                 TypesContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Update a Types entity into database.
     *
     * @param item The Types entity to persist
     * @param typedepokemonsId The typedepokemons id
     * @return count of updated entities
     */
    public int updateWithTypeDePokemonsTypes(
                    Types item,
                    int typeDePokemonstypesInternalId) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + TypesContract.TABLE_NAME + ")");
        }

        ContentValues values =
                TypesContract.itemToContentValues(item);
        values.put(
                TypesContract.COL_TYPEDEPOKEMONSTYPESINTERNAL_ID,
                typeDePokemonstypesInternalId);
        String whereClause =
                 TypesContract.COL_ID
                 + "=?";
        String[] whereArgs =
                new String[] {String.valueOf(item.getId())};

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Either insert or update a Types entity into database whether.
     * it already exists or not.
     *
     * @param item The Types entity to persist
     * @param typedepokemonsId The typedepokemons id
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdateWithTypeDePokemonsTypes(
            Types item, int typedepokemonsId) {
        int result = 0;
        if (this.getByID(item.getId()) != null) {
            // Item already exists => update it
            result = this.updateWithTypeDePokemonsTypes(item,
                    typedepokemonsId);
        } else {
            // Item doesn't exist => create it
            long id = this.insertWithTypeDePokemonsTypes(item,
                    typedepokemonsId);
            if (id != 0) {
                result = 1;
            }
        }

        return result;
    }


    /**
     * Insert a Types entity into database.
     *
     * @param item The Types entity to persist
     * @param typedepokemonsId The typedepokemons id
     * @return Id of the Types entity
     */
    public long insertWithTypeDePokemonsTypes(
            Types item, int typedepokemonsId) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + TypesContract.TABLE_NAME + ")");
        }

        ContentValues values = TypesContract.itemToContentValues(item,
                typedepokemonsId,
                0,
                0);
        values.remove(TypesContract.COL_ID);
        int newid = (int) this.insert(
            null,
            values);

        TypesSQLiteAdapter faibleContreAdapter =
                new TypesSQLiteAdapter(this.ctx);
        faibleContreAdapter.open(this.mDatabase);
        if (item.getFaibleContre() != null) {
            for (Types types : item.getFaibleContre()) {
                faibleContreAdapter.updateWithTypesFaibleContre(
                        types, newid);
            }
        }
        TypesSQLiteAdapter fortContreAdapter =
                new TypesSQLiteAdapter(this.ctx);
        fortContreAdapter.open(this.mDatabase);
        if (item.getFortContre() != null) {
            for (Types types : item.getFortContre()) {
                fortContreAdapter.updateWithTypesFortContre(
                        types, newid);
            }
        }

        return newid;
    }


    /**
     * Update a Types entity into database.
     *
     * @param item The Types entity to persist
     * @param typesId The types id
     * @return count of updated entities
     */
    public int updateWithTypesFaibleContre(
                    Types item,
                    int typesfaibleContreInternalId) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + TypesContract.TABLE_NAME + ")");
        }

        ContentValues values =
                TypesContract.itemToContentValues(item);
        values.put(
                TypesContract.COL_TYPESFAIBLECONTREINTERNAL_ID,
                typesfaibleContreInternalId);
        String whereClause =
                 TypesContract.COL_ID
                 + "=?";
        String[] whereArgs =
                new String[] {String.valueOf(item.getId())};

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Either insert or update a Types entity into database whether.
     * it already exists or not.
     *
     * @param item The Types entity to persist
     * @param typesId The types id
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdateWithTypesFaibleContre(
            Types item, int typesId) {
        int result = 0;
        if (this.getByID(item.getId()) != null) {
            // Item already exists => update it
            result = this.updateWithTypesFaibleContre(item,
                    typesId);
        } else {
            // Item doesn't exist => create it
            long id = this.insertWithTypesFaibleContre(item,
                    typesId);
            if (id != 0) {
                result = 1;
            }
        }

        return result;
    }


    /**
     * Insert a Types entity into database.
     *
     * @param item The Types entity to persist
     * @param typesId The types id
     * @return Id of the Types entity
     */
    public long insertWithTypesFaibleContre(
            Types item, int typesId) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + TypesContract.TABLE_NAME + ")");
        }

        ContentValues values = TypesContract.itemToContentValues(item,
                0,
                typesId,
                0);
        values.remove(TypesContract.COL_ID);
        int newid = (int) this.insert(
            null,
            values);

        TypesSQLiteAdapter faibleContreAdapter =
                new TypesSQLiteAdapter(this.ctx);
        faibleContreAdapter.open(this.mDatabase);
        if (item.getFaibleContre() != null) {
            for (Types types : item.getFaibleContre()) {
                faibleContreAdapter.updateWithTypesFaibleContre(
                        types, newid);
            }
        }
        TypesSQLiteAdapter fortContreAdapter =
                new TypesSQLiteAdapter(this.ctx);
        fortContreAdapter.open(this.mDatabase);
        if (item.getFortContre() != null) {
            for (Types types : item.getFortContre()) {
                fortContreAdapter.updateWithTypesFortContre(
                        types, newid);
            }
        }

        return newid;
    }


    /**
     * Update a Types entity into database.
     *
     * @param item The Types entity to persist
     * @param typesId The types id
     * @return count of updated entities
     */
    public int updateWithTypesFortContre(
                    Types item,
                    int typesfortContreInternalId) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + TypesContract.TABLE_NAME + ")");
        }

        ContentValues values =
                TypesContract.itemToContentValues(item);
        values.put(
                TypesContract.COL_TYPESFORTCONTREINTERNAL_ID,
                typesfortContreInternalId);
        String whereClause =
                 TypesContract.COL_ID
                 + "=?";
        String[] whereArgs =
                new String[] {String.valueOf(item.getId())};

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Either insert or update a Types entity into database whether.
     * it already exists or not.
     *
     * @param item The Types entity to persist
     * @param typesId The types id
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdateWithTypesFortContre(
            Types item, int typesId) {
        int result = 0;
        if (this.getByID(item.getId()) != null) {
            // Item already exists => update it
            result = this.updateWithTypesFortContre(item,
                    typesId);
        } else {
            // Item doesn't exist => create it
            long id = this.insertWithTypesFortContre(item,
                    typesId);
            if (id != 0) {
                result = 1;
            }
        }

        return result;
    }


    /**
     * Insert a Types entity into database.
     *
     * @param item The Types entity to persist
     * @param typesId The types id
     * @return Id of the Types entity
     */
    public long insertWithTypesFortContre(
            Types item, int typesId) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + TypesContract.TABLE_NAME + ")");
        }

        ContentValues values = TypesContract.itemToContentValues(item,
                0,
                0,
                typesId);
        values.remove(TypesContract.COL_ID);
        int newid = (int) this.insert(
            null,
            values);

        TypesSQLiteAdapter faibleContreAdapter =
                new TypesSQLiteAdapter(this.ctx);
        faibleContreAdapter.open(this.mDatabase);
        if (item.getFaibleContre() != null) {
            for (Types types : item.getFaibleContre()) {
                faibleContreAdapter.updateWithTypesFaibleContre(
                        types, newid);
            }
        }
        TypesSQLiteAdapter fortContreAdapter =
                new TypesSQLiteAdapter(this.ctx);
        fortContreAdapter.open(this.mDatabase);
        if (item.getFortContre() != null) {
            for (Types types : item.getFortContre()) {
                fortContreAdapter.updateWithTypesFortContre(
                        types, newid);
            }
        }

        return newid;
    }


    /**
     * Delete a Types entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + TypesContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                TypesContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param types The entity to delete
     * @return count of updated entities
     */
    public int delete(final Types types) {
        return this.remove(types.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the Types corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                TypesContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                TypesContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a Types entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = TypesContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                TypesContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

