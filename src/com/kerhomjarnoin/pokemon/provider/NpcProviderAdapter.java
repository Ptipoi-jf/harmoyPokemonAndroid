/**************************************************************************
 * NpcProviderAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.provider;

import com.kerhomjarnoin.pokemon.provider.base.NpcProviderAdapterBase;
import com.kerhomjarnoin.pokemon.provider.base.PokemonProviderBase;

/**
 * NpcProviderAdapter.
 *
 * A provider adapter is used to separate your provider requests for
 * each entity of your application.
 * You will find here basic methods for database manipulation.
 * Feel free to override any method here.
 */
public class NpcProviderAdapter
                    extends NpcProviderAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public NpcProviderAdapter(
            final PokemonProviderBase provider) {
        super(provider);
    }
}

