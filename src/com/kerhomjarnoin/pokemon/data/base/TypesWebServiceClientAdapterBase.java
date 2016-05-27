/**************************************************************************
 * TypesWebServiceClientAdapterBase.java, pokemon Android
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
import com.kerhomjarnoin.pokemon.entity.Types;
import com.kerhomjarnoin.pokemon.data.RestClient.Verb;
import com.kerhomjarnoin.pokemon.provider.contract.TypesContract;

import com.kerhomjarnoin.pokemon.entity.TypeDePokemons;


/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit TypesWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class TypesWebServiceClientAdapterBase
        extends WebServiceClientAdapter<Types> {
    /** TypesWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "TypesWSClientAdapter";

    /** JSON Object Types pattern. */
    protected static String JSON_OBJECT_TYPES = "Types";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";
    /** JSON_NOM attributes. */
    protected static String JSON_NOM = "nom";
    /** JSON_FAIBLECONTRE attributes. */
    protected static String JSON_FAIBLECONTRE = "faibleContre";
    /** JSON_FORTCONTRE attributes. */
    protected static String JSON_FORTCONTRE = "fortContre";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** Types REST Columns. */
    public static String[] REST_COLS = new String[]{
            TypesContract.COL_ID,
            TypesContract.COL_NOM
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public TypesWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public TypesWebServiceClientAdapterBase(Context context,
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
    public TypesWebServiceClientAdapterBase(Context context,
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
    public TypesWebServiceClientAdapterBase(Context context,
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
    public TypesWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the Typess in the given list. Uses the route : Types.
     * @param typess : The list in which the Typess will be returned
     * @return The number of Typess returned
     */
    public int getAll(List<Types> typess) {
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
                result = extractItems(json, "Typess", typess);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                typess = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "types";
    }

    /**
     * Retrieve one Types. Uses the route : Types/%id%.
     * @param types : The Types to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(Types types) {
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
                if (extract(json, types)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                types = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one Types. Uses the route : Types/%id%.
     * @param types : The Types to retrieve (set the  ID)
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
     * Update a Types. Uses the route : Types/%id%.
     * @param types : The Types to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(Types types) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        types.getId(),
                        REST_FORMAT),
                    itemToJson(types));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, types);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a Types. Uses the route : Types/%id%.
     * @param types : The Types to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(Types types) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        types.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }

    /**
     * Get the Typess associated with a TypeDePokemons. Uses the route : typedepokemons/%TypeDePokemons_id%/types.
     * @param typess : The list in which the Typess will be returned
     * @param typedepokemons : The associated typedepokemons
     * @return The number of Typess returned
     */
    public int getByTypeDePokemonstypesInternal(List<Types> typess, TypeDePokemons typeDePokemons) {
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
                result = this.extractItems(json, "Typess", typess);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                typess = null;
            }
        }

        return result;
    }

    /**
     * Get the Typess associated with a Types. Uses the route : types/%Types_id%/types.
     * @param typess : The list in which the Typess will be returned
     * @param types : The associated types
     * @return The number of Typess returned
     */
    public int getByTypesfaibleContreInternal(List<Types> typess, Types types) {
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
                result = this.extractItems(json, "Typess", typess);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                typess = null;
            }
        }

        return result;
    }


    /**
     * Get the Typess associated with a Types. Uses the route : types/%Types_id%/types.
     * @param typess : The list in which the Typess will be returned
     * @param types : The associated types
     * @return The number of Typess returned
     */
    public int getByTypesfortContreInternal(List<Types> typess, Types types) {
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
                result = this.extractItems(json, "Typess", typess);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                typess = null;
            }
        }

        return result;
    }



    /**
     * Tests if the json is a valid Types Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(TypesWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a Types from a JSONObject describing a Types.
     * @param json The JSONObject describing the Types
     * @param types The returned Types
     * @return true if a Types was found. false if not
     */
    public boolean extract(JSONObject json, Types types) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(TypesWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(TypesWebServiceClientAdapter.JSON_ID)) {
                    types.setId(
                            json.getInt(TypesWebServiceClientAdapter.JSON_ID));
                }

                if (json.has(TypesWebServiceClientAdapter.JSON_NOM)
                        && !json.isNull(TypesWebServiceClientAdapter.JSON_NOM)) {
                    types.setNom(
                            json.getString(TypesWebServiceClientAdapter.JSON_NOM));
                }

                if (json.has(TypesWebServiceClientAdapter.JSON_FAIBLECONTRE)
                        && !json.isNull(TypesWebServiceClientAdapter.JSON_FAIBLECONTRE)) {
                    ArrayList<Types> faibleContre =
                            new ArrayList<Types>();
                    TypesWebServiceClientAdapter faibleContreAdapter =
                            new TypesWebServiceClientAdapter(this.context);

                    try {
                        //.optJSONObject(TypesWebServiceClientAdapter.JSON_FAIBLECONTRE);
                        faibleContreAdapter.extractItems(
                                json, TypesWebServiceClientAdapter.JSON_FAIBLECONTRE,
                                faibleContre);
                        types.setFaibleContre(faibleContre);
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }

                if (json.has(TypesWebServiceClientAdapter.JSON_FORTCONTRE)
                        && !json.isNull(TypesWebServiceClientAdapter.JSON_FORTCONTRE)) {
                    ArrayList<Types> fortContre =
                            new ArrayList<Types>();
                    TypesWebServiceClientAdapter fortContreAdapter =
                            new TypesWebServiceClientAdapter(this.context);

                    try {
                        //.optJSONObject(TypesWebServiceClientAdapter.JSON_FORTCONTRE);
                        fortContreAdapter.extractItems(
                                json, TypesWebServiceClientAdapter.JSON_FORTCONTRE,
                                fortContre);
                        types.setFortContre(fortContre);
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
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
        String id = json.optString(TypesWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[2];
                if (json.has(TypesWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(TypesWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(TypesWebServiceClientAdapter.JSON_NOM)) {
                    row[1] = json.getString(TypesWebServiceClientAdapter.JSON_NOM);
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
     * Convert a Types to a JSONObject.
     * @param types The Types to convert
     * @return The converted Types
     */
    public JSONObject itemToJson(Types types) {
        JSONObject params = new JSONObject();
        try {
            params.put(TypesWebServiceClientAdapter.JSON_ID,
                    types.getId());
            params.put(TypesWebServiceClientAdapter.JSON_NOM,
                    types.getNom());

            if (types.getFaibleContre() != null) {
                TypesWebServiceClientAdapter faibleContreAdapter =
                        new TypesWebServiceClientAdapter(this.context);

                params.put(TypesWebServiceClientAdapter.JSON_FAIBLECONTRE,
                        faibleContreAdapter.itemsIdToJson(types.getFaibleContre()));
            }

            if (types.getFortContre() != null) {
                TypesWebServiceClientAdapter fortContreAdapter =
                        new TypesWebServiceClientAdapter(this.context);

                params.put(TypesWebServiceClientAdapter.JSON_FORTCONTRE,
                        fortContreAdapter.itemsIdToJson(types.getFortContre()));
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
    public JSONObject itemIdToJson(Types item) {
        JSONObject params = new JSONObject();
        try {
            params.put(TypesWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a Types to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(TypesWebServiceClientAdapter.JSON_ID,
                    values.get(TypesContract.COL_ID));
            params.put(TypesWebServiceClientAdapter.JSON_NOM,
                    values.get(TypesContract.COL_NOM));
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
            List<Types> items,
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
                Types item = new Types();
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
            List<Types> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}
