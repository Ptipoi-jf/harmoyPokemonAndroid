/**************************************************************************
 * ObjetsWebServiceClientAdapterBase.java, pokemon Android
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
import com.kerhomjarnoin.pokemon.entity.Objets;
import com.kerhomjarnoin.pokemon.data.RestClient.Verb;
import com.kerhomjarnoin.pokemon.provider.contract.ObjetsContract;

import com.kerhomjarnoin.pokemon.entity.Npc;
import com.kerhomjarnoin.pokemon.entity.TypeObjet;


/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit ObjetsWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class ObjetsWebServiceClientAdapterBase
        extends WebServiceClientAdapter<Objets> {
    /** ObjetsWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "ObjetsWSClientAdapter";

    /** JSON Object Objets pattern. */
    protected static String JSON_OBJECT_OBJETS = "Objets";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";
    /** JSON_NOM attributes. */
    protected static String JSON_NOM = "nom";
    /** JSON_QUANTITE attributes. */
    protected static String JSON_QUANTITE = "quantite";
    /** JSON_TYPE attributes. */
    protected static String JSON_TYPE = "type";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** Objets REST Columns. */
    public static String[] REST_COLS = new String[]{
            ObjetsContract.COL_ID,
            ObjetsContract.COL_NOM,
            ObjetsContract.COL_QUANTITE,
            ObjetsContract.COL_TYPE_ID
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public ObjetsWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public ObjetsWebServiceClientAdapterBase(Context context,
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
    public ObjetsWebServiceClientAdapterBase(Context context,
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
    public ObjetsWebServiceClientAdapterBase(Context context,
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
    public ObjetsWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the Objetss in the given list. Uses the route : Objets.
     * @param objetss : The list in which the Objetss will be returned
     * @return The number of Objetss returned
     */
    public int getAll(List<Objets> objetss) {
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
                result = extractItems(json, "Objetss", objetss);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                objetss = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "objets";
    }

    /**
     * Retrieve one Objets. Uses the route : Objets/%id%.
     * @param objets : The Objets to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(Objets objets) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        objets.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                if (extract(json, objets)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                objets = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one Objets. Uses the route : Objets/%id%.
     * @param objets : The Objets to retrieve (set the  ID)
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
     * Update a Objets. Uses the route : Objets/%id%.
     * @param objets : The Objets to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(Objets objets) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        objets.getId(),
                        REST_FORMAT),
                    itemToJson(objets));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, objets);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a Objets. Uses the route : Objets/%id%.
     * @param objets : The Objets to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(Objets objets) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        objets.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }

    /**
     * Get the Objetss associated with a Npc. Uses the route : npc/%Npc_id%/objets.
     * @param objetss : The list in which the Objetss will be returned
     * @param npc : The associated npc
     * @return The number of Objetss returned
     */
    public int getByNpcobjetsInternal(List<Objets> objetss, Npc npc) {
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
                result = this.extractItems(json, "Objetss", objetss);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                objetss = null;
            }
        }

        return result;
    }

    /**
     * Get the Objets associated with a TypeObjet. Uses the route : typeobjet/%TypeObjet_id%/objets.
     * @param objets : The Objets that will be returned
     * @param typeobjet : The associated typeobjet
     * @return -1 if an error has occurred. 0 if not.
     */
    public int getByTypeObjet(Objets objets, TypeObjet typeObjet) {
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
                this.extract(json, objets);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                objets = null;
            }
        }

        return result;
    }


    /**
     * Tests if the json is a valid Objets Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(ObjetsWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a Objets from a JSONObject describing a Objets.
     * @param json The JSONObject describing the Objets
     * @param objets The returned Objets
     * @return true if a Objets was found. false if not
     */
    public boolean extract(JSONObject json, Objets objets) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(ObjetsWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(ObjetsWebServiceClientAdapter.JSON_ID)) {
                    objets.setId(
                            json.getInt(ObjetsWebServiceClientAdapter.JSON_ID));
                }

                if (json.has(ObjetsWebServiceClientAdapter.JSON_NOM)
                        && !json.isNull(ObjetsWebServiceClientAdapter.JSON_NOM)) {
                    objets.setNom(
                            json.getString(ObjetsWebServiceClientAdapter.JSON_NOM));
                }

                if (json.has(ObjetsWebServiceClientAdapter.JSON_QUANTITE)
                        && !json.isNull(ObjetsWebServiceClientAdapter.JSON_QUANTITE)) {
                    objets.setQuantite(
                            json.getInt(ObjetsWebServiceClientAdapter.JSON_QUANTITE));
                }

                if (json.has(ObjetsWebServiceClientAdapter.JSON_TYPE)
                        && !json.isNull(ObjetsWebServiceClientAdapter.JSON_TYPE)) {

                    try {
                        TypeObjetWebServiceClientAdapter typeAdapter =
                                new TypeObjetWebServiceClientAdapter(this.context);
                        TypeObjet type =
                                new TypeObjet();

                        if (typeAdapter.extract(
                                json.optJSONObject(
                                        ObjetsWebServiceClientAdapter.JSON_TYPE),
                                        type)) {
                            objets.setType(type);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains TypeObjet data");
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
        String id = json.optString(ObjetsWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[4];
                if (json.has(ObjetsWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(ObjetsWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(ObjetsWebServiceClientAdapter.JSON_NOM)) {
                    row[1] = json.getString(ObjetsWebServiceClientAdapter.JSON_NOM);
                }
                if (json.has(ObjetsWebServiceClientAdapter.JSON_QUANTITE)) {
                    row[2] = json.getString(ObjetsWebServiceClientAdapter.JSON_QUANTITE);
                }
                if (json.has(ObjetsWebServiceClientAdapter.JSON_TYPE)) {
                    JSONObject typeJson = json.getJSONObject(
                            ObjetsWebServiceClientAdapter.JSON_TYPE);
                    row[3] = typeJson.getString(
                            TypeObjetWebServiceClientAdapter.JSON_ID);
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
     * Convert a Objets to a JSONObject.
     * @param objets The Objets to convert
     * @return The converted Objets
     */
    public JSONObject itemToJson(Objets objets) {
        JSONObject params = new JSONObject();
        try {
            params.put(ObjetsWebServiceClientAdapter.JSON_ID,
                    objets.getId());
            params.put(ObjetsWebServiceClientAdapter.JSON_NOM,
                    objets.getNom());
            params.put(ObjetsWebServiceClientAdapter.JSON_QUANTITE,
                    objets.getQuantite());

            if (objets.getType() != null) {
                TypeObjetWebServiceClientAdapter typeAdapter =
                        new TypeObjetWebServiceClientAdapter(this.context);

                params.put(ObjetsWebServiceClientAdapter.JSON_TYPE,
                        typeAdapter.itemIdToJson(objets.getType()));
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
    public JSONObject itemIdToJson(Objets item) {
        JSONObject params = new JSONObject();
        try {
            params.put(ObjetsWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a Objets to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(ObjetsWebServiceClientAdapter.JSON_ID,
                    values.get(ObjetsContract.COL_ID));
            params.put(ObjetsWebServiceClientAdapter.JSON_NOM,
                    values.get(ObjetsContract.COL_NOM));
            params.put(ObjetsWebServiceClientAdapter.JSON_QUANTITE,
                    values.get(ObjetsContract.COL_QUANTITE));
            TypeObjetWebServiceClientAdapter typeAdapter =
                    new TypeObjetWebServiceClientAdapter(this.context);

            params.put(ObjetsWebServiceClientAdapter.JSON_TYPE,
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
            List<Objets> items,
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
                Objets item = new Objets();
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
            List<Objets> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}
