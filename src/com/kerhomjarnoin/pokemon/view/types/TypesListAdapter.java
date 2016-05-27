/**************************************************************************
 * TypesListAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.view.types;


import com.kerhomjarnoin.pokemon.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kerhomjarnoin.pokemon.entity.Types;
import com.kerhomjarnoin.pokemon.harmony.view.HarmonyCursorAdapter;
import com.kerhomjarnoin.pokemon.harmony.view.HarmonyViewHolder;
import com.kerhomjarnoin.pokemon.provider.contract.TypesContract;
import com.kerhomjarnoin.pokemon.provider.contract.TypeDePokemonsContract;

/**
 * List adapter for Types entity.
 */
public class TypesListAdapter extends HarmonyCursorAdapter<Types> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public TypesListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public TypesListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected Types cursorToItem(Cursor cursor) {
        return TypesContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return TypesContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<Types> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<Types> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_types);
        }

        /**
         * Populate row with a {@link Types}.
         *
         * @param model {@link Types} data
         */
        public void populate(final Types model) {
            TextView nomView = (TextView) this.getView().findViewById(
                    R.id.row_types_nom);
                    

            if (model.getNom() != null) {
                nomView.setText(model.getNom());
            }
        }
    }
}
