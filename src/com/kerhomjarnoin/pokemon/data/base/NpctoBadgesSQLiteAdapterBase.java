
/**************************************************************************
 * NpctoBadgesSQLiteAdapterBase.java, pokemon Android
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
import com.kerhomjarnoin.pokemon.data.NpctoBadgesSQLiteAdapter;
import com.kerhomjarnoin.pokemon.data.NpcSQLiteAdapter;
import com.kerhomjarnoin.pokemon.data.BadgesSQLiteAdapter;
import com.kerhomjarnoin.pokemon.provider.contract.NpctoBadgesContract;
import com.kerhomjarnoin.pokemon.provider.contract.NpcContract;
import com.kerhomjarnoin.pokemon.provider.contract.BadgesContract;
import com.kerhomjarnoin.pokemon.entity.Npc;
import com.kerhomjarnoin.pokemon.entity.Badges;


import com.kerhomjarnoin.pokemon.PokemonApplication;

import com.kerhomjarnoin.pokemon.criterias.base.CriteriaExpression;
import com.kerhomjarnoin.pokemon.criterias.base.Criterion;
import com.kerhomjarnoin.pokemon.criterias.base.Criterion.Type;
import com.kerhomjarnoin.pokemon.criterias.base.CriteriaExpression.GroupType;
import com.kerhomjarnoin.pokemon.criterias.base.value.SelectValue;


/** NpctoBadges adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit NpctoBadgesAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class NpctoBadgesSQLiteAdapterBase
                        extends SQLiteAdapter<Void> {

    /** TAG for debug purpose. */
    protected static final String TAG = "NpctoBadgesDBAdapter";


    /**
     * Get the table name used in DB for your NpctoBadges entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return NpctoBadgesContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your NpctoBadges entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = NpctoBadgesContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the NpctoBadges entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return NpctoBadgesContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + NpctoBadgesContract.TABLE_NAME    + " ("
        
         + NpctoBadgesContract.COL_NPCINTERNALID_ID    + " INTEGER NOT NULL,"
         + NpctoBadgesContract.COL_BADGES_ID    + " INTEGER NOT NULL,"

        
         + "FOREIGN KEY(" + NpctoBadgesContract.COL_NPCINTERNALID_ID + ") REFERENCES " 
             + NpcContract.TABLE_NAME 
                + " (" + NpcContract.COL_ID + "),"
         + "FOREIGN KEY(" + NpctoBadgesContract.COL_BADGES_ID + ") REFERENCES " 
             + BadgesContract.TABLE_NAME 
                + " (" + BadgesContract.COL_ID + ")"
        + ", PRIMARY KEY (" + NpctoBadgesContract.COL_NPCINTERNALID_ID + "," + NpctoBadgesContract.COL_BADGES_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public NpctoBadgesSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }


    /**
     * Query the DB to find a NpctoBadges entity.
     *
     * @param NpcInternalId The NpcInternalId of the entity to get from the DB
     * @param badges The badges of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final Npc NpcInternalId,
                final Badges badges) {

        String selection = NpctoBadgesContract.ALIASED_COL_NPCINTERNALID_ID + " = ?";
        selection += " AND ";
        selection += NpctoBadgesContract.ALIASED_COL_BADGES_ID + " = ?";
        

        String[] selectionArgs = new String[2];
        selectionArgs[0] = String.valueOf(NpcInternalId.getId());
        selectionArgs[1] = String.valueOf(badges.getId());

        return this.query(
                NpctoBadgesContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }



    /**
     * Insert a NpctoBadges entity into database.
     *
     * @param npcinternalid npcinternalid
     * @param badges badges
     * @return Id of the NpctoBadges entity
     */
    public long insert(final int npcInternalIdId,
            final int badgesId) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + NpctoBadgesContract.TABLE_NAME + ")");
        }

        ContentValues values = new ContentValues();
        values.put(NpctoBadgesContract.COL_NPCINTERNALID_ID,
                npcInternalIdId);
        values.put(NpctoBadgesContract.COL_BADGES_ID,
                badgesId);

        return this.mDatabase.insert(
                NpctoBadgesContract.TABLE_NAME,
                null,
                values);
    }


    /**
     * Find & read NpctoBadges by NpcInternalId.
     * @param npcinternalid badges
     * @param orderBy Order by string (can be null)
     * @return ArrayList of Badges matching npcinternalid
     */
    public android.database.Cursor getByNpcInternalId(
            final int npcInternalIdId,
            final String[] projection,
            String selection,
            String[] selectionArgs,
            final String orderBy) {

        android.database.Cursor ret = null;
        CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
        crit.add(NpctoBadgesContract.COL_NPCINTERNALID_ID,
                String.valueOf(npcInternalIdId),
                Type.EQUALS);
        SelectValue value = new SelectValue();
        value.setRefKey(NpctoBadgesContract.COL_BADGES_ID);
        value.setRefTable(NpctoBadgesContract.TABLE_NAME);
        value.setCriteria(crit);
        CriteriaExpression badgesCrit = new CriteriaExpression(GroupType.AND);
        Criterion badgesSelectCrit = new Criterion();
        badgesSelectCrit.setKey(BadgesContract.ALIASED_COL_ID);
        badgesSelectCrit.setType(Type.IN);
        badgesSelectCrit.addValue(value);
        badgesCrit.add(badgesSelectCrit);

        if (Strings.isNullOrEmpty(selection)) {
            selection = badgesCrit.toSQLiteSelection();
            selectionArgs = badgesCrit.toSQLiteSelectionArgs();
        } else {
            selection += " AND " + badgesCrit.toSQLiteSelection();
            selectionArgs = ObjectArrays.concat(
                        badgesCrit.toSQLiteSelectionArgs(),
                        selectionArgs,
                        String.class);
        }

        ret = this.mDatabase.query(BadgesContract.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);
        return ret;
    }

    /**
     * Find & read NpctoBadges by badges.
     * @param badges npcinternalid
     * @param orderBy Order by string (can be null)
     * @return ArrayList of Npc matching badges
     */
    public android.database.Cursor getByBadges(
            final int badgesId,
            final String[] projection,
            String selection,
            String[] selectionArgs,
            final String orderBy) {

        android.database.Cursor ret = null;
        CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
        crit.add(NpctoBadgesContract.COL_BADGES_ID,
                String.valueOf(badgesId),
                Type.EQUALS);
        SelectValue value = new SelectValue();
        value.setRefKey(NpctoBadgesContract.COL_NPCINTERNALID_ID);
        value.setRefTable(NpctoBadgesContract.TABLE_NAME);
        value.setCriteria(crit);
        CriteriaExpression npcCrit = new CriteriaExpression(GroupType.AND);
        Criterion npcSelectCrit = new Criterion();
        npcSelectCrit.setKey(NpcContract.ALIASED_COL_ID);
        npcSelectCrit.setType(Type.IN);
        npcSelectCrit.addValue(value);
        npcCrit.add(npcSelectCrit);

        if (Strings.isNullOrEmpty(selection)) {
            selection = npcCrit.toSQLiteSelection();
            selectionArgs = npcCrit.toSQLiteSelectionArgs();
        } else {
            selection += " AND " + npcCrit.toSQLiteSelection();
            selectionArgs = ObjectArrays.concat(
                        npcCrit.toSQLiteSelectionArgs(),
                        selectionArgs,
                        String.class);
        }

        ret = this.mDatabase.query(NpcContract.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);
        return ret;
    }


    @Override
    public Void cursorToItem(android.database.Cursor c) {
        return null;
    }

    @Override
    public ContentValues itemToContentValues(Void item) {
        return null;
    }

    @Override
    public long insert(Void item) {
        return -1;
    }

    @Override
    public int update(Void item) {
        return 0;
    }

    @Override
    public int delete(Void item) {
        return 0;
    }

}

