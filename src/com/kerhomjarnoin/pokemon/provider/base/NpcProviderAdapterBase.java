/**************************************************************************
 * NpcProviderAdapterBase.java, pokemon Android
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



import com.kerhomjarnoin.pokemon.entity.Npc;
import com.kerhomjarnoin.pokemon.provider.ProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.PokemonProvider;
import com.kerhomjarnoin.pokemon.provider.contract.NpcContract;
import com.kerhomjarnoin.pokemon.provider.contract.ObjetsContract;
import com.kerhomjarnoin.pokemon.provider.contract.PokemonsContract;
import com.kerhomjarnoin.pokemon.provider.contract.BadgesContract;
import com.kerhomjarnoin.pokemon.data.NpcSQLiteAdapter;
import com.kerhomjarnoin.pokemon.data.ObjetsSQLiteAdapter;
import com.kerhomjarnoin.pokemon.data.PokemonsSQLiteAdapter;
import com.kerhomjarnoin.pokemon.data.NpctoBadgesSQLiteAdapter;
import com.kerhomjarnoin.pokemon.data.BadgesSQLiteAdapter;
import com.kerhomjarnoin.pokemon.data.PositionsSQLiteAdapter;

/**
 * NpcProviderAdapterBase.
 */
public abstract class NpcProviderAdapterBase
                extends ProviderAdapter<Npc> {

    /** TAG for debug purpose. */
    protected static final String TAG = "NpcProviderAdapter";

    /** NPC_URI. */
    public      static Uri NPC_URI;

    /** npc type. */
    protected static final String npcType =
            "npc";

    /** NPC_ALL. */
    protected static final int NPC_ALL =
            78529;
    /** NPC_ONE. */
    protected static final int NPC_ONE =
            78530;

    /** NPC_OBJETS. */
    protected static final int NPC_OBJETS =
            78531;
    /** NPC_POKEMONS. */
    protected static final int NPC_POKEMONS =
            78532;
    /** NPC_BADGES. */
    protected static final int NPC_BADGES =
            78533;
    /** NPC_POSITION. */
    protected static final int NPC_POSITION =
            78534;

    /**
     * Static constructor.
     */
    static {
        NPC_URI =
                PokemonProvider.generateUri(
                        npcType);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                npcType,
                NPC_ALL);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                npcType + "/#",
                NPC_ONE);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                npcType + "/#" + "/objets",
                NPC_OBJETS);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                npcType + "/#" + "/pokemons",
                NPC_POKEMONS);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                npcType + "/#" + "/badges",
                NPC_BADGES);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                npcType + "/#" + "/position",
                NPC_POSITION);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public NpcProviderAdapterBase(
            PokemonProviderBase provider) {
        super(
            provider,
            new NpcSQLiteAdapter(provider.getContext()));

        this.uriIds.add(NPC_ALL);
        this.uriIds.add(NPC_ONE);
        this.uriIds.add(NPC_OBJETS);
        this.uriIds.add(NPC_POKEMONS);
        this.uriIds.add(NPC_BADGES);
        this.uriIds.add(NPC_POSITION);
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
            case NPC_ALL:
                result = collection + "npc";
                break;
            case NPC_ONE:
                result = single + "npc";
                break;
            case NPC_OBJETS:
                result = collection + "npc";
                break;
            case NPC_POKEMONS:
                result = collection + "npc";
                break;
            case NPC_BADGES:
                result = collection + "npc";
                break;
            case NPC_POSITION:
                result = single + "npc";
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
            case NPC_ONE:
                String id = uri.getPathSegments().get(1);
                selection = NpcContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case NPC_ALL:
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
            case NPC_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(NpcContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            NPC_URI,
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
        android.database.Cursor npcCursor;
        int npcId;

        switch (matchedUri) {

            case NPC_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case NPC_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            case NPC_OBJETS:
                npcId = Integer.parseInt(uri.getPathSegments().get(1));
                ObjetsSQLiteAdapter objetsAdapter = new ObjetsSQLiteAdapter(this.ctx);
                objetsAdapter.open(this.getDb());
                result = objetsAdapter.getByNpcobjetsInternal(npcId, ObjetsContract.ALIASED_COLS, selection, selectionArgs, null);
                break;

            case NPC_POKEMONS:
                npcId = Integer.parseInt(uri.getPathSegments().get(1));
                PokemonsSQLiteAdapter pokemonsAdapter = new PokemonsSQLiteAdapter(this.ctx);
                pokemonsAdapter.open(this.getDb());
                result = pokemonsAdapter.getByNpcpokemonsInternal(npcId, PokemonsContract.ALIASED_COLS, selection, selectionArgs, null);
                break;

            case NPC_BADGES:
                npcId = Integer.parseInt(uri.getPathSegments().get(1));
                NpctoBadgesSQLiteAdapter badgesAdapter = new NpctoBadgesSQLiteAdapter(this.ctx);
                badgesAdapter.open(this.getDb());
                result = badgesAdapter.getByNpcInternalId(npcId, BadgesContract.ALIASED_COLS, selection, selectionArgs, null);
                break;

            case NPC_POSITION:
                npcCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (npcCursor.getCount() > 0) {
                    npcCursor.moveToFirst();
                    int positionId = npcCursor.getInt(
                            npcCursor.getColumnIndex(
                                    NpcContract.COL_POSITION_ID));

                    PositionsSQLiteAdapter positionsAdapter = new PositionsSQLiteAdapter(this.ctx);
                    positionsAdapter.open(this.getDb());
                    result = positionsAdapter.query(positionId);
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
            case NPC_ONE:
                selectionArgs = new String[1];
                selection = NpcContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case NPC_ALL:
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
        return NPC_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = NpcContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    NpcContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

