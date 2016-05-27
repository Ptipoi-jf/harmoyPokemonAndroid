/**************************************************************************
 * PokemonsListAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.view.pokemons;


import com.kerhomjarnoin.pokemon.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kerhomjarnoin.pokemon.harmony.util.DateUtils;
import com.kerhomjarnoin.pokemon.entity.Pokemons;
import com.kerhomjarnoin.pokemon.harmony.view.HarmonyCursorAdapter;
import com.kerhomjarnoin.pokemon.harmony.view.HarmonyViewHolder;
import com.kerhomjarnoin.pokemon.provider.contract.PokemonsContract;
import com.kerhomjarnoin.pokemon.provider.contract.NpcContract;
import com.kerhomjarnoin.pokemon.provider.contract.TypeDePokemonsContract;
import com.kerhomjarnoin.pokemon.provider.contract.AttaquesContract;

/**
 * List adapter for Pokemons entity.
 */
public class PokemonsListAdapter extends HarmonyCursorAdapter<Pokemons> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public PokemonsListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public PokemonsListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected Pokemons cursorToItem(Cursor cursor) {
        return PokemonsContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return PokemonsContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<Pokemons> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<Pokemons> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_pokemons);
        }

        /**
         * Populate row with a {@link Pokemons}.
         *
         * @param model {@link Pokemons} data
         */
        public void populate(final Pokemons model) {
            TextView surnomView = (TextView) this.getView().findViewById(
                    R.id.row_pokemons_surnom);
                    
            TextView niveauView = (TextView) this.getView().findViewById(
                    R.id.row_pokemons_niveau);
                    
            TextView captureView = (TextView) this.getView().findViewById(
                    R.id.row_pokemons_capture);
                    
            TextView typePokemonView = (TextView) this.getView().findViewById(
                    R.id.row_pokemons_typepokemon);
                    
            TextView attaque1View = (TextView) this.getView().findViewById(
                    R.id.row_pokemons_attaque1);
                    
            TextView attaque2View = (TextView) this.getView().findViewById(
                    R.id.row_pokemons_attaque2);
                    
            TextView attaque3View = (TextView) this.getView().findViewById(
                    R.id.row_pokemons_attaque3);
                    
            TextView attaque4View = (TextView) this.getView().findViewById(
                    R.id.row_pokemons_attaque4);
                    

            if (model.getSurnom() != null) {
                surnomView.setText(model.getSurnom());
            }
            niveauView.setText(String.valueOf(model.getNiveau()));
            if (model.getCapture() != null) {
                captureView.setText(DateUtils.formatDateToString(model.getCapture(), true));
            }
            if (model.getTypePokemon() != null) {
                typePokemonView.setText(
                        String.valueOf(model.getTypePokemon().getId()));
            }
            if (model.getAttaque1() != null) {
                attaque1View.setText(
                        String.valueOf(model.getAttaque1().getId()));
            }
            if (model.getAttaque2() != null) {
                attaque2View.setText(
                        String.valueOf(model.getAttaque2().getId()));
            }
            if (model.getAttaque3() != null) {
                attaque3View.setText(
                        String.valueOf(model.getAttaque3().getId()));
            }
            if (model.getAttaque4() != null) {
                attaque4View.setText(
                        String.valueOf(model.getAttaque4().getId()));
            }
        }
    }
}
