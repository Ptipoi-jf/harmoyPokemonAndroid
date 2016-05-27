/**************************************************************************
 * NpcListAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.view.npc;


import com.kerhomjarnoin.pokemon.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kerhomjarnoin.pokemon.entity.Npc;
import com.kerhomjarnoin.pokemon.harmony.view.HarmonyCursorAdapter;
import com.kerhomjarnoin.pokemon.harmony.view.HarmonyViewHolder;
import com.kerhomjarnoin.pokemon.provider.contract.NpcContract;
import com.kerhomjarnoin.pokemon.provider.contract.ObjetsContract;
import com.kerhomjarnoin.pokemon.provider.contract.PokemonsContract;
import com.kerhomjarnoin.pokemon.provider.contract.NpctoBadgesContract;
import com.kerhomjarnoin.pokemon.provider.contract.BadgesContract;
import com.kerhomjarnoin.pokemon.provider.contract.PositionsContract;

/**
 * List adapter for Npc entity.
 */
public class NpcListAdapter extends HarmonyCursorAdapter<Npc> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public NpcListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public NpcListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected Npc cursorToItem(Cursor cursor) {
        return NpcContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return NpcContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<Npc> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<Npc> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_npc);
        }

        /**
         * Populate row with a {@link Npc}.
         *
         * @param model {@link Npc} data
         */
        public void populate(final Npc model) {
            TextView nomView = (TextView) this.getView().findViewById(
                    R.id.row_npc_nom);
                    
            TextView professionView = (TextView) this.getView().findViewById(
                    R.id.row_npc_profession);
                    
            TextView texteView = (TextView) this.getView().findViewById(
                    R.id.row_npc_texte);
                    
            TextView positionView = (TextView) this.getView().findViewById(
                    R.id.row_npc_position);
                    

            if (model.getNom() != null) {
                nomView.setText(model.getNom());
            }
            if (model.getProfession() != null) {
                professionView.setText(model.getProfession());
            }
            if (model.getTexte() != null) {
                texteView.setText(model.getTexte());
            }
            if (model.getPosition() != null) {
                positionView.setText(
                        String.valueOf(model.getPosition().getId()));
            }
        }
    }
}
