/**************************************************************************
 * TypeObjetListAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.view.typeobjet;


import com.kerhomjarnoin.pokemon.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kerhomjarnoin.pokemon.entity.TypeObjet;
import com.kerhomjarnoin.pokemon.harmony.view.HarmonyCursorAdapter;
import com.kerhomjarnoin.pokemon.harmony.view.HarmonyViewHolder;
import com.kerhomjarnoin.pokemon.provider.contract.TypeObjetContract;

/**
 * List adapter for TypeObjet entity.
 */
public class TypeObjetListAdapter extends HarmonyCursorAdapter<TypeObjet> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public TypeObjetListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public TypeObjetListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected TypeObjet cursorToItem(Cursor cursor) {
        return TypeObjetContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return TypeObjetContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<TypeObjet> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<TypeObjet> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_typeobjet);
        }

        /**
         * Populate row with a {@link TypeObjet}.
         *
         * @param model {@link TypeObjet} data
         */
        public void populate(final TypeObjet model) {
            TextView nomView = (TextView) this.getView().findViewById(
                    R.id.row_typeobjet_nom);
                    

            if (model.getNom() != null) {
                nomView.setText(model.getNom());
            }
        }
    }
}
