/**************************************************************************
 * DresseursShowActivity.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.view.dresseurs;

import com.kerhomjarnoin.pokemon.R;

import com.kerhomjarnoin.pokemon.harmony.view.HarmonyFragmentActivity;
import com.kerhomjarnoin.pokemon.view.dresseurs.DresseursShowFragment.DeleteCallback;
import android.os.Bundle;

/** Dresseurs show Activity.
 *
 * This only contains a DresseursShowFragment.
 *
 * @see android.app.Activity
 */
public class DresseursShowActivity 
        extends HarmonyFragmentActivity 
        implements DeleteCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_dresseurs_show;
    }

    @Override
    public void onItemDeleted() {
        this.finish();
    }
}
