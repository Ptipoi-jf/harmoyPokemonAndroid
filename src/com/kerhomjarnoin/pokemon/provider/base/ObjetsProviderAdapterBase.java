/**************************************************************************
 * ObjetsProviderAdapterBase.java, pokemon Android
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



import com.kerhomjarnoin.pokemon.entity.Objets;
import com.kerhomjarnoin.pokemon.provider.ProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.PokemonProvider;
import com.kerhomjarnoin.pokemon.provider.contract.ObjetsContract;
import com.kerhomjarnoin.pokemon.data.ObjetsSQLiteAdapter;
import com.kerhomjarnoin.pokemon.data.TypeObjetSQLiteAdapter;

/**
 * ObjetsProviderAdapterBase.
 */
public abstract class ObjetsProviderAdapterBase
                extends ProviderAdapter<Objets> {

    /** TAG for debug purpose. */
    protected static final String TAG = "ObjetsProviderAdapter";

    /** OBJETS_URI. */
    public      static Uri OBJETS_URI;

    /** objets type. */
    protected static final String objetsType =
            "objets";

    /** OBJETS_ALL. */
    protected static final int OBJETS_ALL =
            1939500691;
    /** OBJETS_ONE. */
    protected static final int OBJETS_ONE =
            1939500692;

    /** OBJETS_TYPE. */
    protected static final int OBJETS_TYPE =
            1939500693;

    /**
     * Static constructor.
     */
    static {
        OBJETS_URI =
                PokemonProvider.generateUri(
                        objetsType);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                objetsType,
                OBJETS_ALL);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                objetsType + "/#",
                OBJETS_ONE);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                objetsType + "/#" + "/type",
                OBJETS_TYPE);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public ObjetsProviderAdapterBase(
            PokemonProviderBase provider) {
        super(
            provider,
            new ObjetsSQLiteAdapter(provider.getContext()));

        this.uriIds.add(OBJETS_ALL);
        this.uriIds.add(OBJETS_ONE);
        this.uriIds.add(OBJETS_TYPE);
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
            case OBJETS_ALL:
                result = collection + "objets";
                break;
            case OBJETS_ONE:
                result = single + "objets";
                break;
            case OBJETS_TYPE:
                result = single + "objets";
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
            case OBJETS_ONE:
                String id = uri.getPathSegments().get(1);
                selection = ObjetsContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case OBJETS_ALL:
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
            case OBJETS_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(ObjetsContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            OBJETS_URI,
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
        android.database.Cursor objetsCursor;

        switch (matchedUri) {

            case OBJETS_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case OBJETS_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            case OBJETS_TYPE:
                objetsCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (objetsCursor.getCount() > 0) {
                    objetsCursor.moveToFirst();
                    int typeId = objetsCursor.getInt(
                            objetsCursor.getColumnIndex(
                                    ObjetsContract.COL_TYPE_ID));

                    TypeObjetSQLiteAdapter typeObjetAdapter = new TypeObjetSQLiteAdapter(this.ctx);
                    typeObjetAdapter.open(this.getDb());
                    result = typeObjetAdapter.query(typeId);
                }
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
            case OBJETS_ONE:
                selectionArgs = new String[1];
                selection = ObjetsContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case OBJETS_ALL:
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
        return OBJETS_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = ObjetsContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    ObjetsContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

