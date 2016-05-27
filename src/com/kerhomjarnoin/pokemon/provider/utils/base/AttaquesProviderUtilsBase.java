/**************************************************************************
 * AttaquesProviderUtilsBase.java, pokemon Android
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

import com.kerhomjarnoin.pokemon.entity.Attaques;
import com.kerhomjarnoin.pokemon.entity.Types;

import com.kerhomjarnoin.pokemon.provider.AttaquesProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.TypesProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.PokemonProvider;
import com.kerhomjarnoin.pokemon.provider.contract.AttaquesContract;
import com.kerhomjarnoin.pokemon.provider.contract.TypesContract;

/**
 * Attaques Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class AttaquesProviderUtilsBase
            extends ProviderUtils<Attaques> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "AttaquesProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public AttaquesProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final Attaques item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = AttaquesContract.itemToContentValues(item);
        itemValues.remove(AttaquesContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                AttaquesProviderAdapter.ATTAQUES_URI)
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
     * @param item Attaques
     * @return number of row affected
     */
    public int delete(final Attaques item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = AttaquesProviderAdapter.ATTAQUES_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return Attaques
     */
    public Attaques query(final Attaques item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return Attaques
     */
    public Attaques query(final int id) {
        Attaques result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(AttaquesContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            AttaquesProviderAdapter.ATTAQUES_URI,
            AttaquesContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = AttaquesContract.cursorToItem(cursor);

            if (result.getType() != null) {
                result.setType(
                    this.getAssociateType(result));
            }
        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<Attaques>
     */
    public ArrayList<Attaques> queryAll() {
        ArrayList<Attaques> result =
                    new ArrayList<Attaques>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                AttaquesProviderAdapter.ATTAQUES_URI,
                AttaquesContract.ALIASED_COLS,
                null,
                null,
                null);

        result = AttaquesContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<Attaques>
     */
    public ArrayList<Attaques> query(CriteriaExpression expression) {
        ArrayList<Attaques> result =
                    new ArrayList<Attaques>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                AttaquesProviderAdapter.ATTAQUES_URI,
                AttaquesContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = AttaquesContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item Attaques
     
     * @return number of rows updated
     */
    public int update(final Attaques item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = AttaquesContract.itemToContentValues(
                item);

        Uri uri = AttaquesProviderAdapter.ATTAQUES_URI;
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
     * Get associate Type.
     * @param item Attaques
     * @return Types
     */
    public Types getAssociateType(
            final Attaques item) {
        Types result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor typesCursor = prov.query(
                TypesProviderAdapter.TYPES_URI,
                TypesContract.ALIASED_COLS,
                TypesContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getType().getId())},
                null);

        if (typesCursor.getCount() > 0) {
            typesCursor.moveToFirst();
            result = TypesContract.cursorToItem(typesCursor);
        } else {
            result = null;
        }
        typesCursor.close();

        return result;
    }

}
