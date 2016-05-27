/**************************************************************************
 * TypeDePokemonsListAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.view.typedepokemons;


import com.kerhomjarnoin.pokemon.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kerhomjarnoin.pokemon.entity.TypeDePokemons;
import com.kerhomjarnoin.pokemon.harmony.view.HarmonyCursorAdapter;
import com.kerhomjarnoin.pokemon.harmony.view.HarmonyViewHolder;
import com.kerhomjarnoin.pokemon.provider.contract.TypeDePokemonsContract;
import com.kerhomjarnoin.pokemon.provider.contract.TypesContract;

/**
 * List adapter for TypeDePokemons entity.
 */
public class TypeDePokemonsListAdapter extends HarmonyCursorAdapter<TypeDePokemons> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public TypeDePokemonsListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public TypeDePokemonsListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected TypeDePokemons cursorToItem(Cursor cursor) {
        return TypeDePokemonsContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return TypeDePokemonsContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<TypeDePokemons> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<TypeDePokemons> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_typedepokemons);
        }

        /**
         * Populate row with a {@link TypeDePokemons}.
         *
         * @param model {@link TypeDePokemons} data
         */
        public void populate(final TypeDePokemons model) {
            TextView nomView = (TextView) this.getView().findViewById(
                    R.id.row_typedepokemons_nom);
                    
            TextView attaqueView = (TextView) this.getView().findViewById(
                    R.id.row_typedepokemons_attaque);
                    
            TextView attaque_speView = (TextView) this.getView().findViewById(
                    R.id.row_typedepokemons_attaque_spe);
                    
            TextView defenceView = (TextView) this.getView().findViewById(
                    R.id.row_typedepokemons_defence);
                    
            TextView defence_speView = (TextView) this.getView().findViewById(
                    R.id.row_typedepokemons_defence_spe);
                    
            TextView vitesseView = (TextView) this.getView().findViewById(
                    R.id.row_typedepokemons_vitesse);
                    
            TextView pvView = (TextView) this.getView().findViewById(
                    R.id.row_typedepokemons_pv);
                    

            if (model.getNom() != null) {
                nomView.setText(model.getNom());
            }
            attaqueView.setText(String.valueOf(model.getAttaque()));
            attaque_speView.setText(String.valueOf(model.getAttaque_spe()));
            defenceView.setText(String.valueOf(model.getDefence()));
            defence_speView.setText(String.valueOf(model.getDefence_spe()));
            vitesseView.setText(String.valueOf(model.getVitesse()));
            pvView.setText(String.valueOf(model.getPv()));
        }
    }
}
