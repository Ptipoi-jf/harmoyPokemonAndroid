/**************************************************************************
 * ArenesWebServiceClientAdapterBase.java, pokemon Android
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
import com.kerhomjarnoin.pokemon.entity.Arenes;
import com.kerhomjarnoin.pokemon.data.RestClient.Verb;
import com.kerhomjarnoin.pokemon.provider.contract.ArenesContract;

import com.kerhomjarnoin.pokemon.entity.Positions;
import com.kerhomjarnoin.pokemon.entity.Badges;


/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit ArenesWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class ArenesWebServiceClientAdapterBase
        extends WebServiceClientAdapter<Arenes> {
    /** ArenesWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "ArenesWSClientAdapter";

    /** JSON Object Arenes pattern. */
    protected static String JSON_OBJECT_ARENES = "Arenes";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";
    /** JSON_NOM attributes. */
    protected static String JSON_NOM = "nom";
    /** JSON_POSITION attributes. */
    protected static String JSON_POSITION = "position";
    /** JSON_BADGE attributes. */
    protected static String JSON_BADGE = "badge";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** Arenes REST Columns. */
    public static String[] REST_COLS = new String[]{
            ArenesContract.COL_ID,
            ArenesContract.COL_NOM,
            ArenesContract.COL_POSITION_ID,
            ArenesContract.COL_BADGE_ID
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public ArenesWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public ArenesWebServiceClientAdapterBase(Context context,
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
    public ArenesWebServiceClientAdapterBase(Context context,
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
    public ArenesWebServiceClientAdapterBase(Context context,
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
    public ArenesWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the Areness in the given list. Uses the route : Arenes.
     * @param areness : The list in which the Areness will be returned
     * @return The number of Areness returned
     */
    public int getAll(List<Arenes> areness) {
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
                result = extractItems(json, "Areness", areness);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                areness = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "arenes";
    }

    /**
     * Retrieve one Arenes. Uses the route : Arenes/%id%.
     * @param arenes : The Arenes to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(Arenes arenes) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        arenes.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                if (extract(json, arenes)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                arenes = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one Arenes. Uses the route : Arenes/%id%.
     * @param arenes : The Arenes to retrieve (set the  ID)
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
     * Update a Arenes. Uses the route : Arenes/%id%.
     * @param arenes : The Arenes to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(Arenes arenes) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        arenes.getId(),
                        REST_FORMAT),
                    itemToJson(arenes));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, arenes);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a Arenes. Uses the route : Arenes/%id%.
     * @param arenes : The Arenes to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(Arenes arenes) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        arenes.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }

    /**
     * Get the Arenes associated with a Positions. Uses the route : positions/%Positions_id%/arenes.
     * @param arenes : The Arenes that will be returned
     * @param positions : The associated positions
     * @return -1 if an error has occurred. 0 if not.
     */
    public int getByPositions(Arenes arenes, Positions positions) {
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
                this.extract(json, arenes);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                arenes = null;
            }
        }

        return result;
    }

    /**
     * Get the Arenes associated with a Badges. Uses the route : badges/%Badges_id%/arenes.
     * @param arenes : The Arenes that will be returned
     * @param badges : The associated badges
     * @return -1 if an error has occurred. 0 if not.
     */
    public int getByBadges(Arenes arenes, Badges badges) {
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
                this.extract(json, arenes);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                arenes = null;
            }
        }

        return result;
    }


    /**
     * Tests if the json is a valid Arenes Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(ArenesWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a Arenes from a JSONObject describing a Arenes.
     * @param json The JSONObject describing the Arenes
     * @param arenes The returned Arenes
     * @return true if a Arenes was found. false if not
     */
    public boolean extract(JSONObject json, Arenes arenes) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(ArenesWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(ArenesWebServiceClientAdapter.JSON_ID)) {
                    arenes.setId(
                            json.getInt(ArenesWebServiceClientAdapter.JSON_ID));
                }

                if (json.has(ArenesWebServiceClientAdapter.JSON_NOM)
                        && !json.isNull(ArenesWebServiceClientAdapter.JSON_NOM)) {
                    arenes.setNom(
                            json.getString(ArenesWebServiceClientAdapter.JSON_NOM));
                }

                if (json.has(ArenesWebServiceClientAdapter.JSON_POSITION)
                        && !json.isNull(ArenesWebServiceClientAdapter.JSON_POSITION)) {

                    try {
                        PositionsWebServiceClientAdapter positionAdapter =
                                new PositionsWebServiceClientAdapter(this.context);
                        Positions position =
                                new Positions();

                        if (positionAdapter.extract(
                                json.optJSONObject(
                                        ArenesWebServiceClientAdapter.JSON_POSITION),
                                        position)) {
                            arenes.setPosition(position);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains Positions data");
                    }
                }

                if (json.has(ArenesWebServiceClientAdapter.JSON_BADGE)
                        && !json.isNull(ArenesWebServiceClientAdapter.JSON_BADGE)) {

                    try {
                        BadgesWebServiceClientAdapter badgeAdapter =
                                new BadgesWebServiceClientAdapter(this.context);
                        Badges badge =
                                new Badges();

                        if (badgeAdapter.extract(
                                json.optJSONObject(
                                        ArenesWebServiceClientAdapter.JSON_BADGE),
                                        badge)) {
                            arenes.setBadge(badge);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains Badges data");
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
        String id = json.optString(ArenesWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[4];
                if (json.has(ArenesWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(ArenesWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(ArenesWebServiceClientAdapter.JSON_NOM)) {
                    row[1] = json.getString(ArenesWebServiceClientAdapter.JSON_NOM);
                }
                if (json.has(ArenesWebServiceClientAdapter.JSON_POSITION)) {
                    JSONObject positionJson = json.getJSONObject(
                            ArenesWebServiceClientAdapter.JSON_POSITION);
                    row[2] = positionJson.getString(
                            PositionsWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(ArenesWebServiceClientAdapter.JSON_BADGE)) {
                    JSONObject badgeJson = json.getJSONObject(
                            ArenesWebServiceClientAdapter.JSON_BADGE);
                    row[3] = badgeJson.getString(
                            BadgesWebServiceClientAdapter.JSON_ID);
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
     * Convert a Arenes to a JSONObject.
     * @param arenes The Arenes to convert
     * @return The converted Arenes
     */
    public JSONObject itemToJson(Arenes arenes) {
        JSONObject params = new JSONObject();
        try {
            params.put(ArenesWebServiceClientAdapter.JSON_ID,
                    arenes.getId());
            params.put(ArenesWebServiceClientAdapter.JSON_NOM,
                    arenes.getNom());

            if (arenes.getPosition() != null) {
                PositionsWebServiceClientAdapter positionAdapter =
                        new PositionsWebServiceClientAdapter(this.context);

                params.put(ArenesWebServiceClientAdapter.JSON_POSITION,
                        positionAdapter.itemIdToJson(arenes.getPosition()));
            }

            if (arenes.getBadge() != null) {
                BadgesWebServiceClientAdapter badgeAdapter =
                        new BadgesWebServiceClientAdapter(this.context);

                params.put(ArenesWebServiceClientAdapter.JSON_BADGE,
                        badgeAdapter.itemIdToJson(arenes.getBadge()));
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
    public JSONObject itemIdToJson(Arenes item) {
        JSONObject params = new JSONObject();
        try {
            params.put(ArenesWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a Arenes to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(ArenesWebServiceClientAdapter.JSON_ID,
                    values.get(ArenesContract.COL_ID));
            params.put(ArenesWebServiceClientAdapter.JSON_NOM,
                    values.get(ArenesContract.COL_NOM));
            PositionsWebServiceClientAdapter positionAdapter =
                    new PositionsWebServiceClientAdapter(this.context);

            params.put(ArenesWebServiceClientAdapter.JSON_POSITION,
                    positionAdapter.contentValuesToJson(values));
            BadgesWebServiceClientAdapter badgeAdapter =
                    new BadgesWebServiceClientAdapter(this.context);

            params.put(ArenesWebServiceClientAdapter.JSON_BADGE,
                    badgeAdapter.contentValuesToJson(values));
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
            List<Arenes> items,
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
                Arenes item = new Arenes();
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
            List<Arenes> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}
