/**************************************************************************
 * ArenesProviderUtilsBase.java, pokemon Android
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
import com.kerhomjarnoin.pokemon.criterias.base.CriteriaExpression;
import com.kerhomjarnoin.pokemon.criterias.base.CriteriaExpression.GroupType;

import com.kerhomjarnoin.pokemon.entity.Arenes;
import com.kerhomjarnoin.pokemon.entity.Positions;
import com.kerhomjarnoin.pokemon.entity.Badges;

import com.kerhomjarnoin.pokemon.provider.ArenesProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.PositionsProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.BadgesProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.PokemonProvider;
import com.kerhomjarnoin.pokemon.provider.contract.ArenesContract;
import com.kerhomjarnoin.pokemon.provider.contract.PositionsContract;
import com.kerhomjarnoin.pokemon.provider.contract.BadgesContract;

/**
 * Arenes Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class ArenesProviderUtilsBase
            extends ProviderUtils<Arenes> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "ArenesProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public ArenesProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final Arenes item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = ArenesContract.itemToContentValues(item);
        itemValues.remove(ArenesContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                ArenesProviderAdapter.ARENES_URI)
                        .withValues(itemValues)
                        .build());


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
     * @param item Arenes
     * @return number of row affected
     */
    public int delete(final Arenes item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = ArenesProviderAdapter.ARENES_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return Arenes
     */
    public Arenes query(final Arenes item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return Arenes
     */
    public Arenes query(final int id) {
        Arenes result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(ArenesContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            ArenesProviderAdapter.ARENES_URI,
            ArenesContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = ArenesContract.cursorToItem(cursor);

            if (result.getPosition() != null) {
                result.setPosition(
                    this.getAssociatePosition(result));
            }
            if (result.getBadge() != null) {
                result.setBadge(
                    this.getAssociateBadge(result));
            }
        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<Arenes>
     */
    public ArrayList<Arenes> queryAll() {
        ArrayList<Arenes> result =
                    new ArrayList<Arenes>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                ArenesProviderAdapter.ARENES_URI,
                ArenesContract.ALIASED_COLS,
                null,
                null,
                null);

        result = ArenesContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<Arenes>
     */
    public ArrayList<Arenes> query(CriteriaExpression expression) {
        ArrayList<Arenes> result =
                    new ArrayList<Arenes>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                ArenesProviderAdapter.ARENES_URI,
                ArenesContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = ArenesContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item Arenes
     
     * @return number of rows updated
     */
    public int update(final Arenes item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = ArenesContract.itemToContentValues(
                item);

        Uri uri = ArenesProviderAdapter.ARENES_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());



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
     * Get associate Position.
     * @param item Arenes
     * @return Positions
     */
    public Positions getAssociatePosition(
            final Arenes item) {
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

    /**
     * Get associate Badge.
     * @param item Arenes
     * @return Badges
     */
    public Badges getAssociateBadge(
            final Arenes item) {
        Badges result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor badgesCursor = prov.query(
                BadgesProviderAdapter.BADGES_URI,
                BadgesContract.ALIASED_COLS,
                BadgesContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getBadge().getId())},
                null);

        if (badgesCursor.getCount() > 0) {
            badgesCursor.moveToFirst();
            result = BadgesContract.cursorToItem(badgesCursor);
        } else {
            result = null;
        }
        badgesCursor.close();

        return result;
    }

}
