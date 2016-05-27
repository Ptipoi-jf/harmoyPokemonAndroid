/**************************************************************************
 * DresseursWebServiceClientAdapterBase.java, pokemon Android
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
import com.kerhomjarnoin.pokemon.entity.Dresseurs;
import com.kerhomjarnoin.pokemon.data.RestClient.Verb;
import com.kerhomjarnoin.pokemon.provider.contract.DresseursContract;

import com.kerhomjarnoin.pokemon.entity.Npc;


/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit DresseursWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class DresseursWebServiceClientAdapterBase
        extends WebServiceClientAdapter<Dresseurs> {
    /** DresseursWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "DresseursWSClientAdapter";

    /** JSON Object Dresseurs pattern. */
    protected static String JSON_OBJECT_DRESSEURS = "Dresseurs";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";
    /** JSON_NOM attributes. */
    protected static String JSON_NOM = "nom";
    /** JSON_NPC attributes. */
    protected static String JSON_NPC = "npc";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** Dresseurs REST Columns. */
    public static String[] REST_COLS = new String[]{
            DresseursContract.COL_ID,
            DresseursContract.COL_NOM,
            DresseursContract.COL_NPC_ID
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public DresseursWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public DresseursWebServiceClientAdapterBase(Context context,
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
    public DresseursWebServiceClientAdapterBase(Context context,
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
    public DresseursWebServiceClientAdapterBase(Context context,
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
    public DresseursWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the Dresseurss in the given list. Uses the route : Dresseurs.
     * @param dresseurss : The list in which the Dresseurss will be returned
     * @return The number of Dresseurss returned
     */
    public int getAll(List<Dresseurs> dresseurss) {
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
                result = extractItems(json, "Dresseurss", dresseurss);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                dresseurss = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "dresseurs";
    }

    /**
     * Retrieve one Dresseurs. Uses the route : Dresseurs/%id%.
     * @param dresseurs : The Dresseurs to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(Dresseurs dresseurs) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        dresseurs.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                if (extract(json, dresseurs)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                dresseurs = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one Dresseurs. Uses the route : Dresseurs/%id%.
     * @param dresseurs : The Dresseurs to retrieve (set the  ID)
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
     * Update a Dresseurs. Uses the route : Dresseurs/%id%.
     * @param dresseurs : The Dresseurs to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(Dresseurs dresseurs) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        dresseurs.getId(),
                        REST_FORMAT),
                    itemToJson(dresseurs));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, dresseurs);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a Dresseurs. Uses the route : Dresseurs/%id%.
     * @param dresseurs : The Dresseurs to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(Dresseurs dresseurs) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        dresseurs.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }

    /**
     * Get the Dresseurs associated with a Npc. Uses the route : npc/%Npc_id%/dresseurs.
     * @param dresseurs : The Dresseurs that will be returned
     * @param npc : The associated npc
     * @return -1 if an error has occurred. 0 if not.
     */
    public int getByNpc(Dresseurs dresseurs, Npc npc) {
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
                this.extract(json, dresseurs);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                dresseurs = null;
            }
        }

        return result;
    }


    /**
     * Tests if the json is a valid Dresseurs Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(DresseursWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a Dresseurs from a JSONObject describing a Dresseurs.
     * @param json The JSONObject describing the Dresseurs
     * @param dresseurs The returned Dresseurs
     * @return true if a Dresseurs was found. false if not
     */
    public boolean extract(JSONObject json, Dresseurs dresseurs) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(DresseursWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(DresseursWebServiceClientAdapter.JSON_ID)) {
                    dresseurs.setId(
                            json.getInt(DresseursWebServiceClientAdapter.JSON_ID));
                }

                if (json.has(DresseursWebServiceClientAdapter.JSON_NOM)
                        && !json.isNull(DresseursWebServiceClientAdapter.JSON_NOM)) {
                    dresseurs.setNom(
                            json.getString(DresseursWebServiceClientAdapter.JSON_NOM));
                }

                if (json.has(DresseursWebServiceClientAdapter.JSON_NPC)
                        && !json.isNull(DresseursWebServiceClientAdapter.JSON_NPC)) {

                    try {
                        NpcWebServiceClientAdapter npcAdapter =
                                new NpcWebServiceClientAdapter(this.context);
                        Npc npc =
                                new Npc();

                        if (npcAdapter.extract(
                                json.optJSONObject(
                                        DresseursWebServiceClientAdapter.JSON_NPC),
                                        npc)) {
                            dresseurs.setNpc(npc);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains Npc data");
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
        String id = json.optString(DresseursWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[3];
                if (json.has(DresseursWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(DresseursWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(DresseursWebServiceClientAdapter.JSON_NOM)) {
                    row[1] = json.getString(DresseursWebServiceClientAdapter.JSON_NOM);
                }
                if (json.has(DresseursWebServiceClientAdapter.JSON_NPC)) {
                    JSONObject npcJson = json.getJSONObject(
                            DresseursWebServiceClientAdapter.JSON_NPC);
                    row[2] = npcJson.getString(
                            NpcWebServiceClientAdapter.JSON_ID);
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
     * Convert a Dresseurs to a JSONObject.
     * @param dresseurs The Dresseurs to convert
     * @return The converted Dresseurs
     */
    public JSONObject itemToJson(Dresseurs dresseurs) {
        JSONObject params = new JSONObject();
        try {
            params.put(DresseursWebServiceClientAdapter.JSON_ID,
                    dresseurs.getId());
            params.put(DresseursWebServiceClientAdapter.JSON_NOM,
                    dresseurs.getNom());

            if (dresseurs.getNpc() != null) {
                NpcWebServiceClientAdapter npcAdapter =
                        new NpcWebServiceClientAdapter(this.context);

                params.put(DresseursWebServiceClientAdapter.JSON_NPC,
                        npcAdapter.itemIdToJson(dresseurs.getNpc()));
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
    public JSONObject itemIdToJson(Dresseurs item) {
        JSONObject params = new JSONObject();
        try {
            params.put(DresseursWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a Dresseurs to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(DresseursWebServiceClientAdapter.JSON_ID,
                    values.get(DresseursContract.COL_ID));
            params.put(DresseursWebServiceClientAdapter.JSON_NOM,
                    values.get(DresseursContract.COL_NOM));
            NpcWebServiceClientAdapter npcAdapter =
                    new NpcWebServiceClientAdapter(this.context);

            params.put(DresseursWebServiceClientAdapter.JSON_NPC,
                    npcAdapter.contentValuesToJson(values));
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
            List<Dresseurs> items,
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
                Dresseurs item = new Dresseurs();
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
            List<Dresseurs> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}
