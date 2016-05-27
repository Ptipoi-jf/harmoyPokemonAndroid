/**************************************************************************
 * BadgesListAdapter.java, pokemon Android
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

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kerhomjarnoin.pokemon.entity.Badges;
import com.kerhomjarnoin.pokemon.harmony.view.HarmonyCursorAdapter;
import com.kerhomjarnoin.pokemon.harmony.view.HarmonyViewHolder;
import com.kerhomjarnoin.pokemon.provider.contract.BadgesContract;

/**
 * List adapter for Badges entity.
 */
public class BadgesListAdapter extends HarmonyCursorAdapter<Badges> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public BadgesListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public BadgesListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected Badges cursorToItem(Cursor cursor) {
        return BadgesContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return BadgesContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<Badges> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<Badges> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_badges);
        }

        /**
         * Populate row with a {@link Badges}.
         *
         * @param model {@link Badges} data
         */
        public void populate(final Badges model) {
            TextView nomView = (TextView) this.getView().findViewById(
                    R.id.row_badges_nom);
                    

            if (model.getNom() != null) {
                nomView.setText(model.getNom());
            }
        }
    }
}
