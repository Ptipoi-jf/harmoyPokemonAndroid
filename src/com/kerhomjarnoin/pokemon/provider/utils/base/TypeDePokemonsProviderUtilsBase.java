/**************************************************************************
 * TypeDePokemonsProviderUtilsBase.java, pokemon Android
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

import com.kerhomjarnoin.pokemon.entity.TypeDePokemons;
import com.kerhomjarnoin.pokemon.entity.Types;

import com.kerhomjarnoin.pokemon.provider.TypeDePokemonsProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.TypesProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.PokemonProvider;
import com.kerhomjarnoin.pokemon.provider.contract.TypeDePokemonsContract;
import com.kerhomjarnoin.pokemon.provider.contract.TypesContract;

/**
 * TypeDePokemons Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class TypeDePokemonsProviderUtilsBase
            extends ProviderUtils<TypeDePokemons> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "TypeDePokemonsProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public TypeDePokemonsProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final TypeDePokemons item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = TypeDePokemonsContract.itemToContentValues(item);
        itemValues.remove(TypeDePokemonsContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                TypeDePokemonsProviderAdapter.TYPEDEPOKEMONS_URI)
                        .withValues(itemValues)
                        .build());

        if (item.getTypes() != null && item.getTypes().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(TypesContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getTypes().size(); i++) {
                inValue.addValue(String.valueOf(item.getTypes().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(TypesProviderAdapter.TYPES_URI)
                    .withValueBackReference(
                            TypesContract
                                    .COL_TYPEDEPOKEMONSTYPESINTERNAL_ID,
                            0)
                    .withSelection(
                            crit.toSQLiteSelection(),
                            crit.toSQLiteSelectionArgs())
                    .build());
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
     * @param item TypeDePokemons
     * @return number of row affected
     */
    public int delete(final TypeDePokemons item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = TypeDePokemonsProviderAdapter.TYPEDEPOKEMONS_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return TypeDePokemons
     */
    public TypeDePokemons query(final TypeDePokemons item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return TypeDePokemons
     */
    public TypeDePokemons query(final int id) {
        TypeDePokemons result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(TypeDePokemonsContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            TypeDePokemonsProviderAdapter.TYPEDEPOKEMONS_URI,
            TypeDePokemonsContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = TypeDePokemonsContract.cursorToItem(cursor);

            result.setTypes(
                this.getAssociateTypes(result));
        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<TypeDePokemons>
     */
    public ArrayList<TypeDePokemons> queryAll() {
        ArrayList<TypeDePokemons> result =
                    new ArrayList<TypeDePokemons>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                TypeDePokemonsProviderAdapter.TYPEDEPOKEMONS_URI,
                TypeDePokemonsContract.ALIASED_COLS,
                null,
                null,
                null);

        result = TypeDePokemonsContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<TypeDePokemons>
     */
    public ArrayList<TypeDePokemons> query(CriteriaExpression expression) {
        ArrayList<TypeDePokemons> result =
                    new ArrayList<TypeDePokemons>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                TypeDePokemonsProviderAdapter.TYPEDEPOKEMONS_URI,
                TypeDePokemonsContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = TypeDePokemonsContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item TypeDePokemons
     
     * @return number of rows updated
     */
    public int update(final TypeDePokemons item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = TypeDePokemonsContract.itemToContentValues(
                item);

        Uri uri = TypeDePokemonsProviderAdapter.TYPEDEPOKEMONS_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());


        if (item.getTypes() != null && item.getTypes().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new types for TypeDePokemons
            CriteriaExpression typesCrit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(TypesContract.COL_ID);
            crit.addValue(values);
            typesCrit.add(crit);


            for (Types types : item.getTypes()) {
                values.addValue(
                    String.valueOf(types.getId()));
            }
            selection = typesCrit.toSQLiteSelection();
            selectionArgs = typesCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    TypesProviderAdapter.TYPES_URI)
                    .withValue(
                            TypesContract.COL_TYPEDEPOKEMONSTYPESINTERNAL_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated types
            crit.setType(Type.NOT_IN);
            typesCrit.add(TypesContract.COL_TYPEDEPOKEMONSTYPESINTERNAL_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    TypesProviderAdapter.TYPES_URI)
                    .withValue(
                            TypesContract.COL_TYPEDEPOKEMONSTYPESINTERNAL_ID,
                            null)
                    .withSelection(
                            typesCrit.toSQLiteSelection(),
                            typesCrit.toSQLiteSelectionArgs())
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
     * Get associate Types.
     * @param item TypeDePokemons
     * @return Types
     */
    public ArrayList<Types> getAssociateTypes(
            final TypeDePokemons item) {
        ArrayList<Types> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor typesCursor = prov.query(
                TypesProviderAdapter.TYPES_URI,
                TypesContract.ALIASED_COLS,
                TypesContract.ALIASED_COL_TYPEDEPOKEMONSTYPESINTERNAL_ID
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        result = TypesContract.cursorToItems(
                        typesCursor);
        typesCursor.close();

        return result;
    }

}
