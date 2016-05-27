/**************************************************************************
 * AttaquesListAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.view.attaques;


import com.kerhomjarnoin.pokemon.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kerhomjarnoin.pokemon.entity.Attaques;
import com.kerhomjarnoin.pokemon.harmony.view.HarmonyCursorAdapter;
import com.kerhomjarnoin.pokemon.harmony.view.HarmonyViewHolder;
import com.kerhomjarnoin.pokemon.provider.contract.AttaquesContract;
import com.kerhomjarnoin.pokemon.provider.contract.TypesContract;

/**
 * List adapter for Attaques entity.
 */
public class AttaquesListAdapter extends HarmonyCursorAdapter<Attaques> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public AttaquesListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public AttaquesListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected Attaques cursorToItem(Cursor cursor) {
        return AttaquesContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return AttaquesContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<Attaques> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<Attaques> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_attaques);
        }

        /**
         * Populate row with a {@link Attaques}.
         *
         * @param model {@link Attaques} data
         */
        public void populate(final Attaques model) {
            TextView nomView = (TextView) this.getView().findViewById(
                    R.id.row_attaques_nom);
                    
            TextView puissanceView = (TextView) this.getView().findViewById(
                    R.id.row_attaques_puissance);
                    
            TextView precisView = (TextView) this.getView().findViewById(
                    R.id.row_attaques_precis);
                    
            TextView typeView = (TextView) this.getView().findViewById(
                    R.id.row_attaques_type);
                    

            if (model.getNom() != null) {
                nomView.setText(model.getNom());
            }
            puissanceView.setText(String.valueOf(model.getPuissance()));
            precisView.setText(String.valueOf(model.getPrecis()));
            if (model.getType() != null) {
                typeView.setText(
                        String.valueOf(model.getType().getId()));
            }
        }
    }
}
