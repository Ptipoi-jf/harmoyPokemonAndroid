/**************************************************************************
 * ArenesShowActivity.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.view.arenes;

import com.kerhomjarnoin.pokemon.R;

import com.kerhomjarnoin.pokemon.harmony.view.HarmonyFragmentActivity;
import com.kerhomjarnoin.pokemon.view.arenes.ArenesShowFragment.DeleteCallback;
import android.os.Bundle;

/** Arenes show Activity.
 *
 * This only contains a ArenesShowFragment.
 *
 * @see android.app.Activity
 */
public class ArenesShowActivity 
        extends HarmonyFragmentActivity 
        implements DeleteCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_arenes_show;
    }

    @Override
    public void onItemDeleted() {
        this.finish();
    }
}
