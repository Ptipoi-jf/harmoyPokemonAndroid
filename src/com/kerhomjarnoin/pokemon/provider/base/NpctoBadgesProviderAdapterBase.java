/**************************************************************************
 * NpctoBadgesProviderAdapterBase.java, pokemon Android
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



import com.kerhomjarnoin.pokemon.provider.ProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.PokemonProvider;
import com.kerhomjarnoin.pokemon.provider.contract.NpctoBadgesContract;
import com.kerhomjarnoin.pokemon.data.NpctoBadgesSQLiteAdapter;
import com.kerhomjarnoin.pokemon.data.NpcSQLiteAdapter;
import com.kerhomjarnoin.pokemon.data.BadgesSQLiteAdapter;

/**
 * NpctoBadgesProviderAdapterBase.
 */
public abstract class NpctoBadgesProviderAdapterBase
                extends ProviderAdapter<Void> {

    /** TAG for debug purpose. */
    protected static final String TAG = "NpctoBadgesProviderAdapter";

    /** NPCTOBADGES_URI. */
    public      static Uri NPCTOBADGES_URI;

    /** npctoBadges type. */
    protected static final String npctoBadgesType =
            "npctobadges";

    /** NPCTOBADGES_ALL. */
    protected static final int NPCTOBADGES_ALL =
            206089548;
    /** NPCTOBADGES_ONE. */
    protected static final int NPCTOBADGES_ONE =
            206089549;

    /** NPCTOBADGES_NPCINTERNALID. */
    protected static final int NPCTOBADGES_NPCINTERNALID =
            206089550;
    /** NPCTOBADGES_BADGES. */
    protected static final int NPCTOBADGES_BADGES =
            206089551;

    /**
     * Static constructor.
     */
    static {
        NPCTOBADGES_URI =
                PokemonProvider.generateUri(
                        npctoBadgesType);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                npctoBadgesType,
                NPCTOBADGES_ALL);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                npctoBadgesType + "/#" + "/#",
                NPCTOBADGES_ONE);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                npctoBadgesType + "/#" + "/#" + "/npcinternalid",
                NPCTOBADGES_NPCINTERNALID);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                npctoBadgesType + "/#" + "/#" + "/badges",
                NPCTOBADGES_BADGES);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public NpctoBadgesProviderAdapterBase(
            PokemonProviderBase provider) {
        super(
            provider,
            new NpctoBadgesSQLiteAdapter(provider.getContext()));

        this.uriIds.add(NPCTOBADGES_ALL);
        this.uriIds.add(NPCTOBADGES_ONE);
        this.uriIds.add(NPCTOBADGES_NPCINTERNALID);
        this.uriIds.add(NPCTOBADGES_BADGES);
    }

    @Override
    public String getType(final Uri uri) {
        return null;
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
            case NPCTOBADGES_ONE:
                String NpcInternalIdId = uri.getPathSegments().get(1);
                String badgesId = uri.getPathSegments().get(2);
                selection = NpctoBadgesContract.COL_NPCINTERNALID_ID
                        + " = ?"
                        + " AND "
                        + NpctoBadgesContract.COL_BADGES_ID
                        + " = ?";
                selectionArgs = new String[2];
                selectionArgs[0] = NpcInternalIdId;
                selectionArgs[1] = badgesId;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case NPCTOBADGES_ALL:
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
            case NPCTOBADGES_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(NpctoBadgesContract.COL_NPCINTERNALID_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            NPCTOBADGES_URI,
                            values.getAsString(NpctoBadgesContract.COL_NPCINTERNALID_ID)
                            + "/"
                            + values.getAsString(NpctoBadgesContract.COL_BADGES_ID));
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
        android.database.Cursor npctoBadgesCursor;

        switch (matchedUri) {

            case NPCTOBADGES_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case NPCTOBADGES_ONE:
                result = this.queryById(uri.getPathSegments().get(1),
                        uri.getPathSegments().get(2));
                break;

            case NPCTOBADGES_NPCINTERNALID:
                npctoBadgesCursor = this.queryById(
                        uri.getPathSegments().get(1),
                        uri.getPathSegments().get(2));

                if (npctoBadgesCursor.getCount() > 0) {
                    npctoBadgesCursor.moveToFirst();
                    int NpcInternalIdId = npctoBadgesCursor.getInt(
                            npctoBadgesCursor.getColumnIndex(
                                    NpctoBadgesContract.COL_NPCINTERNALID_ID));

                    NpcSQLiteAdapter npcAdapter = new NpcSQLiteAdapter(this.ctx);
                    npcAdapter.open(this.getDb());
                    result = npcAdapter.query(NpcInternalIdId);
                }
                break;

            case NPCTOBADGES_BADGES:
                npctoBadgesCursor = this.queryById(
                        uri.getPathSegments().get(1),
                        uri.getPathSegments().get(2));

                if (npctoBadgesCursor.getCount() > 0) {
                    npctoBadgesCursor.moveToFirst();
                    int badgesId = npctoBadgesCursor.getInt(
                            npctoBadgesCursor.getColumnIndex(
                                    NpctoBadgesContract.COL_BADGES_ID));

                    BadgesSQLiteAdapter badgesAdapter = new BadgesSQLiteAdapter(this.ctx);
                    badgesAdapter.open(this.getDb());
                    result = badgesAdapter.query(badgesId);
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
            case NPCTOBADGES_ONE:
                selectionArgs = new String[2];
                selection = NpctoBadgesContract.COL_NPCINTERNALID_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                selection += " AND " + NpctoBadgesContract.COL_BADGES_ID + " = ?";
                selectionArgs[1] = uri.getPathSegments().get(2);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case NPCTOBADGES_ALL:
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
        return NPCTOBADGES_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String NpcInternalIdId, String badgesId) {
        android.database.Cursor result = null;
        String selection = NpctoBadgesContract.ALIASED_COL_NPCINTERNALID_ID
                        + " = ?"
                        + " AND "
                        + NpctoBadgesContract.ALIASED_COL_BADGES_ID
                        + " = ?";

        String[] selectionArgs = new String[2];
        selectionArgs[0] = NpcInternalIdId;
        selectionArgs[1] = badgesId;
        
        

        result = this.adapter.query(
                    NpctoBadgesContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

