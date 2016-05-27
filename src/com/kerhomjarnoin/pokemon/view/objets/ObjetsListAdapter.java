/**************************************************************************
 * ObjetsListAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.view.objets;


import com.kerhomjarnoin.pokemon.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kerhomjarnoin.pokemon.entity.Objets;
import com.kerhomjarnoin.pokemon.harmony.view.HarmonyCursorAdapter;
import com.kerhomjarnoin.pokemon.harmony.view.HarmonyViewHolder;
import com.kerhomjarnoin.pokemon.provider.contract.ObjetsContract;
import com.kerhomjarnoin.pokemon.provider.contract.NpcContract;
import com.kerhomjarnoin.pokemon.provider.contract.TypeObjetContract;

/**
 * List adapter for Objets entity.
 */
public class ObjetsListAdapter extends HarmonyCursorAdapter<Objets> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public ObjetsListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public ObjetsListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected Objets cursorToItem(Cursor cursor) {
        return ObjetsContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return ObjetsContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<Objets> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<Objets> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_objets);
        }

        /**
         * Populate row with a {@link Objets}.
         *
         * @param model {@link Objets} data
         */
        public void populate(final Objets model) {
            TextView nomView = (TextView) this.getView().findViewById(
                    R.id.row_objets_nom);
                    
            TextView quantiteView = (TextView) this.getView().findViewById(
                    R.id.row_objets_quantite);
                    
            TextView typeView = (TextView) this.getView().findViewById(
                    R.id.row_objets_type);
                    

            if (model.getNom() != null) {
                nomView.setText(model.getNom());
            }
            quantiteView.setText(String.valueOf(model.getQuantite()));
            if (model.getType() != null) {
                typeView.setText(
                        String.valueOf(model.getType().getId()));
            }
        }
    }
}
