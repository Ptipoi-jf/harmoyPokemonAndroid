/**************************************************************************
 * HarmonyFragmentActivity.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.harmony.view;

import android.content.Intent;
import android.os.Bundle;
import android.content.res.TypedArray;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.kerhomjarnoin.pokemon.menu.PokemonMenu;
import com.kerhomjarnoin.pokemon.PokemonApplication;
import com.kerhomjarnoin.pokemon.PokemonApplicationBase.DeviceType;
import com.kerhomjarnoin.pokemon.R;

/**
 * Custom FragmentActivity for harmony projects.
 * This fragment activity helps you use the menu wrappers, detect alone if
 * you're in tablet/dual mode.
 */
public abstract class HarmonyFragmentActivity extends ActionBarActivity {
    /** Hack number for support v4 onActivityResult. */
    protected static final int SUPPORT_V4_RESULT_HACK = 0xFFFF;

    /** Android {@link Toolbar}. */
    private Toolbar toolbar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.setContentView(this.getContentView());
        
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        
        if (this.toolbar != null) {
            this.setSupportActionBar(this.toolbar);
            this.getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }
    }
    
    /** Get the content view id. */
    protected abstract int getContentView();
    
    /** Get the activity {@link Toolbar}. */
    protected Toolbar getToolbar() {
        return this.toolbar;
    }
    
    /** Enable/Disable the back button in toolbar. */
    protected void setNavigationBack(boolean enableBack) {
        if (this.toolbar != null) {
            if (enableBack) {
                TypedArray a = getTheme().obtainStyledAttributes(new int[] {
                        android.support.v7.appcompat.R.attr.homeAsUpIndicator});
                int resId = a.getResourceId(0, 0);
                this.toolbar.setNavigationIcon(resId);
                
                this.toolbar.setNavigationOnClickListener(
                        new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HarmonyFragmentActivity.this.onBackPressed();
                    }
                });
            } else {
                this.toolbar.setNavigationIcon(null);
                this.toolbar.setNavigationOnClickListener(null);
            }
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = true;

        try {
            PokemonMenu.getInstance(this).clear(menu);
            PokemonMenu.getInstance(this).updateMenu(menu, this);
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }

        if (result) {
            result = super.onCreateOptionsMenu(menu);
        }

        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result;
        try {
            result = PokemonMenu.getInstance(this).dispatch(
                                                                    item, this);
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }

        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                                                  Intent data) {
        try {
            PokemonMenu.getInstance(this).onActivityResult(
                                           requestCode, resultCode, data, this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Is this device in tablet mode ?
     *
     * @return true if tablet mode
     */
    public boolean isDualMode() {
        return PokemonApplication.getDeviceType(this).equals(DeviceType.TABLET);
    }
}
