/**************************************************************************
 * BadgesCreateActivity.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.view.badges;

import com.kerhomjarnoin.pokemon.R;

import com.kerhomjarnoin.pokemon.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** 
 * Badges create Activity.
 *
 * This only contains a BadgesCreateFragment.
 *
 * @see android.app.Activity
 */
public class BadgesCreateActivity extends HarmonyFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_badges_create;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
