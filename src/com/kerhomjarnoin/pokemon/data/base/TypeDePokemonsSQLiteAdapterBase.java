
/**************************************************************************
 * TypeDePokemonsSQLiteAdapterBase.java, pokemon Android
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
import com.kerhomjarnoin.pokemon.data.TypeDePokemonsSQLiteAdapter;
import com.kerhomjarnoin.pokemon.data.TypesSQLiteAdapter;
import com.kerhomjarnoin.pokemon.provider.contract.TypeDePokemonsContract;
import com.kerhomjarnoin.pokemon.provider.contract.TypesContract;
import com.kerhomjarnoin.pokemon.entity.TypeDePokemons;
import com.kerhomjarnoin.pokemon.entity.Types;


import com.kerhomjarnoin.pokemon.PokemonApplication;



/** TypeDePokemons adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit TypeDePokemonsAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class TypeDePokemonsSQLiteAdapterBase
                        extends SQLiteAdapter<TypeDePokemons> {

    /** TAG for debug purpose. */
    protected static final String TAG = "TypeDePokemonsDBAdapter";


    /**
     * Get the table name used in DB for your TypeDePokemons entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return TypeDePokemonsContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your TypeDePokemons entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = TypeDePokemonsContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the TypeDePokemons entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return TypeDePokemonsContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + TypeDePokemonsContract.TABLE_NAME    + " ("
        
         + TypeDePokemonsContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + TypeDePokemonsContract.COL_NOM    + " VARCHAR NOT NULL,"
         + TypeDePokemonsContract.COL_ATTAQUE    + " INTEGER NOT NULL,"
         + TypeDePokemonsContract.COL_ATTAQUE_SPE    + " INTEGER NOT NULL,"
         + TypeDePokemonsContract.COL_DEFENCE    + " INTEGER NOT NULL,"
         + TypeDePokemonsContract.COL_DEFENCE_SPE    + " INTEGER NOT NULL,"
         + TypeDePokemonsContract.COL_VITESSE    + " INTEGER NOT NULL,"
         + TypeDePokemonsContract.COL_PV    + " INTEGER NOT NULL"

        
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public TypeDePokemonsSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert TypeDePokemons entity to Content Values for database.
     * @param item TypeDePokemons entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final TypeDePokemons item) {
        return TypeDePokemonsContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to TypeDePokemons entity.
     * @param cursor android.database.Cursor object
     * @return TypeDePokemons entity
     */
    public TypeDePokemons cursorToItem(final android.database.Cursor cursor) {
        return TypeDePokemonsContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to TypeDePokemons entity.
     * @param cursor android.database.Cursor object
     * @param result TypeDePokemons entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final TypeDePokemons result) {
        TypeDePokemonsContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read TypeDePokemons by id in database.
     *
     * @param id Identify of TypeDePokemons
     * @return TypeDePokemons entity
     */
    public TypeDePokemons getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final TypeDePokemons result = this.cursorToItem(cursor);
        cursor.close();

        final TypesSQLiteAdapter typesAdapter =
                new TypesSQLiteAdapter(this.ctx);
        typesAdapter.open(this.mDatabase);
        android.database.Cursor typesCursor = typesAdapter
                    .getByTypeDePokemonstypesInternal(
                            result.getId(),
                            TypesContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setTypes(typesAdapter.cursorToItems(typesCursor));

        typesCursor.close();
        return result;
    }


    /**
     * Read All TypeDePokemonss entities.
     *
     * @return List of TypeDePokemons entities
     */
    public ArrayList<TypeDePokemons> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<TypeDePokemons> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a TypeDePokemons entity into database.
     *
     * @param item The TypeDePokemons entity to persist
     * @return Id of the TypeDePokemons entity
     */
    public long insert(final TypeDePokemons item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + TypeDePokemonsContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                TypeDePokemonsContract.itemToContentValues(item);
        values.remove(TypeDePokemonsContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    TypeDePokemonsContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        if (item.getTypes() != null) {
            TypesSQLiteAdapterBase typesAdapter =
                    new TypesSQLiteAdapter(this.ctx);
            typesAdapter.open(this.mDatabase);
            for (Types types
                        : item.getTypes()) {
                typesAdapter.insertOrUpdateWithTypeDePokemonsTypes(
                                    types,
                                    insertResult);
            }
        }
        return insertResult;
    }

    /**
     * Either insert or update a TypeDePokemons entity into database whether.
     * it already exists or not.
     *
     * @param item The TypeDePokemons entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final TypeDePokemons item) {
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
     * Update a TypeDePokemons entity into database.
     *
     * @param item The TypeDePokemons entity to persist
     * @return count of updated entities
     */
    public int update(final TypeDePokemons item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + TypeDePokemonsContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                TypeDePokemonsContract.itemToContentValues(item);
        final String whereClause =
                 TypeDePokemonsContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a TypeDePokemons entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + TypeDePokemonsContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                TypeDePokemonsContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param typeDePokemons The entity to delete
     * @return count of updated entities
     */
    public int delete(final TypeDePokemons typeDePokemons) {
        return this.remove(typeDePokemons.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the TypeDePokemons corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                TypeDePokemonsContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                TypeDePokemonsContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a TypeDePokemons entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = TypeDePokemonsContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                TypeDePokemonsContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

