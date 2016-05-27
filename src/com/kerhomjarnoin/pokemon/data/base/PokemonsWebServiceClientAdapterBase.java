/**************************************************************************
 * PokemonsWebServiceClientAdapterBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/

package com.kerhomjarnoin.pokemon.data.base;

import java.util.List;


import org.joda.time.format.DateTimeFormatter;
import com.kerhomjarnoin.pokemon.harmony.util.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.joda.time.format.DateTimeFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;

import com.kerhomjarnoin.pokemon.data.*;
import com.kerhomjarnoin.pokemon.entity.Pokemons;
import com.kerhomjarnoin.pokemon.data.RestClient.Verb;
import com.kerhomjarnoin.pokemon.provider.contract.PokemonsContract;

import com.kerhomjarnoin.pokemon.entity.Npc;
import com.kerhomjarnoin.pokemon.entity.TypeDePokemons;
import com.kerhomjarnoin.pokemon.entity.Attaques;


/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit PokemonsWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class PokemonsWebServiceClientAdapterBase
        extends WebServiceClientAdapter<Pokemons> {
    /** PokemonsWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "PokemonsWSClientAdapter";

    /** JSON Object Pokemons pattern. */
    protected static String JSON_OBJECT_POKEMONS = "Pokemons";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";
    /** JSON_SURNOM attributes. */
    protected static String JSON_SURNOM = "surnom";
    /** JSON_NIVEAU attributes. */
    protected static String JSON_NIVEAU = "niveau";
    /** JSON_CAPTURE attributes. */
    protected static String JSON_CAPTURE = "capture";
    /** JSON_TYPEPOKEMON attributes. */
    protected static String JSON_TYPEPOKEMON = "typePokemon";
    /** JSON_ATTAQUE1 attributes. */
    protected static String JSON_ATTAQUE1 = "attaque1";
    /** JSON_ATTAQUE2 attributes. */
    protected static String JSON_ATTAQUE2 = "attaque2";
    /** JSON_ATTAQUE3 attributes. */
    protected static String JSON_ATTAQUE3 = "attaque3";
    /** JSON_ATTAQUE4 attributes. */
    protected static String JSON_ATTAQUE4 = "attaque4";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** Pokemons REST Columns. */
    public static String[] REST_COLS = new String[]{
            PokemonsContract.COL_ID,
            PokemonsContract.COL_SURNOM,
            PokemonsContract.COL_NIVEAU,
            PokemonsContract.COL_CAPTURE,
            PokemonsContract.COL_TYPEPOKEMON_ID,
            PokemonsContract.COL_ATTAQUE1_ID,
            PokemonsContract.COL_ATTAQUE2_ID,
            PokemonsContract.COL_ATTAQUE3_ID,
            PokemonsContract.COL_ATTAQUE4_ID
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public PokemonsWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public PokemonsWebServiceClientAdapterBase(Context context,
        Integer port) {
        this(context, null, port);
    }

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     * @param host The overriden host
     * @param port The overriden port
     */
    public PokemonsWebServiceClientAdapterBase(Context context,
            String host, Integer port) {
        this(context, host, port, null);
    }

    /**
     * Constructor with overriden port, host and scheme.
     *
     * @param context The context
     * @param host The overriden host
     * @param port The overriden port
     * @param scheme The overriden scheme
     */
    public PokemonsWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme) {
        this(context, host, port, scheme, null);
    }

    /**
     * Constructor with overriden port, host, scheme and prefix.
     *
     * @param context The context
     * @param host The overriden host
     * @param port The overriden port
     * @param scheme The overriden scheme
     * @param prefix The overriden prefix
     */
    public PokemonsWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the Pokemonss in the given list. Uses the route : Pokemons.
     * @param pokemonss : The list in which the Pokemonss will be returned
     * @return The number of Pokemonss returned
     */
    public int getAll(List<Pokemons> pokemonss) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "%s",
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                result = extractItems(json, "Pokemonss", pokemonss);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokemonss = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "pokemons";
    }

    /**
     * Retrieve one Pokemons. Uses the route : Pokemons/%id%.
     * @param pokemons : The Pokemons to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(Pokemons pokemons) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokemons.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                if (extract(json, pokemons)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokemons = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one Pokemons. Uses the route : Pokemons/%id%.
     * @param pokemons : The Pokemons to retrieve (set the  ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public Cursor query(final int id) {
        MatrixCursor result = new MatrixCursor(REST_COLS);
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        id,
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extractCursor(json, result);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                result = null;
            }
        }

        return result;
    }

    /**
     * Update a Pokemons. Uses the route : Pokemons/%id%.
     * @param pokemons : The Pokemons to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(Pokemons pokemons) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokemons.getId(),
                        REST_FORMAT),
                    itemToJson(pokemons));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, pokemons);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a Pokemons. Uses the route : Pokemons/%id%.
     * @param pokemons : The Pokemons to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(Pokemons pokemons) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokemons.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }

    /**
     * Get the Pokemonss associated with a Npc. Uses the route : npc/%Npc_id%/pokemons.
     * @param pokemonss : The list in which the Pokemonss will be returned
     * @param npc : The associated npc
     * @return The number of Pokemonss returned
     */
    public int getByNpcpokemonsInternal(List<Pokemons> pokemonss, Npc npc) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        npc.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                result = this.extractItems(json, "Pokemonss", pokemonss);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokemonss = null;
            }
        }

        return result;
    }

    /**
     * Get the Pokemons associated with a TypeDePokemons. Uses the route : typedepokemons/%TypeDePokemons_id%/pokemons.
     * @param pokemons : The Pokemons that will be returned
     * @param typedepokemons : The associated typedepokemons
     * @return -1 if an error has occurred. 0 if not.
     */
    public int getByTypeDePokemons(Pokemons pokemons, TypeDePokemons typeDePokemons) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        typeDePokemons.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, pokemons);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokemons = null;
            }
        }

        return result;
    }

    /**
     * Get the Pokemons associated with a Attaques. Uses the route : attaques/%Attaques_id%/pokemons.
     * @param pokemons : The Pokemons that will be returned
     * @param attaques : The associated attaques
     * @return -1 if an error has occurred. 0 if not.
     */
    public int getByAttaque1(Pokemons pokemons, Attaques attaques) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        attaques.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, pokemons);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokemons = null;
            }
        }

        return result;
    }

    /**
     * Get the Pokemons associated with a Attaques. Uses the route : attaques/%Attaques_id%/pokemons.
     * @param pokemons : The Pokemons that will be returned
     * @param attaques : The associated attaques
     * @return -1 if an error has occurred. 0 if not.
     */
    public int getByAttaque2(Pokemons pokemons, Attaques attaques) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        attaques.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, pokemons);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokemons = null;
            }
        }

        return result;
    }

    /**
     * Get the Pokemons associated with a Attaques. Uses the route : attaques/%Attaques_id%/pokemons.
     * @param pokemons : The Pokemons that will be returned
     * @param attaques : The associated attaques
     * @return -1 if an error has occurred. 0 if not.
     */
    public int getByAttaque3(Pokemons pokemons, Attaques attaques) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        attaques.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, pokemons);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokemons = null;
            }
        }

        return result;
    }

    /**
     * Get the Pokemons associated with a Attaques. Uses the route : attaques/%Attaques_id%/pokemons.
     * @param pokemons : The Pokemons that will be returned
     * @param attaques : The associated attaques
     * @return -1 if an error has occurred. 0 if not.
     */
    public int getByAttaque4(Pokemons pokemons, Attaques attaques) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        attaques.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, pokemons);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokemons = null;
            }
        }

        return result;
    }


    /**
     * Tests if the json is a valid Pokemons Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(PokemonsWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a Pokemons from a JSONObject describing a Pokemons.
     * @param json The JSONObject describing the Pokemons
     * @param pokemons The returned Pokemons
     * @return true if a Pokemons was found. false if not
     */
    public boolean extract(JSONObject json, Pokemons pokemons) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(PokemonsWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(PokemonsWebServiceClientAdapter.JSON_ID)) {
                    pokemons.setId(
                            json.getInt(PokemonsWebServiceClientAdapter.JSON_ID));
                }

                if (json.has(PokemonsWebServiceClientAdapter.JSON_SURNOM)
                        && !json.isNull(PokemonsWebServiceClientAdapter.JSON_SURNOM)) {
                    pokemons.setSurnom(
                            json.getString(PokemonsWebServiceClientAdapter.JSON_SURNOM));
                }

                if (json.has(PokemonsWebServiceClientAdapter.JSON_NIVEAU)
                        && !json.isNull(PokemonsWebServiceClientAdapter.JSON_NIVEAU)) {
                    pokemons.setNiveau(
                            json.getInt(PokemonsWebServiceClientAdapter.JSON_NIVEAU));
                }

                if (json.has(PokemonsWebServiceClientAdapter.JSON_CAPTURE)
                        && !json.isNull(PokemonsWebServiceClientAdapter.JSON_CAPTURE)) {
                    DateTimeFormatter captureFormatter = DateTimeFormat.forPattern(
                            PokemonsWebServiceClientAdapter.REST_UPDATE_DATE_FORMAT);
                    try {
                        pokemons.setCapture(
                                captureFormatter.withOffsetParsed().parseDateTime(
                                        json.getString(
                                        PokemonsWebServiceClientAdapter.JSON_CAPTURE)));
                    } catch (IllegalArgumentException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }

                if (json.has(PokemonsWebServiceClientAdapter.JSON_TYPEPOKEMON)
                        && !json.isNull(PokemonsWebServiceClientAdapter.JSON_TYPEPOKEMON)) {

                    try {
                        TypeDePokemonsWebServiceClientAdapter typePokemonAdapter =
                                new TypeDePokemonsWebServiceClientAdapter(this.context);
                        TypeDePokemons typePokemon =
                                new TypeDePokemons();

                        if (typePokemonAdapter.extract(
                                json.optJSONObject(
                                        PokemonsWebServiceClientAdapter.JSON_TYPEPOKEMON),
                                        typePokemon)) {
                            pokemons.setTypePokemon(typePokemon);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains TypeDePokemons data");
                    }
                }

                if (json.has(PokemonsWebServiceClientAdapter.JSON_ATTAQUE1)
                        && !json.isNull(PokemonsWebServiceClientAdapter.JSON_ATTAQUE1)) {

                    try {
                        AttaquesWebServiceClientAdapter attaque1Adapter =
                                new AttaquesWebServiceClientAdapter(this.context);
                        Attaques attaque1 =
                                new Attaques();

                        if (attaque1Adapter.extract(
                                json.optJSONObject(
                                        PokemonsWebServiceClientAdapter.JSON_ATTAQUE1),
                                        attaque1)) {
                            pokemons.setAttaque1(attaque1);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains Attaques data");
                    }
                }

                if (json.has(PokemonsWebServiceClientAdapter.JSON_ATTAQUE2)
                        && !json.isNull(PokemonsWebServiceClientAdapter.JSON_ATTAQUE2)) {

                    try {
                        AttaquesWebServiceClientAdapter attaque2Adapter =
                                new AttaquesWebServiceClientAdapter(this.context);
                        Attaques attaque2 =
                                new Attaques();

                        if (attaque2Adapter.extract(
                                json.optJSONObject(
                                        PokemonsWebServiceClientAdapter.JSON_ATTAQUE2),
                                        attaque2)) {
                            pokemons.setAttaque2(attaque2);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains Attaques data");
                    }
                }

                if (json.has(PokemonsWebServiceClientAdapter.JSON_ATTAQUE3)
                        && !json.isNull(PokemonsWebServiceClientAdapter.JSON_ATTAQUE3)) {

                    try {
                        AttaquesWebServiceClientAdapter attaque3Adapter =
                                new AttaquesWebServiceClientAdapter(this.context);
                        Attaques attaque3 =
                                new Attaques();

                        if (attaque3Adapter.extract(
                                json.optJSONObject(
                                        PokemonsWebServiceClientAdapter.JSON_ATTAQUE3),
                                        attaque3)) {
                            pokemons.setAttaque3(attaque3);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains Attaques data");
                    }
                }

                if (json.has(PokemonsWebServiceClientAdapter.JSON_ATTAQUE4)
                        && !json.isNull(PokemonsWebServiceClientAdapter.JSON_ATTAQUE4)) {

                    try {
                        AttaquesWebServiceClientAdapter attaque4Adapter =
                                new AttaquesWebServiceClientAdapter(this.context);
                        Attaques attaque4 =
                                new Attaques();

                        if (attaque4Adapter.extract(
                                json.optJSONObject(
                                        PokemonsWebServiceClientAdapter.JSON_ATTAQUE4),
                                        attaque4)) {
                            pokemons.setAttaque4(attaque4);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains Attaques data");
                    }
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    @Override
    public boolean extractCursor(JSONObject json, MatrixCursor cursor) {
        boolean result = false;
        String id = json.optString(PokemonsWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[9];
                if (json.has(PokemonsWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(PokemonsWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(PokemonsWebServiceClientAdapter.JSON_SURNOM)) {
                    row[1] = json.getString(PokemonsWebServiceClientAdapter.JSON_SURNOM);
                }
                if (json.has(PokemonsWebServiceClientAdapter.JSON_NIVEAU)) {
                    row[2] = json.getString(PokemonsWebServiceClientAdapter.JSON_NIVEAU);
                }
                if (json.has(PokemonsWebServiceClientAdapter.JSON_CAPTURE)) {
                    row[3] = json.getString(PokemonsWebServiceClientAdapter.JSON_CAPTURE);
                }
                if (json.has(PokemonsWebServiceClientAdapter.JSON_TYPEPOKEMON)) {
                    JSONObject typePokemonJson = json.getJSONObject(
                            PokemonsWebServiceClientAdapter.JSON_TYPEPOKEMON);
                    row[4] = typePokemonJson.getString(
                            TypeDePokemonsWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(PokemonsWebServiceClientAdapter.JSON_ATTAQUE1)) {
                    JSONObject attaque1Json = json.getJSONObject(
                            PokemonsWebServiceClientAdapter.JSON_ATTAQUE1);
                    row[5] = attaque1Json.getString(
                            AttaquesWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(PokemonsWebServiceClientAdapter.JSON_ATTAQUE2)) {
                    JSONObject attaque2Json = json.getJSONObject(
                            PokemonsWebServiceClientAdapter.JSON_ATTAQUE2);
                    row[6] = attaque2Json.getString(
                            AttaquesWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(PokemonsWebServiceClientAdapter.JSON_ATTAQUE3)) {
                    JSONObject attaque3Json = json.getJSONObject(
                            PokemonsWebServiceClientAdapter.JSON_ATTAQUE3);
                    row[7] = attaque3Json.getString(
                            AttaquesWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(PokemonsWebServiceClientAdapter.JSON_ATTAQUE4)) {
                    JSONObject attaque4Json = json.getJSONObject(
                            PokemonsWebServiceClientAdapter.JSON_ATTAQUE4);
                    row[8] = attaque4Json.getString(
                            AttaquesWebServiceClientAdapter.JSON_ID);
                }

                cursor.addRow(row);
                result = true;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Convert a Pokemons to a JSONObject.
     * @param pokemons The Pokemons to convert
     * @return The converted Pokemons
     */
    public JSONObject itemToJson(Pokemons pokemons) {
        JSONObject params = new JSONObject();
        try {
            params.put(PokemonsWebServiceClientAdapter.JSON_ID,
                    pokemons.getId());
            params.put(PokemonsWebServiceClientAdapter.JSON_SURNOM,
                    pokemons.getSurnom());
            params.put(PokemonsWebServiceClientAdapter.JSON_NIVEAU,
                    pokemons.getNiveau());

            if (pokemons.getCapture() != null) {
                params.put(PokemonsWebServiceClientAdapter.JSON_CAPTURE,
                        pokemons.getCapture().toString(REST_UPDATE_DATE_FORMAT));
            }

            if (pokemons.getTypePokemon() != null) {
                TypeDePokemonsWebServiceClientAdapter typePokemonAdapter =
                        new TypeDePokemonsWebServiceClientAdapter(this.context);

                params.put(PokemonsWebServiceClientAdapter.JSON_TYPEPOKEMON,
                        typePokemonAdapter.itemIdToJson(pokemons.getTypePokemon()));
            }

            if (pokemons.getAttaque1() != null) {
                AttaquesWebServiceClientAdapter attaque1Adapter =
                        new AttaquesWebServiceClientAdapter(this.context);

                params.put(PokemonsWebServiceClientAdapter.JSON_ATTAQUE1,
                        attaque1Adapter.itemIdToJson(pokemons.getAttaque1()));
            }

            if (pokemons.getAttaque2() != null) {
                AttaquesWebServiceClientAdapter attaque2Adapter =
                        new AttaquesWebServiceClientAdapter(this.context);

                params.put(PokemonsWebServiceClientAdapter.JSON_ATTAQUE2,
                        attaque2Adapter.itemIdToJson(pokemons.getAttaque2()));
            }

            if (pokemons.getAttaque3() != null) {
                AttaquesWebServiceClientAdapter attaque3Adapter =
                        new AttaquesWebServiceClientAdapter(this.context);

                params.put(PokemonsWebServiceClientAdapter.JSON_ATTAQUE3,
                        attaque3Adapter.itemIdToJson(pokemons.getAttaque3()));
            }

            if (pokemons.getAttaque4() != null) {
                AttaquesWebServiceClientAdapter attaque4Adapter =
                        new AttaquesWebServiceClientAdapter(this.context);

                params.put(PokemonsWebServiceClientAdapter.JSON_ATTAQUE4,
                        attaque4Adapter.itemIdToJson(pokemons.getAttaque4()));
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Convert a <T> to a JSONObject.
     * @param item The <T> to convert
     * @return The converted <T>
     */
    public JSONObject itemIdToJson(Pokemons item) {
        JSONObject params = new JSONObject();
        try {
            params.put(PokemonsWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a Pokemons to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(PokemonsWebServiceClientAdapter.JSON_ID,
                    values.get(PokemonsContract.COL_ID));
            params.put(PokemonsWebServiceClientAdapter.JSON_SURNOM,
                    values.get(PokemonsContract.COL_SURNOM));
            params.put(PokemonsWebServiceClientAdapter.JSON_NIVEAU,
                    values.get(PokemonsContract.COL_NIVEAU));
            params.put(PokemonsWebServiceClientAdapter.JSON_CAPTURE,
                    new DateTime(values.get(
                            PokemonsContract.COL_CAPTURE)).toString(REST_UPDATE_DATE_FORMAT));
            TypeDePokemonsWebServiceClientAdapter typePokemonAdapter =
                    new TypeDePokemonsWebServiceClientAdapter(this.context);

            params.put(PokemonsWebServiceClientAdapter.JSON_TYPEPOKEMON,
                    typePokemonAdapter.contentValuesToJson(values));
            AttaquesWebServiceClientAdapter attaque1Adapter =
                    new AttaquesWebServiceClientAdapter(this.context);

            params.put(PokemonsWebServiceClientAdapter.JSON_ATTAQUE1,
                    attaque1Adapter.contentValuesToJson(values));
            AttaquesWebServiceClientAdapter attaque2Adapter =
                    new AttaquesWebServiceClientAdapter(this.context);

            params.put(PokemonsWebServiceClientAdapter.JSON_ATTAQUE2,
                    attaque2Adapter.contentValuesToJson(values));
            AttaquesWebServiceClientAdapter attaque3Adapter =
                    new AttaquesWebServiceClientAdapter(this.context);

            params.put(PokemonsWebServiceClientAdapter.JSON_ATTAQUE3,
                    attaque3Adapter.contentValuesToJson(values));
            AttaquesWebServiceClientAdapter attaque4Adapter =
                    new AttaquesWebServiceClientAdapter(this.context);

            params.put(PokemonsWebServiceClientAdapter.JSON_ATTAQUE4,
                    attaque4Adapter.contentValuesToJson(values));
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Extract a list of <T> from a JSONObject describing an array of <T> given the array name.
     * @param json The JSONObject describing the array of <T>
     * @param items The returned list of <T>
     * @param paramName The name of the array
     * @param limit Limit the number of items to parse
     * @return The number of <T> found in the JSON
     */
    public int extractItems(JSONObject json,
            String paramName,
            List<Pokemons> items,
            int limit) throws JSONException {

        JSONArray itemArray = json.optJSONArray(paramName);

        int result = -1;

        if (itemArray != null) {
            int count = itemArray.length();

            if (limit > 0 && count > limit) {
                count = limit;
            }

            for (int i = 0; i < count; i++) {
                JSONObject jsonItem = itemArray.getJSONObject(i);
                Pokemons item = new Pokemons();
                this.extract(jsonItem, item);
                if (item!=null) {
                    synchronized (items) {
                        items.add(item);
                    }
                }
            }
        }

        if (!json.isNull("Meta")) {
            JSONObject meta = json.optJSONObject("Meta");
            result = meta.optInt("nbt",0);
        }

        return result;
    }

    /**
     * Extract a list of <T> from a JSONObject describing an array of <T> given the array name.
     * @param json The JSONObject describing the array of <T>
     * @param items The returned list of <T>
     * @param paramName The name of the array
     * @return The number of <T> found in the JSON
     */
    public int extractItems(JSONObject json,
            String paramName,
            List<Pokemons> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}
