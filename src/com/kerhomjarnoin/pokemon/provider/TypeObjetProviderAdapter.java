/**************************************************************************
 * TypeObjetProviderAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.provider;

import com.kerhomjarnoin.pokemon.provider.base.TypeObjetProviderAdapterBase;
import com.kerhomjarnoin.pokemon.provider.base.PokemonProviderBase;

/**
 * TypeObjetProviderAdapter.
 *
 * A provider adapter is used to separate your provider requests for
 * each entity of your application.
 * You will find here basic methods for database manipulation.
 * Feel free to override any method here.
 */
public class TypeObjetProviderAdapter
                    extends TypeObjetProviderAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public TypeObjetProviderAdapter(
            final PokemonProviderBase provider) {
        super(provider);
    }
}

