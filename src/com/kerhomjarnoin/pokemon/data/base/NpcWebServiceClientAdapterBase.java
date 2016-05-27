/**************************************************************************
 * NpcWebServiceClientAdapterBase.java, pokemon Android
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
import java.util.ArrayList;

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
import com.kerhomjarnoin.pokemon.entity.Npc;
import com.kerhomjarnoin.pokemon.data.RestClient.Verb;
import com.kerhomjarnoin.pokemon.provider.contract.NpcContract;

import com.kerhomjarnoin.pokemon.entity.Objets;
import com.kerhomjarnoin.pokemon.entity.Pokemons;
import com.kerhomjarnoin.pokemon.entity.Badges;
import com.kerhomjarnoin.pokemon.entity.Positions;


/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit NpcWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class NpcWebServiceClientAdapterBase
        extends WebServiceClientAdapter<Npc> {
    /** NpcWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "NpcWSClientAdapter";

    /** JSON Object Npc pattern. */
    protected static String JSON_OBJECT_NPC = "Npc";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";
    /** JSON_NOM attributes. */
    protected static String JSON_NOM = "nom";
    /** JSON_PROFESSION attributes. */
    protected static String JSON_PROFESSION = "profession";
    /** JSON_TEXTE attributes. */
    protected static String JSON_TEXTE = "texte";
    /** JSON_OBJETS attributes. */
    protected static String JSON_OBJETS = "objets";
    /** JSON_POKEMONS attributes. */
    protected static String JSON_POKEMONS = "pokemons";
    /** JSON_BADGES attributes. */
    protected static String JSON_BADGES = "badges";
    /** JSON_POSITION attributes. */
    protected static String JSON_POSITION = "position";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** Npc REST Columns. */
    public static String[] REST_COLS = new String[]{
            NpcContract.COL_ID,
            NpcContract.COL_NOM,
            NpcContract.COL_PROFESSION,
            NpcContract.COL_TEXTE,
            NpcContract.COL_POSITION_ID
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public NpcWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public NpcWebServiceClientAdapterBase(Context context,
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
    public NpcWebServiceClientAdapterBase(Context context,
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
    public NpcWebServiceClientAdapterBase(Context context,
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
    public NpcWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the Npcs in the given list. Uses the route : Npc.
     * @param npcs : The list in which the Npcs will be returned
     * @return The number of Npcs returned
     */
    public int getAll(List<Npc> npcs) {
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
                result = extractItems(json, "Npcs", npcs);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                npcs = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "npc";
    }

    /**
     * Retrieve one Npc. Uses the route : Npc/%id%.
     * @param npc : The Npc to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(Npc npc) {
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
                if (extract(json, npc)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                npc = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one Npc. Uses the route : Npc/%id%.
     * @param npc : The Npc to retrieve (set the  ID)
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
     * Update a Npc. Uses the route : Npc/%id%.
     * @param npc : The Npc to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(Npc npc) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        npc.getId(),
                        REST_FORMAT),
                    itemToJson(npc));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, npc);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a Npc. Uses the route : Npc/%id%.
     * @param npc : The Npc to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(Npc npc) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        npc.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }



    /**
     * Get the Npcs associated with a Badges. Uses the route : badges/%Badges_id%/npc.
     * @param npcs : The list in which the Npcs will be returned
     * @param badges : The associated badges
     * @return The number of Npcs returned
     */
    public int getByBadges(List<Npc> npcs, Badges badges) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        badges.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                result = this.extractItems(json, "Npcs", npcs);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                npcs = null;
            }
        }

        return result;
    }

    /**
     * Get the Npc associated with a Positions. Uses the route : positions/%Positions_id%/npc.
     * @param npc : The Npc that will be returned
     * @param positions : The associated positions
     * @return -1 if an error has occurred. 0 if not.
     */
    public int getByPositions(Npc npc, Positions positions) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        positions.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, npc);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                npc = null;
            }
        }

        return result;
    }


    /**
     * Tests if the json is a valid Npc Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(NpcWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a Npc from a JSONObject describing a Npc.
     * @param json The JSONObject describing the Npc
     * @param npc The returned Npc
     * @return true if a Npc was found. false if not
     */
    public boolean extract(JSONObject json, Npc npc) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(NpcWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(NpcWebServiceClientAdapter.JSON_ID)) {
                    npc.setId(
                            json.getInt(NpcWebServiceClientAdapter.JSON_ID));
                }

                if (json.has(NpcWebServiceClientAdapter.JSON_NOM)
                        && !json.isNull(NpcWebServiceClientAdapter.JSON_NOM)) {
                    npc.setNom(
                            json.getString(NpcWebServiceClientAdapter.JSON_NOM));
                }

                if (json.has(NpcWebServiceClientAdapter.JSON_PROFESSION)
                        && !json.isNull(NpcWebServiceClientAdapter.JSON_PROFESSION)) {
                    npc.setProfession(
                            json.getString(NpcWebServiceClientAdapter.JSON_PROFESSION));
                }

                if (json.has(NpcWebServiceClientAdapter.JSON_TEXTE)
                        && !json.isNull(NpcWebServiceClientAdapter.JSON_TEXTE)) {
                    npc.setTexte(
                            json.getString(NpcWebServiceClientAdapter.JSON_TEXTE));
                }

                if (json.has(NpcWebServiceClientAdapter.JSON_OBJETS)
                        && !json.isNull(NpcWebServiceClientAdapter.JSON_OBJETS)) {
                    ArrayList<Objets> objets =
                            new ArrayList<Objets>();
                    ObjetsWebServiceClientAdapter objetsAdapter =
                            new ObjetsWebServiceClientAdapter(this.context);

                    try {
                        //.optJSONObject(NpcWebServiceClientAdapter.JSON_OBJETS);
                        objetsAdapter.extractItems(
                                json, NpcWebServiceClientAdapter.JSON_OBJETS,
                                objets);
                        npc.setObjets(objets);
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }

                if (json.has(NpcWebServiceClientAdapter.JSON_POKEMONS)
                        && !json.isNull(NpcWebServiceClientAdapter.JSON_POKEMONS)) {
                    ArrayList<Pokemons> pokemons =
                            new ArrayList<Pokemons>();
                    PokemonsWebServiceClientAdapter pokemonsAdapter =
                            new PokemonsWebServiceClientAdapter(this.context);

                    try {
                        //.optJSONObject(NpcWebServiceClientAdapter.JSON_POKEMONS);
                        pokemonsAdapter.extractItems(
                                json, NpcWebServiceClientAdapter.JSON_POKEMONS,
                                pokemons);
                        npc.setPokemons(pokemons);
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }

                if (json.has(NpcWebServiceClientAdapter.JSON_BADGES)
                        && !json.isNull(NpcWebServiceClientAdapter.JSON_BADGES)) {
                    ArrayList<Badges> badges =
                            new ArrayList<Badges>();
                    BadgesWebServiceClientAdapter badgesAdapter =
                            new BadgesWebServiceClientAdapter(this.context);

                    try {
                        //.optJSONObject(NpcWebServiceClientAdapter.JSON_BADGES);
                        badgesAdapter.extractItems(
                                json, NpcWebServiceClientAdapter.JSON_BADGES,
                                badges);
                        npc.setBadges(badges);
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }

                if (json.has(NpcWebServiceClientAdapter.JSON_POSITION)
                        && !json.isNull(NpcWebServiceClientAdapter.JSON_POSITION)) {

                    try {
                        PositionsWebServiceClientAdapter positionAdapter =
                                new PositionsWebServiceClientAdapter(this.context);
                        Positions position =
                                new Positions();

                        if (positionAdapter.extract(
                                json.optJSONObject(
                                        NpcWebServiceClientAdapter.JSON_POSITION),
                                        position)) {
                            npc.setPosition(position);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains Positions data");
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
        String id = json.optString(NpcWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[5];
                if (json.has(NpcWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(NpcWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(NpcWebServiceClientAdapter.JSON_NOM)) {
                    row[1] = json.getString(NpcWebServiceClientAdapter.JSON_NOM);
                }
                if (json.has(NpcWebServiceClientAdapter.JSON_PROFESSION)) {
                    row[2] = json.getString(NpcWebServiceClientAdapter.JSON_PROFESSION);
                }
                if (json.has(NpcWebServiceClientAdapter.JSON_TEXTE)) {
                    row[3] = json.getString(NpcWebServiceClientAdapter.JSON_TEXTE);
                }
                if (json.has(NpcWebServiceClientAdapter.JSON_POSITION)) {
                    JSONObject positionJson = json.getJSONObject(
                            NpcWebServiceClientAdapter.JSON_POSITION);
                    row[4] = positionJson.getString(
                            PositionsWebServiceClientAdapter.JSON_ID);
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
     * Convert a Npc to a JSONObject.
     * @param npc The Npc to convert
     * @return The converted Npc
     */
    public JSONObject itemToJson(Npc npc) {
        JSONObject params = new JSONObject();
        try {
            params.put(NpcWebServiceClientAdapter.JSON_ID,
                    npc.getId());
            params.put(NpcWebServiceClientAdapter.JSON_NOM,
                    npc.getNom());
            params.put(NpcWebServiceClientAdapter.JSON_PROFESSION,
                    npc.getProfession());
            params.put(NpcWebServiceClientAdapter.JSON_TEXTE,
                    npc.getTexte());

            if (npc.getObjets() != null) {
                ObjetsWebServiceClientAdapter objetsAdapter =
                        new ObjetsWebServiceClientAdapter(this.context);

                params.put(NpcWebServiceClientAdapter.JSON_OBJETS,
                        objetsAdapter.itemsIdToJson(npc.getObjets()));
            }

            if (npc.getPokemons() != null) {
                PokemonsWebServiceClientAdapter pokemonsAdapter =
                        new PokemonsWebServiceClientAdapter(this.context);

                params.put(NpcWebServiceClientAdapter.JSON_POKEMONS,
                        pokemonsAdapter.itemsIdToJson(npc.getPokemons()));
            }

            if (npc.getBadges() != null) {
                BadgesWebServiceClientAdapter badgesAdapter =
                        new BadgesWebServiceClientAdapter(this.context);

                params.put(NpcWebServiceClientAdapter.JSON_BADGES,
                        badgesAdapter.itemsIdToJson(npc.getBadges()));
            }

            if (npc.getPosition() != null) {
                PositionsWebServiceClientAdapter positionAdapter =
                        new PositionsWebServiceClientAdapter(this.context);

                params.put(NpcWebServiceClientAdapter.JSON_POSITION,
                        positionAdapter.itemIdToJson(npc.getPosition()));
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
    public JSONObject itemIdToJson(Npc item) {
        JSONObject params = new JSONObject();
        try {
            params.put(NpcWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a Npc to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(NpcWebServiceClientAdapter.JSON_ID,
                    values.get(NpcContract.COL_ID));
            params.put(NpcWebServiceClientAdapter.JSON_NOM,
                    values.get(NpcContract.COL_NOM));
            params.put(NpcWebServiceClientAdapter.JSON_PROFESSION,
                    values.get(NpcContract.COL_PROFESSION));
            params.put(NpcWebServiceClientAdapter.JSON_TEXTE,
                    values.get(NpcContract.COL_TEXTE));
            PositionsWebServiceClientAdapter positionAdapter =
                    new PositionsWebServiceClientAdapter(this.context);

            params.put(NpcWebServiceClientAdapter.JSON_POSITION,
                    positionAdapter.contentValuesToJson(values));
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
            List<Npc> items,
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
                Npc item = new Npc();
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
            List<Npc> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}
