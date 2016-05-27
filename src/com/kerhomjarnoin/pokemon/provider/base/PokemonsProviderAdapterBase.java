/**************************************************************************
 * PokemonsProviderAdapterBase.java, pokemon Android
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



import com.kerhomjarnoin.pokemon.entity.Pokemons;
import com.kerhomjarnoin.pokemon.provider.ProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.PokemonProvider;
import com.kerhomjarnoin.pokemon.provider.contract.PokemonsContract;
import com.kerhomjarnoin.pokemon.data.PokemonsSQLiteAdapter;
import com.kerhomjarnoin.pokemon.data.TypeDePokemonsSQLiteAdapter;
import com.kerhomjarnoin.pokemon.data.AttaquesSQLiteAdapter;

/**
 * PokemonsProviderAdapterBase.
 */
public abstract class PokemonsProviderAdapterBase
                extends ProviderAdapter<Pokemons> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PokemonsProviderAdapter";

    /** POKEMONS_URI. */
    public      static Uri POKEMONS_URI;

    /** pokemons type. */
    protected static final String pokemonsType =
            "pokemons";

    /** POKEMONS_ALL. */
    protected static final int POKEMONS_ALL =
            579519008;
    /** POKEMONS_ONE. */
    protected static final int POKEMONS_ONE =
            579519009;

    /** POKEMONS_TYPEPOKEMON. */
    protected static final int POKEMONS_TYPEPOKEMON =
            579519010;
    /** POKEMONS_ATTAQUE1. */
    protected static final int POKEMONS_ATTAQUE1 =
            579519011;
    /** POKEMONS_ATTAQUE2. */
    protected static final int POKEMONS_ATTAQUE2 =
            579519012;
    /** POKEMONS_ATTAQUE3. */
    protected static final int POKEMONS_ATTAQUE3 =
            579519013;
    /** POKEMONS_ATTAQUE4. */
    protected static final int POKEMONS_ATTAQUE4 =
            579519014;

    /**
     * Static constructor.
     */
    static {
        POKEMONS_URI =
                PokemonProvider.generateUri(
                        pokemonsType);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                pokemonsType,
                POKEMONS_ALL);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                pokemonsType + "/#",
                POKEMONS_ONE);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                pokemonsType + "/#" + "/typepokemon",
                POKEMONS_TYPEPOKEMON);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                pokemonsType + "/#" + "/attaque1",
                POKEMONS_ATTAQUE1);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                pokemonsType + "/#" + "/attaque2",
                POKEMONS_ATTAQUE2);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                pokemonsType + "/#" + "/attaque3",
                POKEMONS_ATTAQUE3);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                pokemonsType + "/#" + "/attaque4",
                POKEMONS_ATTAQUE4);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public PokemonsProviderAdapterBase(
            PokemonProviderBase provider) {
        super(
            provider,
            new PokemonsSQLiteAdapter(provider.getContext()));

        this.uriIds.add(POKEMONS_ALL);
        this.uriIds.add(POKEMONS_ONE);
        this.uriIds.add(POKEMONS_TYPEPOKEMON);
        this.uriIds.add(POKEMONS_ATTAQUE1);
        this.uriIds.add(POKEMONS_ATTAQUE2);
        this.uriIds.add(POKEMONS_ATTAQUE3);
        this.uriIds.add(POKEMONS_ATTAQUE4);
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
            case POKEMONS_ALL:
                result = collection + "pokemons";
                break;
            case POKEMONS_ONE:
                result = single + "pokemons";
                break;
            case POKEMONS_TYPEPOKEMON:
                result = single + "pokemons";
                break;
            case POKEMONS_ATTAQUE1:
                result = single + "pokemons";
                break;
            case POKEMONS_ATTAQUE2:
                result = single + "pokemons";
                break;
            case POKEMONS_ATTAQUE3:
                result = single + "pokemons";
                break;
            case POKEMONS_ATTAQUE4:
                result = single + "pokemons";
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
            case POKEMONS_ONE:
                String id = uri.getPathSegments().get(1);
                selection = PokemonsContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case POKEMONS_ALL:
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
            case POKEMONS_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(PokemonsContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            POKEMONS_URI,
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
        android.database.Cursor pokemonsCursor;

        switch (matchedUri) {

            case POKEMONS_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case POKEMONS_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            case POKEMONS_TYPEPOKEMON:
                pokemonsCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (pokemonsCursor.getCount() > 0) {
                    pokemonsCursor.moveToFirst();
                    int typePokemonId = pokemonsCursor.getInt(
                            pokemonsCursor.getColumnIndex(
                                    PokemonsContract.COL_TYPEPOKEMON_ID));

                    TypeDePokemonsSQLiteAdapter typeDePokemonsAdapter = new TypeDePokemonsSQLiteAdapter(this.ctx);
                    typeDePokemonsAdapter.open(this.getDb());
                    result = typeDePokemonsAdapter.query(typePokemonId);
                }
                break;

            case POKEMONS_ATTAQUE1:
                pokemonsCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (pokemonsCursor.getCount() > 0) {
                    pokemonsCursor.moveToFirst();
                    int attaque1Id = pokemonsCursor.getInt(
                            pokemonsCursor.getColumnIndex(
                                    PokemonsContract.COL_ATTAQUE1_ID));

                    AttaquesSQLiteAdapter attaquesAdapter = new AttaquesSQLiteAdapter(this.ctx);
                    attaquesAdapter.open(this.getDb());
                    result = attaquesAdapter.query(attaque1Id);
                }
                break;

            case POKEMONS_ATTAQUE2:
                pokemonsCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (pokemonsCursor.getCount() > 0) {
                    pokemonsCursor.moveToFirst();
                    int attaque2Id = pokemonsCursor.getInt(
                            pokemonsCursor.getColumnIndex(
                                    PokemonsContract.COL_ATTAQUE2_ID));

                    AttaquesSQLiteAdapter attaquesAdapter = new AttaquesSQLiteAdapter(this.ctx);
                    attaquesAdapter.open(this.getDb());
                    result = attaquesAdapter.query(attaque2Id);
                }
                break;

            case POKEMONS_ATTAQUE3:
                pokemonsCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (pokemonsCursor.getCount() > 0) {
                    pokemonsCursor.moveToFirst();
                    int attaque3Id = pokemonsCursor.getInt(
                            pokemonsCursor.getColumnIndex(
                                    PokemonsContract.COL_ATTAQUE3_ID));

                    AttaquesSQLiteAdapter attaquesAdapter = new AttaquesSQLiteAdapter(this.ctx);
                    attaquesAdapter.open(this.getDb());
                    result = attaquesAdapter.query(attaque3Id);
                }
                break;

            case POKEMONS_ATTAQUE4:
                pokemonsCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (pokemonsCursor.getCount() > 0) {
                    pokemonsCursor.moveToFirst();
                    int attaque4Id = pokemonsCursor.getInt(
                            pokemonsCursor.getColumnIndex(
                                    PokemonsContract.COL_ATTAQUE4_ID));

                    AttaquesSQLiteAdapter attaquesAdapter = new AttaquesSQLiteAdapter(this.ctx);
                    attaquesAdapter.open(this.getDb());
                    result = attaquesAdapter.query(attaque4Id);
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
            case POKEMONS_ONE:
                selectionArgs = new String[1];
                selection = PokemonsContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case POKEMONS_ALL:
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
        return POKEMONS_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = PokemonsContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    PokemonsContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

