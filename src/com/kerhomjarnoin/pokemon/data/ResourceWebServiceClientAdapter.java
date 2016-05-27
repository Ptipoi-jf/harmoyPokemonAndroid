/**************************************************************************
 * ResourceWebServiceClientAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.data;

import android.content.Context;

import com.kerhomjarnoin.pokemon.data.base.ResourceWebServiceClientAdapterBase;

/**
 * Class for all your ResourceWebServiceClient adapters.
 * You can add your own/customize your methods here.
 */
public class ResourceWebServiceClientAdapter
        extends ResourceWebServiceClientAdapterBase {

    public ResourceWebServiceClientAdapter(Context context, String host,
            Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);
    }
}

