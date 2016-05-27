/**************************************************************************
 * ArenesListAdapter.java, pokemon Android
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

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kerhomjarnoin.pokemon.entity.Arenes;
import com.kerhomjarnoin.pokemon.harmony.view.HarmonyCursorAdapter;
import com.kerhomjarnoin.pokemon.harmony.view.HarmonyViewHolder;
import com.kerhomjarnoin.pokemon.provider.contract.ArenesContract;
import com.kerhomjarnoin.pokemon.provider.contract.PositionsContract;
import com.kerhomjarnoin.pokemon.provider.contract.BadgesContract;

/**
 * List adapter for Arenes entity.
 */
public class ArenesListAdapter extends HarmonyCursorAdapter<Arenes> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public ArenesListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public ArenesListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected Arenes cursorToItem(Cursor cursor) {
        return ArenesContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return ArenesContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<Arenes> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<Arenes> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_arenes);
        }

        /**
         * Populate row with a {@link Arenes}.
         *
         * @param model {@link Arenes} data
         */
        public void populate(final Arenes model) {
            TextView nomView = (TextView) this.getView().findViewById(
                    R.id.row_arenes_nom);
                    
            TextView positionView = (TextView) this.getView().findViewById(
                    R.id.row_arenes_position);
                    
            TextView badgeView = (TextView) this.getView().findViewById(
                    R.id.row_arenes_badge);
                    

            if (model.getNom() != null) {
                nomView.setText(model.getNom());
            }
            if (model.getPosition() != null) {
                positionView.setText(
                        String.valueOf(model.getPosition().getId()));
            }
            if (model.getBadge() != null) {
                badgeView.setText(
                        String.valueOf(model.getBadge().getId()));
            }
        }
    }
}
