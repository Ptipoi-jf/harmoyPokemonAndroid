/**************************************************************************
 * PositionsWebServiceClientAdapterBase.java, pokemon Android
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
import com.kerhomjarnoin.pokemon.entity.Positions;
import com.kerhomjarnoin.pokemon.data.RestClient.Verb;
import com.kerhomjarnoin.pokemon.provider.contract.PositionsContract;



/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit PositionsWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class PositionsWebServiceClientAdapterBase
        extends WebServiceClientAdapter<Positions> {
    /** PositionsWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "PositionsWSClientAdapter";

    /** JSON Object Positions pattern. */
    protected static String JSON_OBJECT_POSITIONS = "Positions";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";
    /** JSON_X attributes. */
    protected static String JSON_X = "x";
    /** JSON_Y attributes. */
    protected static String JSON_Y = "y";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** Positions REST Columns. */
    public static String[] REST_COLS = new String[]{
            PositionsContract.COL_ID,
            PositionsContract.COL_X,
            PositionsContract.COL_Y
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public PositionsWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public PositionsWebServiceClientAdapterBase(Context context,
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
    public PositionsWebServiceClientAdapterBase(Context context,
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
    public PositionsWebServiceClientAdapterBase(Context context,
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
    public PositionsWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the Positionss in the given list. Uses the route : Positions.
     * @param positionss : The list in which the Positionss will be returned
     * @return The number of Positionss returned
     */
    public int getAll(List<Positions> positionss) {
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
                result = extractItems(json, "Positionss", positionss);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                positionss = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "positions";
    }

    /**
     * Retrieve one Positions. Uses the route : Positions/%id%.
     * @param positions : The Positions to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(Positions positions) {
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
                if (extract(json, positions)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                positions = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one Positions. Uses the route : Positions/%id%.
     * @param positions : The Positions to retrieve (set the  ID)
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
     * Update a Positions. Uses the route : Positions/%id%.
     * @param positions : The Positions to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(Positions positions) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        positions.getId(),
                        REST_FORMAT),
                    itemToJson(positions));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, positions);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a Positions. Uses the route : Positions/%id%.
     * @param positions : The Positions to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(Positions positions) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        positions.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }


    /**
     * Tests if the json is a valid Positions Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(PositionsWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a Positions from a JSONObject describing a Positions.
     * @param json The JSONObject describing the Positions
     * @param positions The returned Positions
     * @return true if a Positions was found. false if not
     */
    public boolean extract(JSONObject json, Positions positions) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(PositionsWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(PositionsWebServiceClientAdapter.JSON_ID)) {
                    positions.setId(
                            json.getInt(PositionsWebServiceClientAdapter.JSON_ID));
                }

                if (json.has(PositionsWebServiceClientAdapter.JSON_X)
                        && !json.isNull(PositionsWebServiceClientAdapter.JSON_X)) {
                    positions.setX(
                            json.getInt(PositionsWebServiceClientAdapter.JSON_X));
                }

                if (json.has(PositionsWebServiceClientAdapter.JSON_Y)
                        && !json.isNull(PositionsWebServiceClientAdapter.JSON_Y)) {
                    positions.setY(
                            json.getInt(PositionsWebServiceClientAdapter.JSON_Y));
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
        String id = json.optString(PositionsWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[3];
                if (json.has(PositionsWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(PositionsWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(PositionsWebServiceClientAdapter.JSON_X)) {
                    row[1] = json.getString(PositionsWebServiceClientAdapter.JSON_X);
                }
                if (json.has(PositionsWebServiceClientAdapter.JSON_Y)) {
                    row[2] = json.getString(PositionsWebServiceClientAdapter.JSON_Y);
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
     * Convert a Positions to a JSONObject.
     * @param positions The Positions to convert
     * @return The converted Positions
     */
    public JSONObject itemToJson(Positions positions) {
        JSONObject params = new JSONObject();
        try {
            params.put(PositionsWebServiceClientAdapter.JSON_ID,
                    positions.getId());
            params.put(PositionsWebServiceClientAdapter.JSON_X,
                    positions.getX());
            params.put(PositionsWebServiceClientAdapter.JSON_Y,
                    positions.getY());
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
    public JSONObject itemIdToJson(Positions item) {
        JSONObject params = new JSONObject();
        try {
            params.put(PositionsWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a Positions to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(PositionsWebServiceClientAdapter.JSON_ID,
                    values.get(PositionsContract.COL_ID));
            params.put(PositionsWebServiceClientAdapter.JSON_X,
                    values.get(PositionsContract.COL_X));
            params.put(PositionsWebServiceClientAdapter.JSON_Y,
                    values.get(PositionsContract.COL_Y));
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
            List<Positions> items,
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
                Positions item = new Positions();
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
            List<Positions> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}
