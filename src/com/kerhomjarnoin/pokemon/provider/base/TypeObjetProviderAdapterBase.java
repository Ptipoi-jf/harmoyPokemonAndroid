/**************************************************************************
 * TypeObjetProviderAdapterBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.provider.base;

import android.content.ContentUris;
import android.content.ContentValues;


import com.google.common.collect.ObjectArrays;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;



import com.kerhomjarnoin.pokemon.entity.TypeObjet;
import com.kerhomjarnoin.pokemon.provider.ProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.PokemonProvider;
import com.kerhomjarnoin.pokemon.provider.contract.TypeObjetContract;
import com.kerhomjarnoin.pokemon.data.TypeObjetSQLiteAdapter;

/**
 * TypeObjetProviderAdapterBase.
 */
public abstract class TypeObjetProviderAdapterBase
                extends ProviderAdapter<TypeObjet> {

    /** TAG for debug purpose. */
    protected static final String TAG = "TypeObjetProviderAdapter";

    /** TYPEOBJET_URI. */
    public      static Uri TYPEOBJET_URI;

    /** typeObjet type. */
    protected static final String typeObjetType =
            "typeobjet";

    /** TYPEOBJET_ALL. */
    protected static final int TYPEOBJET_ALL =
            1786942292;
    /** TYPEOBJET_ONE. */
    protected static final int TYPEOBJET_ONE =
            1786942293;


    /**
     * Static constructor.
     */
    static {
        TYPEOBJET_URI =
                PokemonProvider.generateUri(
                        typeObjetType);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                typeObjetType,
                TYPEOBJET_ALL);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                typeObjetType + "/#",
                TYPEOBJET_ONE);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public TypeObjetProviderAdapterBase(
            PokemonProviderBase provider) {
        super(
            provider,
            new TypeObjetSQLiteAdapter(provider.getContext()));

        this.uriIds.add(TYPEOBJET_ALL);
        this.uriIds.add(TYPEOBJET_ONE);
    }

    @Override
    public String getType(final Uri uri) {
        String result;
        final String single =
                "vnc.android.cursor.item/"
                    + PokemonProvider.authority + ".";
        final String collection =
                "vnc.android.cursor.collection/"
                    + PokemonProvider.authority + ".";

        int matchedUri = PokemonProviderBase
                .getUriMatcher().match(uri);

        switch (matchedUri) {
            case TYPEOBJET_ALL:
                result = collection + "typeobjet";
                break;
            case TYPEOBJET_ONE:
                result = single + "typeobjet";
                break;
            default:
                result = null;
                break;
        }

        return result;
    }

    @Override
    public int delete(
            final Uri uri,
            String selection,
            String[] selectionArgs) {
        int matchedUri = PokemonProviderBase
                    .getUriMatcher().match(uri);
        int result = -1;
        switch (matchedUri) {
            case TYPEOBJET_ONE:
                String id = uri.getPathSegments().get(1);
                selection = TypeObjetContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case TYPEOBJET_ALL:
                result = this.adapter.delete(
                            selection,
                            selectionArgs);
                break;
            default:
                result = -1;
                break;
        }
        return result;
    }

    @Override
    public Uri insert(final Uri uri, final ContentValues values) {
        int matchedUri = PokemonProviderBase
                .getUriMatcher().match(uri);
                Uri result = null;
        int id = 0;
        switch (matchedUri) {
            case TYPEOBJET_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(TypeObjetContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            TYPEOBJET_URI,
                            String.valueOf(id));
                }
                break;
            default:
                result = null;
                break;
        }
        return result;
    }

    @Override
    public android.database.Cursor query(final Uri uri,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        final String sortOrder) {

        int matchedUri = PokemonProviderBase.getUriMatcher()
                .match(uri);
        android.database.Cursor result = null;

        switch (matchedUri) {

            case TYPEOBJET_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case TYPEOBJET_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            default:
                result = null;
                break;
        }

        return result;
    }

    @Override
    public int update(
            final Uri uri,
            final ContentValues values,
            String selection,
            String[] selectionArgs) {

        
        int matchedUri = PokemonProviderBase.getUriMatcher()
                .match(uri);
        int result = -1;
        switch (matchedUri) {
            case TYPEOBJET_ONE:
                selectionArgs = new String[1];
                selection = TypeObjetContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case TYPEOBJET_ALL:
                result = this.adapter.update(
                            values,
                            selection,
                            selectionArgs);
                break;
            default:
                result = -1;
                break;
        }
        return result;
    }



    /**
     * Get the entity URI.
     * @return The URI
     */
    @Override
    public Uri getUri() {
        return TYPEOBJET_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = TypeObjetContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    TypeObjetContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

