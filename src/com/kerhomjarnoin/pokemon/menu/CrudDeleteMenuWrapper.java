/**************************************************************************
 * CrudDeleteMenuWrapper.java, pokemon Android
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
 * Crud Menu wrapper for delete and delete actions.
 */
public class CrudDeleteMenuWrapper implements MenuWrapperBase {
    /** Delete menu item. */
    private MenuItem deleteItem;
    /** Menu Visibility. */
    private boolean visible = true;
    
    @Override
    public void initializeMenu(Menu menu, FragmentActivity activity,
            Fragment fragment, android.content.Context ctx) {
        if ((fragment != null
                && fragment instanceof CrudDeleteMenuInterface)
                || activity instanceof CrudDeleteMenuInterface) {            
            this.deleteItem     = menu.add(
                    PokemonMenu.CRUDDELETE,
                    1,
                    Menu.NONE,
                    R.string.menu_item_delete);
                    
            MenuItemCompat.setShowAsAction(this.deleteItem,
                    MenuItemCompat.SHOW_AS_ACTION_IF_ROOM
                    | MenuItemCompat.SHOW_AS_ACTION_WITH_TEXT);
                    
            this.deleteItem.setVisible(false);
        }
    }

    @Override
    public void updateMenu(Menu menu, FragmentActivity activity,
            Fragment fragment, android.content.Context ctx) {
        if ((fragment != null 
                && fragment instanceof CrudDeleteMenuInterface)
                || ctx instanceof CrudDeleteMenuInterface) {
            menu.setGroupVisible(
                    PokemonMenu.CRUDDELETE, this.visible);
        }
    }

    @Override
    public boolean dispatch(MenuItem item, android.content.Context ctx, Fragment fragment) {
        boolean result = false;
        if ((fragment != null 
                && fragment instanceof CrudDeleteMenuInterface)
                || ctx instanceof CrudDeleteMenuInterface) {
            if (item.equals(this.deleteItem)) {
                ((CrudDeleteMenuInterface) fragment).onClickDelete();
                result = true;
            }
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
        
        if (fragment != null 
                && fragment instanceof CrudDeleteMenuInterface) {
            menu.removeGroup(PokemonMenu.CRUDDELETE);
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
     * Implement this interface in your activity or fragment
     * to have delete and delete menu buttons.
     */
    public interface CrudDeleteMenuInterface {
        /** On click delete. */
        void onClickDelete();
    }
}


