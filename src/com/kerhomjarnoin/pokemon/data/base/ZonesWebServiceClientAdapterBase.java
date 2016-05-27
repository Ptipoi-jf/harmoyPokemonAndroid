/**************************************************************************
 * ZonesWebServiceClientAdapterBase.java, pokemon Android
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
import com.kerhomjarnoin.pokemon.entity.Zones;
import com.kerhomjarnoin.pokemon.data.RestClient.Verb;
import com.kerhomjarnoin.pokemon.provider.contract.ZonesContract;



/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit ZonesWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class ZonesWebServiceClientAdapterBase
        extends WebServiceClientAdapter<Zones> {
    /** ZonesWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "ZonesWSClientAdapter";

    /** JSON Object Zones pattern. */
    protected static String JSON_OBJECT_ZONES = "Zones";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";
    /** JSON_NOM attributes. */
    protected static String JSON_NOM = "nom";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** Zones REST Columns. */
    public static String[] REST_COLS = new String[]{
            ZonesContract.COL_ID,
            ZonesContract.COL_NOM
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public ZonesWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public ZonesWebServiceClientAdapterBase(Context context,
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
    public ZonesWebServiceClientAdapterBase(Context context,
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
    public ZonesWebServiceClientAdapterBase(Context context,
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
    public ZonesWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the Zoness in the given list. Uses the route : Zones.
     * @param zoness : The list in which the Zoness will be returned
     * @return The number of Zoness returned
     */
    public int getAll(List<Zones> zoness) {
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
                result = extractItems(json, "Zoness", zoness);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                zoness = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "zones";
    }

    /**
     * Retrieve one Zones. Uses the route : Zones/%id%.
     * @param zones : The Zones to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(Zones zones) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        zones.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                if (extract(json, zones)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                zones = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one Zones. Uses the route : Zones/%id%.
     * @param zones : The Zones to retrieve (set the  ID)
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
     * Update a Zones. Uses the route : Zones/%id%.
     * @param zones : The Zones to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(Zones zones) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        zones.getId(),
                        REST_FORMAT),
                    itemToJson(zones));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, zones);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a Zones. Uses the route : Zones/%id%.
     * @param zones : The Zones to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(Zones zones) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        zones.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }


    /**
     * Tests if the json is a valid Zones Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(ZonesWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a Zones from a JSONObject describing a Zones.
     * @param json The JSONObject describing the Zones
     * @param zones The returned Zones
     * @return true if a Zones was found. false if not
     */
    public boolean extract(JSONObject json, Zones zones) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(ZonesWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(ZonesWebServiceClientAdapter.JSON_ID)) {
                    zones.setId(
                            json.getInt(ZonesWebServiceClientAdapter.JSON_ID));
                }

                if (json.has(ZonesWebServiceClientAdapter.JSON_NOM)
                        && !json.isNull(ZonesWebServiceClientAdapter.JSON_NOM)) {
                    zones.setNom(
                            json.getString(ZonesWebServiceClientAdapter.JSON_NOM));
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
        String id = json.optString(ZonesWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[2];
                if (json.has(ZonesWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(ZonesWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(ZonesWebServiceClientAdapter.JSON_NOM)) {
                    row[1] = json.getString(ZonesWebServiceClientAdapter.JSON_NOM);
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
     * Convert a Zones to a JSONObject.
     * @param zones The Zones to convert
     * @return The converted Zones
     */
    public JSONObject itemToJson(Zones zones) {
        JSONObject params = new JSONObject();
        try {
            params.put(ZonesWebServiceClientAdapter.JSON_ID,
                    zones.getId());
            params.put(ZonesWebServiceClientAdapter.JSON_NOM,
                    zones.getNom());
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
    public JSONObject itemIdToJson(Zones item) {
        JSONObject params = new JSONObject();
        try {
            params.put(ZonesWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a Zones to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(ZonesWebServiceClientAdapter.JSON_ID,
                    values.get(ZonesContract.COL_ID));
            params.put(ZonesWebServiceClientAdapter.JSON_NOM,
                    values.get(ZonesContract.COL_NOM));
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
            List<Zones> items,
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
                Zones item = new Zones();
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
            List<Zones> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}
