/**************************************************************************
 * PokemonsProviderUtilsBase.java, pokemon Android
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

import com.kerhomjarnoin.pokemon.entity.Pokemons;
import com.kerhomjarnoin.pokemon.entity.TypeDePokemons;
import com.kerhomjarnoin.pokemon.entity.Attaques;

import com.kerhomjarnoin.pokemon.provider.PokemonsProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.TypeDePokemonsProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.AttaquesProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.PokemonProvider;
import com.kerhomjarnoin.pokemon.provider.contract.PokemonsContract;
import com.kerhomjarnoin.pokemon.provider.contract.NpcContract;
import com.kerhomjarnoin.pokemon.provider.contract.TypeDePokemonsContract;
import com.kerhomjarnoin.pokemon.provider.contract.AttaquesContract;

/**
 * Pokemons Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class PokemonsProviderUtilsBase
            extends ProviderUtils<Pokemons> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "PokemonsProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public PokemonsProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final Pokemons item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = PokemonsContract.itemToContentValues(item);
        itemValues.remove(PokemonsContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                PokemonsProviderAdapter.POKEMONS_URI)
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
     * Insert into DB.
     * @param item Pokemons to insert
     * @param npcpokemonsInternalId npcpokemonsInternal Id
     * @return number of rows affected
     */
    public Uri insert(final Pokemons item,
                             final int npcpokemonsInternalId) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();

        ContentValues itemValues = PokemonsContract.itemToContentValues(item,
                    npcpokemonsInternalId);
        itemValues.remove(PokemonsContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                PokemonsProviderAdapter.POKEMONS_URI)
                    .withValues(itemValues)
                    .build());



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
     * @param item Pokemons
     * @return number of row affected
     */
    public int delete(final Pokemons item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = PokemonsProviderAdapter.POKEMONS_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return Pokemons
     */
    public Pokemons query(final Pokemons item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return Pokemons
     */
    public Pokemons query(final int id) {
        Pokemons result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(PokemonsContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            PokemonsProviderAdapter.POKEMONS_URI,
            PokemonsContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = PokemonsContract.cursorToItem(cursor);

            if (result.getTypePokemon() != null) {
                result.setTypePokemon(
                    this.getAssociateTypePokemon(result));
            }
            if (result.getAttaque1() != null) {
                result.setAttaque1(
                    this.getAssociateAttaque1(result));
            }
            if (result.getAttaque2() != null) {
                result.setAttaque2(
                    this.getAssociateAttaque2(result));
            }
            if (result.getAttaque3() != null) {
                result.setAttaque3(
                    this.getAssociateAttaque3(result));
            }
            if (result.getAttaque4() != null) {
                result.setAttaque4(
                    this.getAssociateAttaque4(result));
            }
        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<Pokemons>
     */
    public ArrayList<Pokemons> queryAll() {
        ArrayList<Pokemons> result =
                    new ArrayList<Pokemons>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                PokemonsProviderAdapter.POKEMONS_URI,
                PokemonsContract.ALIASED_COLS,
                null,
                null,
                null);

        result = PokemonsContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<Pokemons>
     */
    public ArrayList<Pokemons> query(CriteriaExpression expression) {
        ArrayList<Pokemons> result =
                    new ArrayList<Pokemons>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                PokemonsProviderAdapter.POKEMONS_URI,
                PokemonsContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = PokemonsContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item Pokemons
     * @return number of rows updated
     */
    public int update(final Pokemons item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = PokemonsContract.itemToContentValues(item);

        Uri uri = PokemonsProviderAdapter.POKEMONS_URI;
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

    /**
     * Updates the DB.
     * @param item Pokemons
     * @param npcpokemonsInternalId npcpokemonsInternal Id
     * @return number of rows updated
     */
    public int update(final Pokemons item,
                             final int npcpokemonsInternalId) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = PokemonsContract.itemToContentValues(
                item,
                npcpokemonsInternalId);

        Uri uri = PokemonsProviderAdapter.POKEMONS_URI;
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
     * Get associate TypePokemon.
     * @param item Pokemons
     * @return TypeDePokemons
     */
    public TypeDePokemons getAssociateTypePokemon(
            final Pokemons item) {
        TypeDePokemons result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor typeDePokemonsCursor = prov.query(
                TypeDePokemonsProviderAdapter.TYPEDEPOKEMONS_URI,
                TypeDePokemonsContract.ALIASED_COLS,
                TypeDePokemonsContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getTypePokemon().getId())},
                null);

        if (typeDePokemonsCursor.getCount() > 0) {
            typeDePokemonsCursor.moveToFirst();
            result = TypeDePokemonsContract.cursorToItem(typeDePokemonsCursor);
        } else {
            result = null;
        }
        typeDePokemonsCursor.close();

        return result;
    }

    /**
     * Get associate Attaque1.
     * @param item Pokemons
     * @return Attaques
     */
    public Attaques getAssociateAttaque1(
            final Pokemons item) {
        Attaques result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor attaquesCursor = prov.query(
                AttaquesProviderAdapter.ATTAQUES_URI,
                AttaquesContract.ALIASED_COLS,
                AttaquesContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getAttaque1().getId())},
                null);

        if (attaquesCursor.getCount() > 0) {
            attaquesCursor.moveToFirst();
            result = AttaquesContract.cursorToItem(attaquesCursor);
        } else {
            result = null;
        }
        attaquesCursor.close();

        return result;
    }

    /**
     * Get associate Attaque2.
     * @param item Pokemons
     * @return Attaques
     */
    public Attaques getAssociateAttaque2(
            final Pokemons item) {
        Attaques result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor attaquesCursor = prov.query(
                AttaquesProviderAdapter.ATTAQUES_URI,
                AttaquesContract.ALIASED_COLS,
                AttaquesContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getAttaque2().getId())},
                null);

        if (attaquesCursor.getCount() > 0) {
            attaquesCursor.moveToFirst();
            result = AttaquesContract.cursorToItem(attaquesCursor);
        } else {
            result = null;
        }
        attaquesCursor.close();

        return result;
    }

    /**
     * Get associate Attaque3.
     * @param item Pokemons
     * @return Attaques
     */
    public Attaques getAssociateAttaque3(
            final Pokemons item) {
        Attaques result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor attaquesCursor = prov.query(
                AttaquesProviderAdapter.ATTAQUES_URI,
                AttaquesContract.ALIASED_COLS,
                AttaquesContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getAttaque3().getId())},
                null);

        if (attaquesCursor.getCount() > 0) {
            attaquesCursor.moveToFirst();
            result = AttaquesContract.cursorToItem(attaquesCursor);
        } else {
            result = null;
        }
        attaquesCursor.close();

        return result;
    }

    /**
     * Get associate Attaque4.
     * @param item Pokemons
     * @return Attaques
     */
    public Attaques getAssociateAttaque4(
            final Pokemons item) {
        Attaques result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor attaquesCursor = prov.query(
                AttaquesProviderAdapter.ATTAQUES_URI,
                AttaquesContract.ALIASED_COLS,
                AttaquesContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getAttaque4().getId())},
                null);

        if (attaquesCursor.getCount() > 0) {
            attaquesCursor.moveToFirst();
            result = AttaquesContract.cursorToItem(attaquesCursor);
        } else {
            result = null;
        }
        attaquesCursor.close();

        return result;
    }

}
