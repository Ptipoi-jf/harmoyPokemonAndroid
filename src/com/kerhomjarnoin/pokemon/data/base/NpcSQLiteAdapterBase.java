
/**************************************************************************
 * NpcSQLiteAdapterBase.java, pokemon Android
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
import com.kerhomjarnoin.pokemon.data.NpcSQLiteAdapter;
import com.kerhomjarnoin.pokemon.data.ObjetsSQLiteAdapter;
import com.kerhomjarnoin.pokemon.data.PokemonsSQLiteAdapter;
import com.kerhomjarnoin.pokemon.data.NpctoBadgesSQLiteAdapter;
import com.kerhomjarnoin.pokemon.data.BadgesSQLiteAdapter;
import com.kerhomjarnoin.pokemon.data.PositionsSQLiteAdapter;
import com.kerhomjarnoin.pokemon.provider.contract.NpcContract;
import com.kerhomjarnoin.pokemon.provider.contract.ObjetsContract;
import com.kerhomjarnoin.pokemon.provider.contract.PokemonsContract;
import com.kerhomjarnoin.pokemon.provider.contract.NpctoBadgesContract;
import com.kerhomjarnoin.pokemon.provider.contract.BadgesContract;
import com.kerhomjarnoin.pokemon.provider.contract.PositionsContract;
import com.kerhomjarnoin.pokemon.entity.Npc;
import com.kerhomjarnoin.pokemon.entity.Objets;
import com.kerhomjarnoin.pokemon.entity.Pokemons;
import com.kerhomjarnoin.pokemon.entity.Badges;
import com.kerhomjarnoin.pokemon.entity.Positions;


import com.kerhomjarnoin.pokemon.PokemonApplication;



/** Npc adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit NpcAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class NpcSQLiteAdapterBase
                        extends SQLiteAdapter<Npc> {

    /** TAG for debug purpose. */
    protected static final String TAG = "NpcDBAdapter";


    /**
     * Get the table name used in DB for your Npc entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return NpcContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your Npc entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = NpcContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the Npc entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return NpcContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + NpcContract.TABLE_NAME    + " ("
        
         + NpcContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + NpcContract.COL_NOM    + " VARCHAR NOT NULL,"
         + NpcContract.COL_PROFESSION    + " VARCHAR NOT NULL,"
         + NpcContract.COL_TEXTE    + " VARCHAR NOT NULL,"
         + NpcContract.COL_POSITION_ID    + " INTEGER,"

        
         + "FOREIGN KEY(" + NpcContract.COL_POSITION_ID + ") REFERENCES " 
             + PositionsContract.TABLE_NAME 
                + " (" + PositionsContract.COL_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public NpcSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert Npc entity to Content Values for database.
     * @param item Npc entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final Npc item) {
        return NpcContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to Npc entity.
     * @param cursor android.database.Cursor object
     * @return Npc entity
     */
    public Npc cursorToItem(final android.database.Cursor cursor) {
        return NpcContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to Npc entity.
     * @param cursor android.database.Cursor object
     * @param result Npc entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final Npc result) {
        NpcContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read Npc by id in database.
     *
     * @param id Identify of Npc
     * @return Npc entity
     */
    public Npc getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final Npc result = this.cursorToItem(cursor);
        cursor.close();

        final ObjetsSQLiteAdapter objetsAdapter =
                new ObjetsSQLiteAdapter(this.ctx);
        objetsAdapter.open(this.mDatabase);
        android.database.Cursor objetsCursor = objetsAdapter
                    .getByNpcobjetsInternal(
                            result.getId(),
                            ObjetsContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setObjets(objetsAdapter.cursorToItems(objetsCursor));

        objetsCursor.close();
        final PokemonsSQLiteAdapter pokemonsAdapter =
                new PokemonsSQLiteAdapter(this.ctx);
        pokemonsAdapter.open(this.mDatabase);
        android.database.Cursor pokemonsCursor = pokemonsAdapter
                    .getByNpcpokemonsInternal(
                            result.getId(),
                            PokemonsContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setPokemons(pokemonsAdapter.cursorToItems(pokemonsCursor));

        pokemonsCursor.close();
        NpctoBadgesSQLiteAdapter npctobadgesAdapter =
                new NpctoBadgesSQLiteAdapter(this.ctx);
        npctobadgesAdapter.open(this.mDatabase);
        android.database.Cursor badgesCursor = npctobadgesAdapter.getByNpcInternalId(
                            result.getId(),
                            BadgesContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setBadges(new BadgesSQLiteAdapter(ctx).cursorToItems(badgesCursor));

        badgesCursor.close();
        if (result.getPosition() != null) {
            final PositionsSQLiteAdapter positionAdapter =
                    new PositionsSQLiteAdapter(this.ctx);
            positionAdapter.open(this.mDatabase);

            result.setPosition(positionAdapter.getByID(
                            result.getPosition().getId()));
        }
        return result;
    }

    /**
     * Find & read Npc by position.
     * @param positionId positionId
     * @param orderBy Order by string (can be null)
     * @return List of Npc entities
     */
     public android.database.Cursor getByPosition(final int positionId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = NpcContract.COL_POSITION_ID + "= ?";
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
     * Read All Npcs entities.
     *
     * @return List of Npc entities
     */
    public ArrayList<Npc> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<Npc> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a Npc entity into database.
     *
     * @param item The Npc entity to persist
     * @return Id of the Npc entity
     */
    public long insert(final Npc item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + NpcContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                NpcContract.itemToContentValues(item);
        values.remove(NpcContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    NpcContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        if (item.getObjets() != null) {
            ObjetsSQLiteAdapterBase objetsAdapter =
                    new ObjetsSQLiteAdapter(this.ctx);
            objetsAdapter.open(this.mDatabase);
            for (Objets objets
                        : item.getObjets()) {
                objetsAdapter.insertOrUpdateWithNpcObjets(
                                    objets,
                                    insertResult);
            }
        }
        if (item.getPokemons() != null) {
            PokemonsSQLiteAdapterBase pokemonsAdapter =
                    new PokemonsSQLiteAdapter(this.ctx);
            pokemonsAdapter.open(this.mDatabase);
            for (Pokemons pokemons
                        : item.getPokemons()) {
                pokemonsAdapter.insertOrUpdateWithNpcPokemons(
                                    pokemons,
                                    insertResult);
            }
        }
        if (item.getBadges() != null) {
            NpctoBadgesSQLiteAdapterBase badgesAdapter =
                    new NpctoBadgesSQLiteAdapter(this.ctx);
            badgesAdapter.open(this.mDatabase);
            for (Badges i : item.getBadges()) {
                badgesAdapter.insert(insertResult,
                        i.getId());
            }
        }
        return insertResult;
    }

    /**
     * Either insert or update a Npc entity into database whether.
     * it already exists or not.
     *
     * @param item The Npc entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final Npc item) {
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
     * Update a Npc entity into database.
     *
     * @param item The Npc entity to persist
     * @return count of updated entities
     */
    public int update(final Npc item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + NpcContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                NpcContract.itemToContentValues(item);
        final String whereClause =
                 NpcContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a Npc entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + NpcContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                NpcContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param npc The entity to delete
     * @return count of updated entities
     */
    public int delete(final Npc npc) {
        return this.remove(npc.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the Npc corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                NpcContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                NpcContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a Npc entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = NpcContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                NpcContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

