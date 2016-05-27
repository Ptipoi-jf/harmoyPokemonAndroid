/**************************************************************************
 * SaveMenuWrapper.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.menu;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;

import com.kerhomjarnoin.pokemon.R;
import com.kerhomjarnoin.pokemon.menu.base.MenuWrapperBase;

/**
 * Menu wrapper for save action. To implement a save menu item in your fragment
 * or activity, just make this fragment/activity implement the SaveMenuInterface
 */
public class SaveMenuWrapper implements MenuWrapperBase {
    /** Menu item SAVE. */
    private MenuItem saveItem;
    /** Menu Visibility. */
    private boolean visible = true;
    
    @Override
    public void initializeMenu(Menu menu, FragmentActivity activity,
            Fragment fragment, android.content.Context ctx) {
        
        if (fragment != null && fragment instanceof SaveMenuInterface) {    
            
            this.saveItem     = menu.add(
                    PokemonMenu.SAVE,
                    0,
                    Menu.NONE,
                    R.string.menu_item_save);
                    
            MenuItemCompat.setShowAsAction(this.saveItem,
                    MenuItemCompat.SHOW_AS_ACTION_IF_ROOM
                    | MenuItemCompat.SHOW_AS_ACTION_WITH_TEXT);
                    
            this.saveItem.setVisible(false);
        }
    }

    @Override
    public void updateMenu(Menu menu, FragmentActivity activity,
            Fragment fragment, android.content.Context ctx) {
        if (fragment != null && fragment instanceof SaveMenuInterface) {
            menu.setGroupVisible(
                    PokemonMenu.SAVE, this.visible);
        }
    }

    @Override
    public boolean dispatch(MenuItem item, android.content.Context ctx, Fragment fragment) {
        boolean result;
        if (fragment instanceof SaveMenuInterface) {
            switch (item.getItemId()) {
                case 0:
                    ((SaveMenuInterface) fragment).onClickSave();
                    result = true;
                    break;
                default:
                    result = false;
                    break;
            }
        } else {
            result = false;
        }
        return result;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
            Intent data, android.content.Context ctx, Fragment fragment) {
        // We don't need this.
    }

    @Override
    public void clear(Menu menu, FragmentActivity activity,
            Fragment fragment, android.content.Context ctx) {

        if (fragment != null && fragment instanceof SaveMenuInterface) {
            menu.removeGroup(PokemonMenu.SAVE);
        }
    }

    @Override
    public void hide(Menu menu, FragmentActivity activity, Fragment fragment,
            android.content.Context ctx) {
        this.visible = false;
    }

    @Override
    public void show(Menu menu, FragmentActivity activity, Fragment fragment,
            android.content.Context ctx) {
        this.visible = true;
    }

    /**
     * Implement this interface in your fragment or activity
     * to activate this menu.
     */
    public interface SaveMenuInterface {
        /**
         * Called when user clicks on Add menu button.
         */
        void onClickSave();
    }
}


