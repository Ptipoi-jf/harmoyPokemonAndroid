/**************************************************************************
 * DresseursProviderAdapterBase.java, pokemon Android
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



import com.kerhomjarnoin.pokemon.entity.Dresseurs;
import com.kerhomjarnoin.pokemon.provider.ProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.PokemonProvider;
import com.kerhomjarnoin.pokemon.provider.contract.DresseursContract;
import com.kerhomjarnoin.pokemon.data.DresseursSQLiteAdapter;
import com.kerhomjarnoin.pokemon.data.NpcSQLiteAdapter;

/**
 * DresseursProviderAdapterBase.
 */
public abstract class DresseursProviderAdapterBase
                extends ProviderAdapter<Dresseurs> {

    /** TAG for debug purpose. */
    protected static final String TAG = "DresseursProviderAdapter";

    /** DRESSEURS_URI. */
    public      static Uri DRESSEURS_URI;

    /** dresseurs type. */
    protected static final String dresseursType =
            "dresseurs";

    /** DRESSEURS_ALL. */
    protected static final int DRESSEURS_ALL =
            1309304200;
    /** DRESSEURS_ONE. */
    protected static final int DRESSEURS_ONE =
            1309304201;

    /** DRESSEURS_NPC. */
    protected static final int DRESSEURS_NPC =
            1309304202;

    /**
     * Static constructor.
     */
    static {
        DRESSEURS_URI =
                PokemonProvider.generateUri(
                        dresseursType);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                dresseursType,
                DRESSEURS_ALL);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                dresseursType + "/#",
                DRESSEURS_ONE);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                dresseursType + "/#" + "/npc",
                DRESSEURS_NPC);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public DresseursProviderAdapterBase(
            PokemonProviderBase provider) {
        super(
            provider,
            new DresseursSQLiteAdapter(provider.getContext()));

        this.uriIds.add(DRESSEURS_ALL);
        this.uriIds.add(DRESSEURS_ONE);
        this.uriIds.add(DRESSEURS_NPC);
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
            case DRESSEURS_ALL:
                result = collection + "dresseurs";
                break;
            case DRESSEURS_ONE:
                result = single + "dresseurs";
                break;
            case DRESSEURS_NPC:
                result = single + "dresseurs";
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
            case DRESSEURS_ONE:
                String id = uri.getPathSegments().get(1);
                selection = DresseursContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case DRESSEURS_ALL:
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
            case DRESSEURS_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(DresseursContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            DRESSEURS_URI,
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
        android.database.Cursor dresseursCursor;

        switch (matchedUri) {

            case DRESSEURS_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case DRESSEURS_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            case DRESSEURS_NPC:
                dresseursCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (dresseursCursor.getCount() > 0) {
                    dresseursCursor.moveToFirst();
                    int npcId = dresseursCursor.getInt(
                            dresseursCursor.getColumnIndex(
                                    DresseursContract.COL_NPC_ID));

                    NpcSQLiteAdapter npcAdapter = new NpcSQLiteAdapter(this.ctx);
                    npcAdapter.open(this.getDb());
                    result = npcAdapter.query(npcId);
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
            case DRESSEURS_ONE:
                selectionArgs = new String[1];
                selection = DresseursContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case DRESSEURS_ALL:
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
        return DRESSEURS_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = DresseursContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    DresseursContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

