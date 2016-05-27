/**************************************************************************
 * PositionsListAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.view.positions;


import com.kerhomjarnoin.pokemon.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kerhomjarnoin.pokemon.entity.Positions;
import com.kerhomjarnoin.pokemon.harmony.view.HarmonyCursorAdapter;
import com.kerhomjarnoin.pokemon.harmony.view.HarmonyViewHolder;
import com.kerhomjarnoin.pokemon.provider.contract.PositionsContract;

/**
 * List adapter for Positions entity.
 */
public class PositionsListAdapter extends HarmonyCursorAdapter<Positions> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public PositionsListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public PositionsListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected Positions cursorToItem(Cursor cursor) {
        return PositionsContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return PositionsContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<Positions> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<Positions> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_positions);
        }

        /**
         * Populate row with a {@link Positions}.
         *
         * @param model {@link Positions} data
         */
        public void populate(final Positions model) {
            TextView xView = (TextView) this.getView().findViewById(
                    R.id.row_positions_x);
                    
            TextView yView = (TextView) this.getView().findViewById(
                    R.id.row_positions_y);
                    

            xView.setText(String.valueOf(model.getX()));
            yView.setText(String.valueOf(model.getY()));
        }
    }
}
