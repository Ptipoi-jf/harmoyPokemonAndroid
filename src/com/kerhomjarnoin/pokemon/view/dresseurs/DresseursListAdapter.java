/**************************************************************************
 * DresseursListAdapter.java, pokemon Android
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

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kerhomjarnoin.pokemon.entity.Dresseurs;
import com.kerhomjarnoin.pokemon.harmony.view.HarmonyCursorAdapter;
import com.kerhomjarnoin.pokemon.harmony.view.HarmonyViewHolder;
import com.kerhomjarnoin.pokemon.provider.contract.DresseursContract;
import com.kerhomjarnoin.pokemon.provider.contract.NpcContract;

/**
 * List adapter for Dresseurs entity.
 */
public class DresseursListAdapter extends HarmonyCursorAdapter<Dresseurs> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public DresseursListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public DresseursListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected Dresseurs cursorToItem(Cursor cursor) {
        return DresseursContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return DresseursContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<Dresseurs> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<Dresseurs> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_dresseurs);
        }

        /**
         * Populate row with a {@link Dresseurs}.
         *
         * @param model {@link Dresseurs} data
         */
        public void populate(final Dresseurs model) {
            TextView nomView = (TextView) this.getView().findViewById(
                    R.id.row_dresseurs_nom);
                    
            TextView npcView = (TextView) this.getView().findViewById(
                    R.id.row_dresseurs_npc);
                    

            if (model.getNom() != null) {
                nomView.setText(model.getNom());
            }
            if (model.getNpc() != null) {
                npcView.setText(
                        String.valueOf(model.getNpc().getId()));
            }
        }
    }
}
