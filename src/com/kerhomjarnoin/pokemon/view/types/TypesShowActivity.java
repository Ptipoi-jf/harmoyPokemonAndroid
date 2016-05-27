/**************************************************************************
 * TypesShowActivity.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.view.types;

import com.kerhomjarnoin.pokemon.R;

import com.kerhomjarnoin.pokemon.harmony.view.HarmonyFragmentActivity;
import com.kerhomjarnoin.pokemon.view.types.TypesShowFragment.DeleteCallback;
import android.os.Bundle;

/** Types show Activity.
 *
 * This only contains a TypesShowFragment.
 *
 * @see android.app.Activity
 */
public class TypesShowActivity 
        extends HarmonyFragmentActivity 
        implements DeleteCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_types_show;
    }

    @Override
    public void onItemDeleted() {
        this.finish();
    }
}
