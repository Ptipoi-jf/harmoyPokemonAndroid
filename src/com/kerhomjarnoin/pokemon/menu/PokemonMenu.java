/**************************************************************************
 * PokemonMenu.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.menu;


import android.support.v4.app.Fragment;

import com.kerhomjarnoin.pokemon.menu.base.PokemonMenuBase;

/**
 * PokemonMenu.
 * 
 * This class is an engine used to manage the different menus of your application.
 * Its use is quite simple :
 * Create a class called [YourMenuName]MenuWrapper in this package and
 * make it implement the interface MenuWrapperBase.
 * (For examples, please see CrudCreateMenuWrapper and CrudEditDeleteMenuWrapper in
 * this package.)
 * When this is done, just call this harmony command :
 * script/console.sh orm:menu:update.
 * This will auto-generate a group id for your menu.
 */
public class PokemonMenu
                extends PokemonMenuBase {

    /** Singleton unique instance. */
    private static volatile PokemonMenu singleton;

    /**
     * Constructor.
     * @param ctx The android.content.Context
     * @throws Exception If something bad happened
     */
    public PokemonMenu(final android.content.Context ctx) throws Exception {
        super(ctx);
    }

    /**
     * Constructor.
     * @param ctx The context
     * @param fragment The parent fragment
     * @throws Exception If something bad happened
     */
    public PokemonMenu(final android.content.Context ctx,
                        final Fragment fragment) throws Exception {
        super(ctx, fragment);
    }

    /** Get unique instance.
     * @param ctx The context
     * @return PokemonMenu instance
     * @throws Exception If something bad happened
     */
    public static final synchronized PokemonMenu getInstance(
                        final android.content.Context ctx) throws Exception {
        return getInstance(ctx, null);
    }

    /** Get unique instance.
     * @param ctx The context
     * @param fragment The parent fragment
     * @return PokemonMenu instance
     * @throws Exception If something bad happened
     */
    public static final synchronized PokemonMenu getInstance(
            final android.content.Context ctx, final Fragment fragment) throws Exception {
        if (singleton == null) {
            singleton = new PokemonMenu(ctx, fragment);
        }  else {
            singleton.ctx = ctx;
            singleton.fragment = fragment;
        }

        return singleton;
    }
}
