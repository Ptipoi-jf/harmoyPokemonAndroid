/**************************************************************************
 * AttaquesProviderAdapterBase.java, pokemon Android
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



import com.kerhomjarnoin.pokemon.entity.Attaques;
import com.kerhomjarnoin.pokemon.provider.ProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.PokemonProvider;
import com.kerhomjarnoin.pokemon.provider.contract.AttaquesContract;
import com.kerhomjarnoin.pokemon.data.AttaquesSQLiteAdapter;
import com.kerhomjarnoin.pokemon.data.TypesSQLiteAdapter;

/**
 * AttaquesProviderAdapterBase.
 */
public abstract class AttaquesProviderAdapterBase
                extends ProviderAdapter<Attaques> {

    /** TAG for debug purpose. */
    protected static final String TAG = "AttaquesProviderAdapter";

    /** ATTAQUES_URI. */
    public      static Uri ATTAQUES_URI;

    /** attaques type. */
    protected static final String attaquesType =
            "attaques";

    /** ATTAQUES_ALL. */
    protected static final int ATTAQUES_ALL =
            603811794;
    /** ATTAQUES_ONE. */
    protected static final int ATTAQUES_ONE =
            603811795;

    /** ATTAQUES_TYPE. */
    protected static final int ATTAQUES_TYPE =
            603811796;

    /**
     * Static constructor.
     */
    static {
        ATTAQUES_URI =
                PokemonProvider.generateUri(
                        attaquesType);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                attaquesType,
                ATTAQUES_ALL);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                attaquesType + "/#",
                ATTAQUES_ONE);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                attaquesType + "/#" + "/type",
                ATTAQUES_TYPE);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public AttaquesProviderAdapterBase(
            PokemonProviderBase provider) {
        super(
            provider,
            new AttaquesSQLiteAdapter(provider.getContext()));

        this.uriIds.add(ATTAQUES_ALL);
        this.uriIds.add(ATTAQUES_ONE);
        this.uriIds.add(ATTAQUES_TYPE);
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
            case ATTAQUES_ALL:
                result = collection + "attaques";
                break;
            case ATTAQUES_ONE:
                result = single + "attaques";
                break;
            case ATTAQUES_TYPE:
                result = single + "attaques";
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
            case ATTAQUES_ONE:
                String id = uri.getPathSegments().get(1);
                selection = AttaquesContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case ATTAQUES_ALL:
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
            case ATTAQUES_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(AttaquesContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            ATTAQUES_URI,
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
        android.database.Cursor attaquesCursor;

        switch (matchedUri) {

            case ATTAQUES_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case ATTAQUES_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            case ATTAQUES_TYPE:
                attaquesCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (attaquesCursor.getCount() > 0) {
                    attaquesCursor.moveToFirst();
                    int typeId = attaquesCursor.getInt(
                            attaquesCursor.getColumnIndex(
                                    AttaquesContract.COL_TYPE_ID));

                    TypesSQLiteAdapter typesAdapter = new TypesSQLiteAdapter(this.ctx);
                    typesAdapter.open(this.getDb());
                    result = typesAdapter.query(typeId);
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
            case ATTAQUES_ONE:
                selectionArgs = new String[1];
                selection = AttaquesContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case ATTAQUES_ALL:
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
        return ATTAQUES_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = AttaquesContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    AttaquesContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

