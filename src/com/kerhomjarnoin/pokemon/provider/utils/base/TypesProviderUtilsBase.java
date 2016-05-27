/**************************************************************************
 * TypesProviderUtilsBase.java, pokemon Android
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

import com.kerhomjarnoin.pokemon.entity.Types;

import com.kerhomjarnoin.pokemon.provider.TypesProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.PokemonProvider;
import com.kerhomjarnoin.pokemon.provider.contract.TypesContract;
import com.kerhomjarnoin.pokemon.provider.contract.TypeDePokemonsContract;

/**
 * Types Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class TypesProviderUtilsBase
            extends ProviderUtils<Types> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "TypesProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public TypesProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final Types item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = TypesContract.itemToContentValues(item);
        itemValues.remove(TypesContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                TypesProviderAdapter.TYPES_URI)
                        .withValues(itemValues)
                        .build());

        if (item.getFaibleContre() != null && item.getFaibleContre().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(TypesContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getFaibleContre().size(); i++) {
                inValue.addValue(String.valueOf(item.getFaibleContre().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(TypesProviderAdapter.TYPES_URI)
                    .withValueBackReference(
                            TypesContract
                                    .COL_TYPESFAIBLECONTREINTERNAL_ID,
                            0)
                    .withSelection(
                            crit.toSQLiteSelection(),
                            crit.toSQLiteSelectionArgs())
                    .build());
        }
        if (item.getFortContre() != null && item.getFortContre().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(TypesContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getFortContre().size(); i++) {
                inValue.addValue(String.valueOf(item.getFortContre().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(TypesProviderAdapter.TYPES_URI)
                    .withValueBackReference(
                            TypesContract
                                    .COL_TYPESFORTCONTREINTERNAL_ID,
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
     * Insert into DB.
     * @param item Types to insert
     * @param typeDePokemonstypesInternalId typeDePokemonstypesInternal Id* @param typesfaibleContreInternalId typesfaibleContreInternal Id* @param typesfortContreInternalId typesfortContreInternal Id
     * @return number of rows affected
     */
    public Uri insert(final Types item,
                             final int typeDePokemonstypesInternalId,
                             final int typesfaibleContreInternalId,
                             final int typesfortContreInternalId) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();

        ContentValues itemValues = TypesContract.itemToContentValues(item,
                    typeDePokemonstypesInternalId,
                    typesfaibleContreInternalId,
                    typesfortContreInternalId);
        itemValues.remove(TypesContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                TypesProviderAdapter.TYPES_URI)
                    .withValues(itemValues)
                    .build());


        if (item.getFaibleContre() != null && item.getFaibleContre().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(TypesContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getFaibleContre().size(); i++) {
                inValue.addValue(String.valueOf(item.getFaibleContre().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(TypesProviderAdapter.TYPES_URI)
                    .withValueBackReference(
                            TypesContract
                                    .COL_TYPESFAIBLECONTREINTERNAL_ID,
                            0)
                    .withSelection(
                            crit.toSQLiteSelection(),
                            crit.toSQLiteSelectionArgs())
                    .build());
        }
        if (item.getFortContre() != null && item.getFortContre().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(TypesContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getFortContre().size(); i++) {
                inValue.addValue(String.valueOf(item.getFortContre().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(TypesProviderAdapter.TYPES_URI)
                    .withValueBackReference(
                            TypesContract
                                    .COL_TYPESFORTCONTREINTERNAL_ID,
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
                item.setId(Integer.parseInt(result.getLastPathSegment()));
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
     * @param item Types
     * @return number of row affected
     */
    public int delete(final Types item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = TypesProviderAdapter.TYPES_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return Types
     */
    public Types query(final Types item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return Types
     */
    public Types query(final int id) {
        Types result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(TypesContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            TypesProviderAdapter.TYPES_URI,
            TypesContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = TypesContract.cursorToItem(cursor);

            result.setFaibleContre(
                this.getAssociateFaibleContre(result));
            result.setFortContre(
                this.getAssociateFortContre(result));
        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<Types>
     */
    public ArrayList<Types> queryAll() {
        ArrayList<Types> result =
                    new ArrayList<Types>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                TypesProviderAdapter.TYPES_URI,
                TypesContract.ALIASED_COLS,
                null,
                null,
                null);

        result = TypesContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<Types>
     */
    public ArrayList<Types> query(CriteriaExpression expression) {
        ArrayList<Types> result =
                    new ArrayList<Types>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                TypesProviderAdapter.TYPES_URI,
                TypesContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = TypesContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item Types
     * @return number of rows updated
     */
    public int update(final Types item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = TypesContract.itemToContentValues(item);

        Uri uri = TypesProviderAdapter.TYPES_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());


        if (item.getFaibleContre() != null && item.getFaibleContre().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new faibleContre for Types
            CriteriaExpression faibleContreCrit = 
                    new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(TypesContract.COL_ID);
            crit.addValue(values);
            faibleContreCrit.add(crit);


            for (Types faibleContre : item.getFaibleContre()) {
                values.addValue(
                    String.valueOf(faibleContre.getId()));
            }
            selection = faibleContreCrit.toSQLiteSelection();
            selectionArgs = faibleContreCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    TypesProviderAdapter.TYPES_URI)
                    .withValue(
                            TypesContract.COL_TYPESFAIBLECONTREINTERNAL_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated faibleContre
            crit.setType(Type.NOT_IN);
            faibleContreCrit.add(TypesContract.COL_TYPESFAIBLECONTREINTERNAL_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    TypesProviderAdapter.TYPES_URI)
                    .withValue(
                            TypesContract.COL_TYPESFAIBLECONTREINTERNAL_ID,
                            null)
                    .withSelection(
                            faibleContreCrit.toSQLiteSelection(),
                            faibleContreCrit.toSQLiteSelectionArgs())
                    .build());
        }

        if (item.getFortContre() != null && item.getFortContre().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new fortContre for Types
            CriteriaExpression fortContreCrit = 
                    new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(TypesContract.COL_ID);
            crit.addValue(values);
            fortContreCrit.add(crit);


            for (Types fortContre : item.getFortContre()) {
                values.addValue(
                    String.valueOf(fortContre.getId()));
            }
            selection = fortContreCrit.toSQLiteSelection();
            selectionArgs = fortContreCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    TypesProviderAdapter.TYPES_URI)
                    .withValue(
                            TypesContract.COL_TYPESFORTCONTREINTERNAL_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated fortContre
            crit.setType(Type.NOT_IN);
            fortContreCrit.add(TypesContract.COL_TYPESFORTCONTREINTERNAL_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    TypesProviderAdapter.TYPES_URI)
                    .withValue(
                            TypesContract.COL_TYPESFORTCONTREINTERNAL_ID,
                            null)
                    .withSelection(
                            fortContreCrit.toSQLiteSelection(),
                            fortContreCrit.toSQLiteSelectionArgs())
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

    /**
     * Updates the DB.
     * @param item Types
     * @param typeDePokemonstypesInternalId typeDePokemonstypesInternal Id* @param typesfaibleContreInternalId typesfaibleContreInternal Id* @param typesfortContreInternalId typesfortContreInternal Id
     * @return number of rows updated
     */
    public int update(final Types item,
                             final int typeDePokemonstypesInternalId,
                             final int typesfaibleContreInternalId,
                             final int typesfortContreInternalId) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = TypesContract.itemToContentValues(
                item,
                typeDePokemonstypesInternalId,
                typesfaibleContreInternalId,
                typesfortContreInternalId);

        Uri uri = TypesProviderAdapter.TYPES_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());


        if (item.getFaibleContre() != null && item.getFaibleContre().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new faibleContre for Types
            CriteriaExpression faibleContreCrit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(TypesContract.COL_ID);
            crit.addValue(values);
            faibleContreCrit.add(crit);


            for (Types faibleContre : item.getFaibleContre()) {
                values.addValue(
                    String.valueOf(faibleContre.getId()));
            }
            selection = faibleContreCrit.toSQLiteSelection();
            selectionArgs = faibleContreCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    TypesProviderAdapter.TYPES_URI)
                    .withValue(
                            TypesContract.COL_TYPESFAIBLECONTREINTERNAL_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated faibleContre
            crit.setType(Type.NOT_IN);
            faibleContreCrit.add(TypesContract.COL_TYPESFAIBLECONTREINTERNAL_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    TypesProviderAdapter.TYPES_URI)
                    .withValue(
                            TypesContract.COL_TYPESFAIBLECONTREINTERNAL_ID,
                            null)
                    .withSelection(
                            faibleContreCrit.toSQLiteSelection(),
                            faibleContreCrit.toSQLiteSelectionArgs())
                    .build());
        }

        if (item.getFortContre() != null && item.getFortContre().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new fortContre for Types
            CriteriaExpression fortContreCrit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(TypesContract.COL_ID);
            crit.addValue(values);
            fortContreCrit.add(crit);


            for (Types fortContre : item.getFortContre()) {
                values.addValue(
                    String.valueOf(fortContre.getId()));
            }
            selection = fortContreCrit.toSQLiteSelection();
            selectionArgs = fortContreCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    TypesProviderAdapter.TYPES_URI)
                    .withValue(
                            TypesContract.COL_TYPESFORTCONTREINTERNAL_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated fortContre
            crit.setType(Type.NOT_IN);
            fortContreCrit.add(TypesContract.COL_TYPESFORTCONTREINTERNAL_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    TypesProviderAdapter.TYPES_URI)
                    .withValue(
                            TypesContract.COL_TYPESFORTCONTREINTERNAL_ID,
                            null)
                    .withSelection(
                            fortContreCrit.toSQLiteSelection(),
                            fortContreCrit.toSQLiteSelectionArgs())
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
     * Get associate FaibleContre.
     * @param item Types
     * @return Types
     */
    public ArrayList<Types> getAssociateFaibleContre(
            final Types item) {
        ArrayList<Types> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor typesCursor = prov.query(
                TypesProviderAdapter.TYPES_URI,
                TypesContract.ALIASED_COLS,
                TypesContract.ALIASED_COL_TYPESFAIBLECONTREINTERNAL_ID
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        result = TypesContract.cursorToItems(
                        typesCursor);
        typesCursor.close();

        return result;
    }

    /**
     * Get associate FortContre.
     * @param item Types
     * @return Types
     */
    public ArrayList<Types> getAssociateFortContre(
            final Types item) {
        ArrayList<Types> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor typesCursor = prov.query(
                TypesProviderAdapter.TYPES_URI,
                TypesContract.ALIASED_COLS,
                TypesContract.ALIASED_COL_TYPESFORTCONTREINTERNAL_ID
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        result = TypesContract.cursorToItems(
                        typesCursor);
        typesCursor.close();

        return result;
    }

}
