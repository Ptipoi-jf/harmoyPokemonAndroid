/**************************************************************************
 * ArenesProviderAdapterBase.java, pokemon Android
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



import com.kerhomjarnoin.pokemon.entity.Arenes;
import com.kerhomjarnoin.pokemon.provider.ProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.PokemonProvider;
import com.kerhomjarnoin.pokemon.provider.contract.ArenesContract;
import com.kerhomjarnoin.pokemon.data.ArenesSQLiteAdapter;
import com.kerhomjarnoin.pokemon.data.PositionsSQLiteAdapter;
import com.kerhomjarnoin.pokemon.data.BadgesSQLiteAdapter;

/**
 * ArenesProviderAdapterBase.
 */
public abstract class ArenesProviderAdapterBase
                extends ProviderAdapter<Arenes> {

    /** TAG for debug purpose. */
    protected static final String TAG = "ArenesProviderAdapter";

    /** ARENES_URI. */
    public      static Uri ARENES_URI;

    /** arenes type. */
    protected static final String arenesType =
            "arenes";

    /** ARENES_ALL. */
    protected static final int ARENES_ALL =
            1969294056;
    /** ARENES_ONE. */
    protected static final int ARENES_ONE =
            1969294057;

    /** ARENES_POSITION. */
    protected static final int ARENES_POSITION =
            1969294058;
    /** ARENES_BADGE. */
    protected static final int ARENES_BADGE =
            1969294059;

    /**
     * Static constructor.
     */
    static {
        ARENES_URI =
                PokemonProvider.generateUri(
                        arenesType);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                arenesType,
                ARENES_ALL);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                arenesType + "/#",
                ARENES_ONE);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                arenesType + "/#" + "/position",
                ARENES_POSITION);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                arenesType + "/#" + "/badge",
                ARENES_BADGE);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public ArenesProviderAdapterBase(
            PokemonProviderBase provider) {
        super(
            provider,
            new ArenesSQLiteAdapter(provider.getContext()));

        this.uriIds.add(ARENES_ALL);
        this.uriIds.add(ARENES_ONE);
        this.uriIds.add(ARENES_POSITION);
        this.uriIds.add(ARENES_BADGE);
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
            case ARENES_ALL:
                result = collection + "arenes";
                break;
            case ARENES_ONE:
                result = single + "arenes";
                break;
            case ARENES_POSITION:
                result = single + "arenes";
                break;
            case ARENES_BADGE:
                result = single + "arenes";
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
            case ARENES_ONE:
                String id = uri.getPathSegments().get(1);
                selection = ArenesContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case ARENES_ALL:
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
            case ARENES_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(ArenesContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            ARENES_URI,
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
        android.database.Cursor arenesCursor;

        switch (matchedUri) {

            case ARENES_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case ARENES_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            case ARENES_POSITION:
                arenesCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (arenesCursor.getCount() > 0) {
                    arenesCursor.moveToFirst();
                    int positionId = arenesCursor.getInt(
                            arenesCursor.getColumnIndex(
                                    ArenesContract.COL_POSITION_ID));

                    PositionsSQLiteAdapter positionsAdapter = new PositionsSQLiteAdapter(this.ctx);
                    positionsAdapter.open(this.getDb());
                    result = positionsAdapter.query(positionId);
                }
                break;

            case ARENES_BADGE:
                arenesCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (arenesCursor.getCount() > 0) {
                    arenesCursor.moveToFirst();
                    int badgeId = arenesCursor.getInt(
                            arenesCursor.getColumnIndex(
                                    ArenesContract.COL_BADGE_ID));

                    BadgesSQLiteAdapter badgesAdapter = new BadgesSQLiteAdapter(this.ctx);
                    badgesAdapter.open(this.getDb());
                    result = badgesAdapter.query(badgeId);
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
            case ARENES_ONE:
                selectionArgs = new String[1];
                selection = ArenesContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case ARENES_ALL:
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
        return ARENES_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = ArenesContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    ArenesContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

