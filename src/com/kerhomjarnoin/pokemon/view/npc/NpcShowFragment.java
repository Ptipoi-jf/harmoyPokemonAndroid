/**************************************************************************
 * NpcShowFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 27, 2016
 *
 **************************************************************************/
package com.kerhomjarnoin.pokemon.view.npc;


import android.content.Intent;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kerhomjarnoin.pokemon.R;
import com.kerhomjarnoin.pokemon.entity.Npc;
import com.kerhomjarnoin.pokemon.entity.Objets;
import com.kerhomjarnoin.pokemon.entity.Pokemons;
import com.kerhomjarnoin.pokemon.entity.Badges;
import com.kerhomjarnoin.pokemon.harmony.view.DeleteDialog;
import com.kerhomjarnoin.pokemon.harmony.view.HarmonyFragment;
import com.kerhomjarnoin.pokemon.harmony.view.MultiLoader;
import com.kerhomjarnoin.pokemon.harmony.view.MultiLoader.UriLoadedCallback;
import com.kerhomjarnoin.pokemon.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.kerhomjarnoin.pokemon.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.kerhomjarnoin.pokemon.provider.utils.NpcProviderUtils;
import com.kerhomjarnoin.pokemon.provider.NpcProviderAdapter;
import com.kerhomjarnoin.pokemon.provider.contract.NpcContract;
import com.kerhomjarnoin.pokemon.provider.contract.ObjetsContract;
import com.kerhomjarnoin.pokemon.provider.contract.PokemonsContract;
import com.kerhomjarnoin.pokemon.provider.contract.BadgesContract;
import com.kerhomjarnoin.pokemon.provider.contract.PositionsContract;

/** Npc show fragment.
 *
 * This fragment gives you an interface to show a Npc.
 * 
 * @see android.app.Fragment
 */
public class NpcShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected Npc model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** nom View. */
    protected TextView nomView;
    /** profession View. */
    protected TextView professionView;
    /** texte View. */
    protected TextView texteView;
    /** objets View. */
    protected TextView objetsView;
    /** pokemons View. */
    protected TextView pokemonsView;
    /** badges View. */
    protected TextView badgesView;
    /** position View. */
    protected TextView positionView;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no Npc. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.nomView =
            (TextView) view.findViewById(
                    R.id.npc_nom);
        this.professionView =
            (TextView) view.findViewById(
                    R.id.npc_profession);
        this.texteView =
            (TextView) view.findViewById(
                    R.id.npc_texte);
        this.objetsView =
            (TextView) view.findViewById(
                    R.id.npc_objets);
        this.pokemonsView =
            (TextView) view.findViewById(
                    R.id.npc_pokemons);
        this.badgesView =
            (TextView) view.findViewById(
                    R.id.npc_badges);
        this.positionView =
            (TextView) view.findViewById(
                    R.id.npc_position);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.npc_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.npc_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }
        if (this.model.getProfession() != null) {
            this.professionView.setText(this.model.getProfession());
        }
        if (this.model.getTexte() != null) {
            this.texteView.setText(this.model.getTexte());
        }
        if (this.model.getObjets() != null) {
            String objetsValue = "";
            for (Objets item : this.model.getObjets()) {
                objetsValue += item.getId() + ",";
            }
            this.objetsView.setText(objetsValue);
        }
        if (this.model.getPokemons() != null) {
            String pokemonsValue = "";
            for (Pokemons item : this.model.getPokemons()) {
                pokemonsValue += item.getId() + ",";
            }
            this.pokemonsView.setText(pokemonsValue);
        }
        if (this.model.getBadges() != null) {
            String badgesValue = "";
            for (Badges item : this.model.getBadges()) {
                badgesValue += item.getId() + ",";
            }
            this.badgesView.setText(badgesValue);
        }
        if (this.model.getPosition() != null) {
            this.positionView.setText(
                    String.valueOf(this.model.getPosition().getId()));
        }
        } else {
            this.dataLayout.setVisibility(View.GONE);
            this.emptyText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view =
                inflater.inflate(
                        R.layout.fragment_npc_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((Npc) intent.getParcelableExtra(NpcContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The Npc to get the data from.
     */
    public void update(Npc item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    NpcProviderAdapter.NPC_URI 
                    + "/" 
                    + this.model.getId();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    NpcShowFragment.this.onNpcLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/objets"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    NpcShowFragment.this.onObjetsLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/pokemons"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    NpcShowFragment.this.onPokemonsLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/badges"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    NpcShowFragment.this.onBadgesLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/position"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    NpcShowFragment.this.onPositionLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.init();
        }
    }

    /**
     * Called when the entity has been loaded.
     * 
     * @param c The cursor of this entity
     */
    public void onNpcLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            NpcContract.cursorToItem(
                        c,
                        this.model);
            this.loadData();
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onObjetsLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
            this.model.setObjets(ObjetsContract.cursorToItems(c));
            this.loadData();
            } else {
                this.model.setObjets(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onPokemonsLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
            this.model.setPokemons(PokemonsContract.cursorToItems(c));
            this.loadData();
            } else {
                this.model.setPokemons(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onBadgesLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
            this.model.setBadges(BadgesContract.cursorToItems(c));
            this.loadData();
            } else {
                this.model.setBadges(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onPositionLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setPosition(PositionsContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setPosition(null);
                    this.loadData();
            }
        }
    }

    /**
     * Calls the NpcEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    NpcEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(NpcContract.PARCEL, this.model);
        intent.putExtras(extras);

        this.getActivity().startActivity(intent);
    }
    /**
     * Shows a confirmation dialog.
     */
    @Override
    public void onClickDelete() {
        new DeleteDialog(this.getActivity(), this).show();
    }

    @Override
    public void onDeleteDialogClose(boolean ok) {
        if (ok) {
            new DeleteTask(this.getActivity(), this.model).execute();
        }
    }
    
    /** 
     * Called when delete task is done.
     */    
    public void onPostDelete() {
        if (this.deleteCallback != null) {
            this.deleteCallback.onItemDeleted();
        }
    }

    /**
     * This class will remove the entity into the DB.
     * It runs asynchronously.
     */
    private class DeleteTask extends AsyncTask<Void, Void, Integer> {
        /** AsyncTask's context. */
        private android.content.Context ctx;
        /** Entity to delete. */
        private Npc item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build NpcSQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final Npc item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new NpcProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                NpcShowFragment.this.onPostDelete();
            }
            super.onPostExecute(result);
        }
        
        

    }

    /**
     * Callback for item deletion.
     */ 
    public interface DeleteCallback {
        /** Called when current item has been deleted. */
        void onItemDeleted();
    }
}

