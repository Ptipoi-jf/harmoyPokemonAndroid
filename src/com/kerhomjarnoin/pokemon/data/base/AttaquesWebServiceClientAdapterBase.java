/**************************************************************************
 * AttaquesWebServiceClientAdapterBase.java, pokemon Android
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
import com.kerhomjarnoin.pokemon.entity.Attaques;
import com.kerhomjarnoin.pokemon.data.RestClient.Verb;
import com.kerhomjarnoin.pokemon.provider.contract.AttaquesContract;

import com.kerhomjarnoin.pokemon.entity.Types;


/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit AttaquesWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class AttaquesWebServiceClientAdapterBase
        extends WebServiceClientAdapter<Attaques> {
    /** AttaquesWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "AttaquesWSClientAdapter";

    /** JSON Object Attaques pattern. */
    protected static String JSON_OBJECT_ATTAQUES = "Attaques";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";
    /** JSON_NOM attributes. */
    protected static String JSON_NOM = "nom";
    /** JSON_PUISSANCE attributes. */
    protected static String JSON_PUISSANCE = "puissance";
    /** JSON_PRECIS attributes. */
    protected static String JSON_PRECIS = "precis";
    /** JSON_TYPE attributes. */
    protected static String JSON_TYPE = "type";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** Attaques REST Columns. */
    public static String[] REST_COLS = new String[]{
            AttaquesContract.COL_ID,
            AttaquesContract.COL_NOM,
            AttaquesContract.COL_PUISSANCE,
            AttaquesContract.COL_PRECIS,
            AttaquesContract.COL_TYPE_ID
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public AttaquesWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public AttaquesWebServiceClientAdapterBase(Context context,
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
    public AttaquesWebServiceClientAdapterBase(Context context,
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
    public AttaquesWebServiceClientAdapterBase(Context context,
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
    public AttaquesWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the Attaquess in the given list. Uses the route : Attaques.
     * @param attaquess : The list in which the Attaquess will be returned
     * @return The number of Attaquess returned
     */
    public int getAll(List<Attaques> attaquess) {
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
                result = extractItems(json, "Attaquess", attaquess);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                attaquess = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "attaques";
    }

    /**
     * Retrieve one Attaques. Uses the route : Attaques/%id%.
     * @param attaques : The Attaques to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(Attaques attaques) {
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
                if (extract(json, attaques)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                attaques = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one Attaques. Uses the route : Attaques/%id%.
     * @param attaques : The Attaques to retrieve (set the  ID)
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
     * Update a Attaques. Uses the route : Attaques/%id%.
     * @param attaques : The Attaques to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(Attaques attaques) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        attaques.getId(),
                        REST_FORMAT),
                    itemToJson(attaques));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, attaques);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a Attaques. Uses the route : Attaques/%id%.
     * @param attaques : The Attaques to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(Attaques attaques) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        attaques.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }

    /**
     * Get the Attaques associated with a Types. Uses the route : types/%Types_id%/attaques.
     * @param attaques : The Attaques that will be returned
     * @param types : The associated types
     * @return -1 if an error has occurred. 0 if not.
     */
    public int getByTypes(Attaques attaques, Types types) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        types.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, attaques);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                attaques = null;
            }
        }

        return result;
    }


    /**
     * Tests if the json is a valid Attaques Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(AttaquesWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a Attaques from a JSONObject describing a Attaques.
     * @param json The JSONObject describing the Attaques
     * @param attaques The returned Attaques
     * @return true if a Attaques was found. false if not
     */
    public boolean extract(JSONObject json, Attaques attaques) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(AttaquesWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(AttaquesWebServiceClientAdapter.JSON_ID)) {
                    attaques.setId(
                            json.getInt(AttaquesWebServiceClientAdapter.JSON_ID));
                }

                if (json.has(AttaquesWebServiceClientAdapter.JSON_NOM)
                        && !json.isNull(AttaquesWebServiceClientAdapter.JSON_NOM)) {
                    attaques.setNom(
                            json.getString(AttaquesWebServiceClientAdapter.JSON_NOM));
                }

                if (json.has(AttaquesWebServiceClientAdapter.JSON_PUISSANCE)
                        && !json.isNull(AttaquesWebServiceClientAdapter.JSON_PUISSANCE)) {
                    attaques.setPuissance(
                            json.getInt(AttaquesWebServiceClientAdapter.JSON_PUISSANCE));
                }

                if (json.has(AttaquesWebServiceClientAdapter.JSON_PRECIS)
                        && !json.isNull(AttaquesWebServiceClientAdapter.JSON_PRECIS)) {
                    attaques.setPrecis(
                            json.getInt(AttaquesWebServiceClientAdapter.JSON_PRECIS));
                }

                if (json.has(AttaquesWebServiceClientAdapter.JSON_TYPE)
                        && !json.isNull(AttaquesWebServiceClientAdapter.JSON_TYPE)) {

                    try {
                        TypesWebServiceClientAdapter typeAdapter =
                                new TypesWebServiceClientAdapter(this.context);
                        Types type =
                                new Types();

                        if (typeAdapter.extract(
                                json.optJSONObject(
                                        AttaquesWebServiceClientAdapter.JSON_TYPE),
                                        type)) {
                            attaques.setType(type);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains Types data");
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
        String id = json.optString(AttaquesWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[5];
                if (json.has(AttaquesWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(AttaquesWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(AttaquesWebServiceClientAdapter.JSON_NOM)) {
                    row[1] = json.getString(AttaquesWebServiceClientAdapter.JSON_NOM);
                }
                if (json.has(AttaquesWebServiceClientAdapter.JSON_PUISSANCE)) {
                    row[2] = json.getString(AttaquesWebServiceClientAdapter.JSON_PUISSANCE);
                }
                if (json.has(AttaquesWebServiceClientAdapter.JSON_PRECIS)) {
                    row[3] = json.getString(AttaquesWebServiceClientAdapter.JSON_PRECIS);
                }
                if (json.has(AttaquesWebServiceClientAdapter.JSON_TYPE)) {
                    JSONObject typeJson = json.getJSONObject(
                            AttaquesWebServiceClientAdapter.JSON_TYPE);
                    row[4] = typeJson.getString(
                            TypesWebServiceClientAdapter.JSON_ID);
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
     * Convert a Attaques to a JSONObject.
     * @param attaques The Attaques to convert
     * @return The converted Attaques
     */
    public JSONObject itemToJson(Attaques attaques) {
        JSONObject params = new JSONObject();
        try {
            params.put(AttaquesWebServiceClientAdapter.JSON_ID,
                    attaques.getId());
            params.put(AttaquesWebServiceClientAdapter.JSON_NOM,
                    attaques.getNom());
            params.put(AttaquesWebServiceClientAdapter.JSON_PUISSANCE,
                    attaques.getPuissance());
            params.put(AttaquesWebServiceClientAdapter.JSON_PRECIS,
                    attaques.getPrecis());

            if (attaques.getType() != null) {
                TypesWebServiceClientAdapter typeAdapter =
                        new TypesWebServiceClientAdapter(this.context);

                params.put(AttaquesWebServiceClientAdapter.JSON_TYPE,
                        typeAdapter.itemIdToJson(attaques.getType()));
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
    public JSONObject itemIdToJson(Attaques item) {
        JSONObject params = new JSONObject();
        try {
            params.put(AttaquesWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a Attaques to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(AttaquesWebServiceClientAdapter.JSON_ID,
                    values.get(AttaquesContract.COL_ID));
            params.put(AttaquesWebServiceClientAdapter.JSON_NOM,
                    values.get(AttaquesContract.COL_NOM));
            params.put(AttaquesWebServiceClientAdapter.JSON_PUISSANCE,
                    values.get(AttaquesContract.COL_PUISSANCE));
            params.put(AttaquesWebServiceClientAdapter.JSON_PRECIS,
                    values.get(AttaquesContract.COL_PRECIS));
            TypesWebServiceClientAdapter typeAdapter =
                    new TypesWebServiceClientAdapter(this.context);

            params.put(AttaquesWebServiceClientAdapter.JSON_TYPE,
                    typeAdapter.contentValuesToJson(values));
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
            List<Attaques> items,
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
                Attaques item = new Attaques();
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
            List<Attaques> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}
