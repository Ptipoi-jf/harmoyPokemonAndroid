/**************************************************************************
 * TypeObjetWebServiceClientAdapterBase.java, pokemon Android
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
import com.kerhomjarnoin.pokemon.entity.TypeObjet;
import com.kerhomjarnoin.pokemon.data.RestClient.Verb;
import com.kerhomjarnoin.pokemon.provider.contract.TypeObjetContract;



/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit TypeObjetWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class TypeObjetWebServiceClientAdapterBase
        extends WebServiceClientAdapter<TypeObjet> {
    /** TypeObjetWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "TypeObjetWSClientAdapter";

    /** JSON Object TypeObjet pattern. */
    protected static String JSON_OBJECT_TYPEOBJET = "TypeObjet";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";
    /** JSON_NOM attributes. */
    protected static String JSON_NOM = "nom";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** TypeObjet REST Columns. */
    public static String[] REST_COLS = new String[]{
            TypeObjetContract.COL_ID,
            TypeObjetContract.COL_NOM
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public TypeObjetWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public TypeObjetWebServiceClientAdapterBase(Context context,
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
    public TypeObjetWebServiceClientAdapterBase(Context context,
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
    public TypeObjetWebServiceClientAdapterBase(Context context,
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
    public TypeObjetWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the TypeObjets in the given list. Uses the route : TypeObjet.
     * @param typeObjets : The list in which the TypeObjets will be returned
     * @return The number of TypeObjets returned
     */
    public int getAll(List<TypeObjet> typeObjets) {
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
                result = extractItems(json, "TypeObjets", typeObjets);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                typeObjets = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "typeobjet";
    }

    /**
     * Retrieve one TypeObjet. Uses the route : TypeObjet/%id%.
     * @param typeObjet : The TypeObjet to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(TypeObjet typeObjet) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        typeObjet.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                if (extract(json, typeObjet)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                typeObjet = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one TypeObjet. Uses the route : TypeObjet/%id%.
     * @param typeObjet : The TypeObjet to retrieve (set the  ID)
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
     * Update a TypeObjet. Uses the route : TypeObjet/%id%.
     * @param typeObjet : The TypeObjet to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(TypeObjet typeObjet) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        typeObjet.getId(),
                        REST_FORMAT),
                    itemToJson(typeObjet));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, typeObjet);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a TypeObjet. Uses the route : TypeObjet/%id%.
     * @param typeObjet : The TypeObjet to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(TypeObjet typeObjet) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        typeObjet.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }


    /**
     * Tests if the json is a valid TypeObjet Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(TypeObjetWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a TypeObjet from a JSONObject describing a TypeObjet.
     * @param json The JSONObject describing the TypeObjet
     * @param typeObjet The returned TypeObjet
     * @return true if a TypeObjet was found. false if not
     */
    public boolean extract(JSONObject json, TypeObjet typeObjet) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(TypeObjetWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(TypeObjetWebServiceClientAdapter.JSON_ID)) {
                    typeObjet.setId(
                            json.getInt(TypeObjetWebServiceClientAdapter.JSON_ID));
                }

                if (json.has(TypeObjetWebServiceClientAdapter.JSON_NOM)
                        && !json.isNull(TypeObjetWebServiceClientAdapter.JSON_NOM)) {
                    typeObjet.setNom(
                            json.getString(TypeObjetWebServiceClientAdapter.JSON_NOM));
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
        String id = json.optString(TypeObjetWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[2];
                if (json.has(TypeObjetWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(TypeObjetWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(TypeObjetWebServiceClientAdapter.JSON_NOM)) {
                    row[1] = json.getString(TypeObjetWebServiceClientAdapter.JSON_NOM);
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
     * Convert a TypeObjet to a JSONObject.
     * @param typeObjet The TypeObjet to convert
     * @return The converted TypeObjet
     */
    public JSONObject itemToJson(TypeObjet typeObjet) {
        JSONObject params = new JSONObject();
        try {
            params.put(TypeObjetWebServiceClientAdapter.JSON_ID,
                    typeObjet.getId());
            params.put(TypeObjetWebServiceClientAdapter.JSON_NOM,
                    typeObjet.getNom());
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
    public JSONObject itemIdToJson(TypeObjet item) {
        JSONObject params = new JSONObject();
        try {
            params.put(TypeObjetWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a TypeObjet to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(TypeObjetWebServiceClientAdapter.JSON_ID,
                    values.get(TypeObjetContract.COL_ID));
            params.put(TypeObjetWebServiceClientAdapter.JSON_NOM,
                    values.get(TypeObjetContract.COL_NOM));
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
            List<TypeObjet> items,
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
                TypeObjet item = new TypeObjet();
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
            List<TypeObjet> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}
