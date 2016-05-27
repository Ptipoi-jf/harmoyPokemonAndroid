/**************************************************************************
 * NpcProviderUtilsBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.provider.utils.base;

import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;

import android.content.ContentResolver;
import android.content.ContentValues;

import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;


import com.kerhomjarnoin.pokemon.provider.utils.ProviderUtils;
import com.kerhomjarnoin.pokemon.criterias.base.Criterion;
import com.kerhomjarnoin.pokemon.criterias.base.Criterion.Type;
import com.kerhomjarnoin.pokemon.criterias.base.value.ArrayValue;
import com.kerhomjarnoin.pokemon.criterias.base.CriteriaExpression;
import com.kerhomjarnoin.pokemon.criterias.base.CriteriaExpression.GroupType;
import com.kerhomjarnoin.pokemon.data.BadgesSQLiteAdapter;
import com.kerhomjarnoin.pokemon.entity.Npc;
import com.kerhomjarnoin.pokemon.entity.Objets;
import com.kerhomjarnoin.pokemon.entity.Pokemons;
import com.kerhomjarnoin.pokemon.entity.Badges;
import com.kerhomjarnoin.pokemon.entity.Positions;

import com.kerhomjarnoin.pokemon.provider.NpcProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.ObjetsProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.PokemonsProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.NpctoBadgesProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.BadgesProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.PositionsProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.PokemonProvider;
import com.kerhomjarnoin.pokemon.provider.contract.NpcContract;
import com.kerhomjarnoin.pokemon.provider.contract.ObjetsContract;
import com.kerhomjarnoin.pokemon.provider.contract.PokemonsContract;
import com.kerhomjarnoin.pokemon.provider.contract.NpctoBadgesContract;
import com.kerhomjarnoin.pokemon.provider.contract.BadgesContract;
import com.kerhomjarnoin.pokemon.provider.contract.PositionsContract;

/**
 * Npc Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class NpcProviderUtilsBase
            extends ProviderUtils<Npc> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "NpcProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public NpcProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final Npc item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = NpcContract.itemToContentValues(item);
        itemValues.remove(NpcContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                NpcProviderAdapter.NPC_URI)
                        .withValues(itemValues)
                        .build());

        if (item.getObjets() != null && item.getObjets().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(ObjetsContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getObjets().size(); i++) {
                inValue.addValue(String.valueOf(item.getObjets().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(ObjetsProviderAdapter.OBJETS_URI)
                    .withValueBackReference(
                            ObjetsContract
                                    .COL_NPCOBJETSINTERNAL_ID,
                            0)
                    .withSelection(
                            crit.toSQLiteSelection(),
                            crit.toSQLiteSelectionArgs())
                    .build());
        }
        if (item.getPokemons() != null && item.getPokemons().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(PokemonsContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getPokemons().size(); i++) {
                inValue.addValue(String.valueOf(item.getPokemons().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(PokemonsProviderAdapter.POKEMONS_URI)
                    .withValueBackReference(
                            PokemonsContract
                                    .COL_NPCPOKEMONSINTERNAL_ID,
                            0)
                    .withSelection(
                            crit.toSQLiteSelection(),
                            crit.toSQLiteSelectionArgs())
                    .build());
        }
        if (item.getBadges() != null && item.getBadges().size() > 0) {
            for (Badges badges : item.getBadges()) {
                ContentValues badgesValues = new ContentValues();
                badgesValues.put(
                        NpctoBadgesContract.COL_BADGES_ID,
                        badges.getId());

                operations.add(ContentProviderOperation.newInsert(
                    NpctoBadgesProviderAdapter.NPCTOBADGES_URI)
                        .withValues(badgesValues)
                        .withValueBackReference(
                                NpctoBadgesContract.COL_NPCINTERNALID_ID,
                                0)
                        .build());

            }
        }

        try {
            ContentProviderResult[] results =
                    prov.applyBatch(PokemonProvider.authority, operations);
            if (results[0] != null) {
                result = results[0].uri;
                item.setId(Integer.parseInt(result.getPathSegments().get(1)));
            }
        } catch (RemoteException e) {
            android.util.Log.e(TAG, e.getMessage());
        } catch (OperationApplicationException e) {
            android.util.Log.e(TAG, e.getMessage());
        }

        return result;
    }


    /**
     * Delete from DB.
     * @param item Npc
     * @return number of row affected
     */
    public int delete(final Npc item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = NpcProviderAdapter.NPC_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return Npc
     */
    public Npc query(final Npc item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return Npc
     */
    public Npc query(final int id) {
        Npc result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(NpcContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            NpcProviderAdapter.NPC_URI,
            NpcContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = NpcContract.cursorToItem(cursor);

            result.setObjets(
                this.getAssociateObjets(result));
            result.setPokemons(
                this.getAssociatePokemons(result));
            result.setBadges(
                this.getAssociateBadges(result));
            if (result.getPosition() != null) {
                result.setPosition(
                    this.getAssociatePosition(result));
            }
        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<Npc>
     */
    public ArrayList<Npc> queryAll() {
        ArrayList<Npc> result =
                    new ArrayList<Npc>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                NpcProviderAdapter.NPC_URI,
                NpcContract.ALIASED_COLS,
                null,
                null,
                null);

        result = NpcContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<Npc>
     */
    public ArrayList<Npc> query(CriteriaExpression expression) {
        ArrayList<Npc> result =
                    new ArrayList<Npc>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                NpcProviderAdapter.NPC_URI,
                NpcContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = NpcContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item Npc
     
     * @return number of rows updated
     */
    public int update(final Npc item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = NpcContract.itemToContentValues(
                item);

        Uri uri = NpcProviderAdapter.NPC_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());


        if (item.getObjets() != null && item.getObjets().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new objets for Npc
            CriteriaExpression objetsCrit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(ObjetsContract.COL_ID);
            crit.addValue(values);
            objetsCrit.add(crit);


            for (Objets objets : item.getObjets()) {
                values.addValue(
                    String.valueOf(objets.getId()));
            }
            selection = objetsCrit.toSQLiteSelection();
            selectionArgs = objetsCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    ObjetsProviderAdapter.OBJETS_URI)
                    .withValue(
                            ObjetsContract.COL_NPCOBJETSINTERNAL_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated objets
            crit.setType(Type.NOT_IN);
            objetsCrit.add(ObjetsContract.COL_NPCOBJETSINTERNAL_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    ObjetsProviderAdapter.OBJETS_URI)
                    .withValue(
                            ObjetsContract.COL_NPCOBJETSINTERNAL_ID,
                            null)
                    .withSelection(
                            objetsCrit.toSQLiteSelection(),
                            objetsCrit.toSQLiteSelectionArgs())
                    .build());
        }

        if (item.getPokemons() != null && item.getPokemons().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new pokemons for Npc
            CriteriaExpression pokemonsCrit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(PokemonsContract.COL_ID);
            crit.addValue(values);
            pokemonsCrit.add(crit);


            for (Pokemons pokemons : item.getPokemons()) {
                values.addValue(
                    String.valueOf(pokemons.getId()));
            }
            selection = pokemonsCrit.toSQLiteSelection();
            selectionArgs = pokemonsCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    PokemonsProviderAdapter.POKEMONS_URI)
                    .withValue(
                            PokemonsContract.COL_NPCPOKEMONSINTERNAL_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated pokemons
            crit.setType(Type.NOT_IN);
            pokemonsCrit.add(PokemonsContract.COL_NPCPOKEMONSINTERNAL_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    PokemonsProviderAdapter.POKEMONS_URI)
                    .withValue(
                            PokemonsContract.COL_NPCPOKEMONSINTERNAL_ID,
                            null)
                    .withSelection(
                            pokemonsCrit.toSQLiteSelection(),
                            pokemonsCrit.toSQLiteSelectionArgs())
                    .build());
        }

        operations.add(ContentProviderOperation.newDelete(NpctoBadgesProviderAdapter.NPCTOBADGES_URI)
                .withSelection(NpctoBadgesContract.COL_NPCINTERNALID_ID + "= ?",
                                new String[]{String.valueOf(item.getId())})
                .build());

        for (Badges badges : item.getBadges()) {
            ContentValues badgesValues = new ContentValues();
            badgesValues.put(NpctoBadgesContract.COL_BADGES_ID,
                    badges.getId());
            badgesValues.put(NpctoBadgesContract.COL_NPCINTERNALID_ID,
                    item.getId());

            operations.add(ContentProviderOperation.newInsert(NpctoBadgesProviderAdapter.NPCTOBADGES_URI)
                    .withValues(badgesValues)
                    .build());
        }

        try {
            ContentProviderResult[] results = prov.applyBatch(PokemonProvider.authority, operations);
            result = results[0].count;
        } catch (RemoteException e) {
            android.util.Log.e(TAG, e.getMessage());
        } catch (OperationApplicationException e) {
            android.util.Log.e(TAG, e.getMessage());
        }

        return result;
    }

    /** Relations operations. */
    /**
     * Get associate Objets.
     * @param item Npc
     * @return Objets
     */
    public ArrayList<Objets> getAssociateObjets(
            final Npc item) {
        ArrayList<Objets> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor objetsCursor = prov.query(
                ObjetsProviderAdapter.OBJETS_URI,
                ObjetsContract.ALIASED_COLS,
                ObjetsContract.ALIASED_COL_NPCOBJETSINTERNAL_ID
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        result = ObjetsContract.cursorToItems(
                        objetsCursor);
        objetsCursor.close();

        return result;
    }

    /**
     * Get associate Pokemons.
     * @param item Npc
     * @return Pokemons
     */
    public ArrayList<Pokemons> getAssociatePokemons(
            final Npc item) {
        ArrayList<Pokemons> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor pokemonsCursor = prov.query(
                PokemonsProviderAdapter.POKEMONS_URI,
                PokemonsContract.ALIASED_COLS,
                PokemonsContract.ALIASED_COL_NPCPOKEMONSINTERNAL_ID
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        result = PokemonsContract.cursorToItems(
                        pokemonsCursor);
        pokemonsCursor.close();

        return result;
    }

    /**
     * Get associate Badges.
     * @param item Npc
     * @return Badges
     */
    public ArrayList<Badges> getAssociateBadges(
            final Npc item) {
        ArrayList<Badges> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor npctoBadgesCursor = prov.query(
                NpctoBadgesProviderAdapter.NPCTOBADGES_URI,
                NpctoBadgesContract.ALIASED_COLS,
                NpctoBadgesContract.ALIASED_COL_NPCINTERNALID_ID 
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        if (npctoBadgesCursor.getCount() > 0) {
            CriteriaExpression badgesCrits =
                    new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            ArrayValue arrayValue = new ArrayValue();
            inCrit.setKey(BadgesContract.ALIASED_COL_ID);
            inCrit.setType(Type.IN);
            inCrit.addValue(arrayValue);
            badgesCrits.add(inCrit);

            while (npctoBadgesCursor.moveToNext()) {
                int index = npctoBadgesCursor.getColumnIndex(
                        NpctoBadgesContract.COL_BADGES_ID);
                String badgesId = npctoBadgesCursor.getString(index);

                arrayValue.addValue(badgesId);
            }
            npctoBadgesCursor.close();
            android.database.Cursor badgesCursor = prov.query(
                    BadgesProviderAdapter.BADGES_URI,
                    BadgesContract.ALIASED_COLS,
                    badgesCrits.toSQLiteSelection(),
                    badgesCrits.toSQLiteSelectionArgs(),
                    null);

            result = BadgesContract.cursorToItems(badgesCursor);
            badgesCursor.close();
        } else {
            result = new ArrayList<Badges>();
        }

        return result;
    }

    /**
     * Get associate Position.
     * @param item Npc
     * @return Positions
     */
    public Positions getAssociatePosition(
            final Npc item) {
        Positions result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor positionsCursor = prov.query(
                PositionsProviderAdapter.POSITIONS_URI,
                PositionsContract.ALIASED_COLS,
                PositionsContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getPosition().getId())},
                null);

        if (positionsCursor.getCount() > 0) {
            positionsCursor.moveToFirst();
            result = PositionsContract.cursorToItem(positionsCursor);
        } else {
            result = null;
        }
        positionsCursor.close();

        return result;
    }

}
