/**************************************************************************
 * ZonesProviderUtilsBase.java, pokemon Android
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

import com.kerhomjarnoin.pokemon.entity.Zones;

import com.kerhomjarnoin.pokemon.provider.ZonesProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.PokemonProvider;
import com.kerhomjarnoin.pokemon.provider.contract.ZonesContract;

/**
 * Zones Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class ZonesProviderUtilsBase
            extends ProviderUtils<Zones> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "ZonesProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public ZonesProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final Zones item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = ZonesContract.itemToContentValues(item);
        itemValues.remove(ZonesContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                ZonesProviderAdapter.ZONES_URI)
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
     * @param item Zones
     * @return number of row affected
     */
    public int delete(final Zones item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = ZonesProviderAdapter.ZONES_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return Zones
     */
    public Zones query(final Zones item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return Zones
     */
    public Zones query(final int id) {
        Zones result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(ZonesContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            ZonesProviderAdapter.ZONES_URI,
            ZonesContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = ZonesContract.cursorToItem(cursor);

        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<Zones>
     */
    public ArrayList<Zones> queryAll() {
        ArrayList<Zones> result =
                    new ArrayList<Zones>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                ZonesProviderAdapter.ZONES_URI,
                ZonesContract.ALIASED_COLS,
                null,
                null,
                null);

        result = ZonesContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<Zones>
     */
    public ArrayList<Zones> query(CriteriaExpression expression) {
        ArrayList<Zones> result =
                    new ArrayList<Zones>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                ZonesProviderAdapter.ZONES_URI,
                ZonesContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = ZonesContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item Zones
     
     * @return number of rows updated
     */
    public int update(final Zones item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = ZonesContract.itemToContentValues(
                item);

        Uri uri = ZonesProviderAdapter.ZONES_URI;
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
