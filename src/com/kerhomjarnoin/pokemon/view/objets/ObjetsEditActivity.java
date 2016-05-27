/**************************************************************************
 * ObjetsEditActivity.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.view.objets;

import com.kerhomjarnoin.pokemon.R;

import com.kerhomjarnoin.pokemon.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** Objets edit Activity.
 *
 * This only contains a ObjetsEditFragment.
 *
 * @see android.app.Activity
 */
public class ObjetsEditActivity extends HarmonyFragmentActivity {

    @Override
      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_objets_edit;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
