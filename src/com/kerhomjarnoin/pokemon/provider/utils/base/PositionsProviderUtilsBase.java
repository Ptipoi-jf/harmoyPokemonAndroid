/**************************************************************************
 * PositionsProviderUtilsBase.java, pokemon Android
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

import com.kerhomjarnoin.pokemon.entity.Positions;

import com.kerhomjarnoin.pokemon.provider.PositionsProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.PokemonProvider;
import com.kerhomjarnoin.pokemon.provider.contract.PositionsContract;

/**
 * Positions Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class PositionsProviderUtilsBase
            extends ProviderUtils<Positions> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "PositionsProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public PositionsProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final Positions item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = PositionsContract.itemToContentValues(item);
        itemValues.remove(PositionsContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                PositionsProviderAdapter.POSITIONS_URI)
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
     * @param item Positions
     * @return number of row affected
     */
    public int delete(final Positions item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = PositionsProviderAdapter.POSITIONS_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return Positions
     */
    public Positions query(final Positions item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return Positions
     */
    public Positions query(final int id) {
        Positions result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(PositionsContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            PositionsProviderAdapter.POSITIONS_URI,
            PositionsContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = PositionsContract.cursorToItem(cursor);

        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<Positions>
     */
    public ArrayList<Positions> queryAll() {
        ArrayList<Positions> result =
                    new ArrayList<Positions>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                PositionsProviderAdapter.POSITIONS_URI,
                PositionsContract.ALIASED_COLS,
                null,
                null,
                null);

        result = PositionsContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<Positions>
     */
    public ArrayList<Positions> query(CriteriaExpression expression) {
        ArrayList<Positions> result =
                    new ArrayList<Positions>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                PositionsProviderAdapter.POSITIONS_URI,
                PositionsContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = PositionsContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item Positions
     
     * @return number of rows updated
     */
    public int update(final Positions item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = PositionsContract.itemToContentValues(
                item);

        Uri uri = PositionsProviderAdapter.POSITIONS_URI;
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

    
}
